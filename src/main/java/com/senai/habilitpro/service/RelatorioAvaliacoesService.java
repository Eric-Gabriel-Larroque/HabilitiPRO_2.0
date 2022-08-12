package com.senai.habilitpro.service;

import com.senai.habilitpro.business.RelatorioAvaliacoesBusiness;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.IOException;

@Stateless
public class RelatorioAvaliacoesService {

    @Inject
    private RelatorioAvaliacoesBusiness relatorioBusiness;

    public void imprimir() throws IOException {
        this.relatorioBusiness.imprimir();
    }
}
