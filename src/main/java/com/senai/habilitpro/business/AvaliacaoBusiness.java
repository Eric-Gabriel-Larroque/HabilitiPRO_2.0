package com.senai.habilitpro.business;

import com.senai.habilitpro.enumerator.AvaliacaoStatusEnum;
import com.senai.habilitpro.exception.AvaliacaoException;
import com.senai.habilitpro.model.dto.AvaliacaoDTO;
import com.senai.habilitpro.model.dto.LoginDTO;
import com.senai.habilitpro.model.entity.Avaliacao;
import com.senai.habilitpro.model.repository.AvaliacaoRepository;
import com.senai.habilitpro.security.UserAuthenticationToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AvaliacaoBusiness {

    private static final Logger LOG = LogManager.getLogger(AvaliacaoBusiness.class);

    @Inject
    private AvaliacaoRepository avaliacaoRepository;
    @Inject
    private UserAuthenticationToken userAuthenticationToken;

    public void cadastrar(AvaliacaoDTO avaliacaoDTO) {
        LOG.info("Iniciando método de persistẽncia de avaliação...");
        persistirAvaliacao(avaliacaoDTO);
        LOG.info("Avaliação persistida com sucesso!");
    }

    private void persistirAvaliacao(AvaliacaoDTO avaliacaoDTO) {

        Avaliacao avaliacao = new Avaliacao();

        LOG.info("Populando atributos de avaliação...");
        avaliacaoRepository.persist(avaliacao);
        avaliacaoDTO.setId(avaliacao.getId());
        avaliacao.setStatus(AvaliacaoStatusEnum.EM_ESPERA);
    }

    public List<AvaliacaoDTO> getAvaliacoesByStatus(AvaliacaoStatusEnum statusSelecionado) {
        List<Avaliacao> avaliacoes = avaliacaoRepository.getAvaliacoesByStatus(statusSelecionado);
        LoginDTO loginDTO = userAuthenticationToken.getLoginDTO();
        if (!loginDTO.isAdmin()) {
            avaliacoes = avaliacoes.stream().filter(avaliacao -> loginDTO.getEmpresa().getId().equals(avaliacao.getModulo().getCurso().getEmpresa().getId()))
                    .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        }
        return parseListAvaliacaoEntityToDTO(avaliacoes);
    }

    public void concluir(Long avaliacaoId) throws AvaliacaoException {
        LOG.info("Iniciando método de conclusão de avaliação...");
        if (avaliacaoId == null) {
            throw new AvaliacaoException("O id da avaliação não pode ser nulo!");
        }
        Avaliacao avaliacao = avaliacaoRepository.find(Avaliacao.class, avaliacaoId);
        avaliacao.setStatus(AvaliacaoStatusEnum.CONCLUIDA);
        avaliacaoRepository.merge(avaliacao);
        LOG.info("Avaliação concluída com sucesso!");
    }

    public void mudarStatus(Long avaliacaoId, AvaliacaoStatusEnum avaliacaoStatus) throws AvaliacaoException {
        LOG.info("Iniciando método de mudança de status de avaliação...");
        if (avaliacaoId == null) {
            throw new AvaliacaoException("O id da avaliação não pode ser nulo!");
        }
        if (avaliacaoStatus == null) {
            throw new AvaliacaoException("O status da avaliação não pode ser nulo!");
        }
        Avaliacao avaliacao = avaliacaoRepository.find(Avaliacao.class, avaliacaoId);
        avaliacao.setStatus(avaliacaoStatus);
        avaliacaoRepository.merge(avaliacao);
        LOG.info("Status de avaliação alterado com sucesso!");
    }

    public List<AvaliacaoDTO> getTodasAvaliacoes() {
        LOG.info("Buscando todas as avaliações cadastradas no sistema...");
        List<Avaliacao> avaliacoes = avaliacaoRepository.getTodasAvaliacoes();
        return parseListAvaliacaoEntityToDTO(avaliacoes);
    }

    private List<AvaliacaoDTO> parseListAvaliacaoEntityToDTO(List<Avaliacao> avaliacoes) {
        ModelMapper modelMapper = new ModelMapper();
        return avaliacoes.stream()
                .map(avaliacao -> modelMapper.map(avaliacao, AvaliacaoDTO.class))
                .collect(Collectors.toList());
    }
}
