package com.senai.habilitpro.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class UsuarioException extends WebApplicationException {

    private List<String> erros = new ArrayList<>();
    public UsuarioException(String mensagemErro, Response.Status status) {
        super(mensagemErro, status);
    }

    public UsuarioException(String erro) {
        super(erro);
    }

    public UsuarioException(List<String> erros) {
        super((Response) erros);
    }

    public List<String> getErros() {
        return erros;
    }
}
