package com.senai.habilitpro.business;

import com.senai.habilitpro.model.dto.HabilidadeDTO;
import com.senai.habilitpro.model.entity.Habilidade;
import com.senai.habilitpro.model.repository.HabilidadeRepository;

import javax.inject.Inject;

public class HabilidadeBusiness {

    @Inject
    private HabilidadeRepository habilidadeRepository;

    public void cadastrar(HabilidadeDTO habilidadeDTO) {
        persistirHabilidade(habilidadeDTO);
    }

    private void persistirHabilidade(HabilidadeDTO habilidadeDTO) {
        Habilidade habilidade = new Habilidade();

        habilidade.setNome(habilidadeDTO.getNome());

        habilidadeRepository.persist(habilidade);
        habilidadeDTO.setId(habilidade.getId());
    }
}
