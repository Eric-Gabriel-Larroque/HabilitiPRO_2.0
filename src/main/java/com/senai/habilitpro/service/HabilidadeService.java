package com.senai.habilitpro.service;

import com.senai.habilitpro.business.HabilidadeBusiness;
import com.senai.habilitpro.exception.GenericException;
import com.senai.habilitpro.model.dto.HabilidadeDTO;
import com.senai.habilitpro.model.repository.HabilidadeRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class HabilidadeService {

    @Inject
    private HabilidadeBusiness habilidadeBusiness;

    @Inject
    private HabilidadeRepository habilidadeRepository;

    public void cadastrar(HabilidadeDTO habilidadeDTO) throws GenericException {
        habilidadeBusiness.cadastrar(habilidadeDTO);
    }

}
