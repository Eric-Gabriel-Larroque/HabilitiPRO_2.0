package com.senai.habilitpro.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class RegistroNaoEncontradoException extends WebApplicationException {

    private Response.Status statusCode = Response.Status.NOT_FOUND;
    public RegistroNaoEncontradoException(String tipoRegistro, String identificador) {
        super(String.format("%s: Registro não encontrado com identificador: %s", tipoRegistro, identificador), Response.Status.NOT_FOUND);
    }

    public RegistroNaoEncontradoException(String tipoRegistro) {
        super(String.format("Nenhum registro do tipo %s encontrado.",tipoRegistro), Response.Status.NOT_FOUND);
    }

    public Response.Status getStatus() {
        return statusCode;
    }
}
