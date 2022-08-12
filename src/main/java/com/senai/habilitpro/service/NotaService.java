package com.senai.habilitpro.service;

import com.senai.habilitpro.business.NotaBusiness;
import com.senai.habilitpro.exception.AvaliacaoException;
import com.senai.habilitpro.exception.GenericException;
import com.senai.habilitpro.exception.NotaException;
import com.senai.habilitpro.model.dto.NotaDTO;
import com.senai.habilitpro.model.entity.Trabalhador;
import com.senai.habilitpro.model.repository.NotaRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class NotaService {

    @Inject
    private NotaBusiness notaBusiness;

    @Inject
    private NotaRepository notaRepository;

    public void cadastrar(NotaDTO notaDTO) throws GenericException {
        notaBusiness.cadastrar(notaDTO);
    }

    public void salvar(List<Trabalhador> trabalhadores, Long avaliacaoId) throws NotaException, AvaliacaoException {
        this.notaBusiness.salvar(trabalhadores, avaliacaoId);
    }
}
