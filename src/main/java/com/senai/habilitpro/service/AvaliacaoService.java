package com.senai.habilitpro.service;

import com.senai.habilitpro.business.AvaliacaoBusiness;
import com.senai.habilitpro.enumerator.AvaliacaoStatusEnum;
import com.senai.habilitpro.exception.AvaliacaoException;
import com.senai.habilitpro.exception.GenericException;
import com.senai.habilitpro.model.dto.AvaliacaoDTO;
import com.senai.habilitpro.model.entity.Avaliacao;
import com.senai.habilitpro.model.repository.AvaliacaoRepository;
import org.modelmapper.ModelMapper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class AvaliacaoService {

    @Inject
    private AvaliacaoBusiness avaliacaoBusiness;

    @Inject
    private AvaliacaoRepository avaliacaoRepository;

    public void cadastrar(AvaliacaoDTO avaliacaoDTO) throws GenericException {
        avaliacaoBusiness.cadastrar(avaliacaoDTO);
    }

    public List<AvaliacaoDTO> getAvaliacoesByStatus(AvaliacaoStatusEnum statusSelecionado) {
        return avaliacaoBusiness.getAvaliacoesByStatus(statusSelecionado);
    }

    public AvaliacaoDTO findById(Long avaliacaoId) {
        Avaliacao avaliacao = this.avaliacaoRepository.find(Avaliacao.class, avaliacaoId);
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(avaliacao, AvaliacaoDTO.class);
    }

    public void concluir(Long avaliacaoId) throws AvaliacaoException {
        this.avaliacaoBusiness.concluir(avaliacaoId);
    }

    public void mudarStatus(Long avaliacaoId, AvaliacaoStatusEnum avaliacaoStatus) throws AvaliacaoException {
        this.avaliacaoBusiness.mudarStatus(avaliacaoId, avaliacaoStatus);
    }

    public List<AvaliacaoDTO> getTodasAvaliacoes() {
        return this.avaliacaoBusiness.getTodasAvaliacoes();
    }
}
