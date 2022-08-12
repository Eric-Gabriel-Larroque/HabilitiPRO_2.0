package com.senai.habilitpro.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class TrabalhadorException extends WebApplicationException {
    public TrabalhadorException(String mensagemErro, Response.Status status) {
        super(mensagemErro, status);
    }
}
