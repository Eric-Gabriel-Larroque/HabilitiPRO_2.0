package com.senai.habilitpro.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;

public class EmpresaException extends WebApplicationException {
    public EmpresaException(String mensagemErro, Response.Status status) {
        super(mensagemErro, status);
    }
}
