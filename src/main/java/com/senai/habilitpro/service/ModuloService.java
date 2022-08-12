package com.senai.habilitpro.service;

import com.senai.habilitpro.business.ModuloBusiness;
import com.senai.habilitpro.exception.ModuloException;
import com.senai.habilitpro.model.dto.ModuloDTO;
import com.senai.habilitpro.model.repository.ModuloRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class ModuloService {

    @Inject
    private ModuloBusiness moduloBusiness;

    @Inject
    private ModuloRepository moduloRepository;

    public void cadastrar(ModuloDTO moduloDTO) throws ModuloException {
        moduloBusiness.cadastrar(moduloDTO);
    }
}