package com.senai.habilitpro.service;

import com.senai.habilitpro.business.EnderecoBusiness;
import com.senai.habilitpro.model.dto.EnderecoDTO;
import com.senai.habilitpro.model.repository.EnderecoRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class EnderecoService {

    @Inject
    private EnderecoBusiness enderecoBusiness;

    @Inject
    private EnderecoRepository enderecoRepository;

    public void cadastrar(EnderecoDTO endereco) {
        enderecoBusiness.cadastrar(endereco);
    }
}
