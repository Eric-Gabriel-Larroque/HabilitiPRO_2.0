package com.senai.habilitpro.business;

import com.senai.habilitpro.enumerator.AvaliacaoStatusEnum;
import com.senai.habilitpro.exception.AvaliacaoException;
import com.senai.habilitpro.exception.NotaException;
import com.senai.habilitpro.model.dto.NotaDTO;
import com.senai.habilitpro.model.entity.Avaliacao;
import com.senai.habilitpro.model.entity.Nota;
import com.senai.habilitpro.model.entity.Trabalhador;
import com.senai.habilitpro.model.repository.AvaliacaoRepository;
import com.senai.habilitpro.model.repository.NotaRepository;
import com.senai.habilitpro.model.repository.TrabalhadorRepository;
import com.senai.habilitpro.service.AvaliacaoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NotaBusiness {

    private static final Logger LOG = LogManager.getLogger(NotaBusiness.class);

    @Inject
    private NotaRepository notaRepository;
    @Inject
    private TrabalhadorRepository trabalhadorRepository;
    @Inject
    private AvaliacaoRepository avaliacaoRepository;
    @Inject
    private AvaliacaoService avaliacaoService;

    public void cadastrar(NotaDTO notaDTO) {
    }

    private void persistirNota(NotaDTO notaDTO) {
        Nota nota = new Nota();

        notaRepository.persist(nota);
        notaDTO.setId(nota.getId());
    }

    public void salvar(List<Trabalhador> trabalhadores, Long avaliacaoId) throws NotaException, AvaliacaoException {
        LOG.info("Iniciando método de persistência de notas de trabalhadores...");
        if (trabalhadores.isEmpty()) {
            throw new NotaException("Nenhum trabalhador foi selecionado para avaliação!");
        }
        Avaliacao avaliacao = avaliacaoRepository.find(Avaliacao.class, avaliacaoId);
        if (avaliacao.getStatus() == AvaliacaoStatusEnum.CONCLUIDA) {
            throw new AvaliacaoException("A avaliação já foi concluída, não podendo ser mais alterado as notas!");
        }
        Set<Nota> notas = new HashSet<>();
        for (Trabalhador trabalhador : trabalhadores) {
            Nota nota = new Nota();
            //get note from index 0
            Nota notaAtualTrabalhador = new ArrayList<>(trabalhador.getNotas()).get(0);
            if (notaAtualTrabalhador.getId() == null) {
                nota.setId(avaliacaoId);
            } else {
                nota.setId(notaAtualTrabalhador.getId());
            }
            if (notaAtualTrabalhador.getNota() == null) {
                throw new NotaException("Todos os trabalhadores devem possuir nota!");
            }
            nota.setNota(notaAtualTrabalhador.getNota());
            nota.setTrabalhador(trabalhadorRepository.find(Trabalhador.class, trabalhador.getId()));
            nota.setAvaliacao(avaliacao);
            notas.add(nota);
        }
        for (Nota nota : notas) {
            notaRepository.merge(nota);
        }
        this.avaliacaoService.mudarStatus(avaliacaoId, AvaliacaoStatusEnum.EM_AVALIACAO);
    }
}
