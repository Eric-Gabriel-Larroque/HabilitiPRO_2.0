package com.senai.habilitpro.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;

public class RegistroExistenteException extends WebApplicationException {

    private Response.Status statusCode = Response.Status.CONFLICT;
    public RegistroExistenteException(String tipoRegistro, String identificador) {
        super(String.format("%s: Registro existente com identificador: %s", tipoRegistro, identificador), Response.Status.CONFLICT);
    }
    public RegistroExistenteException(String mensagem) {
        super(mensagem, Response.Status.CONFLICT);
    }

    public RegistroExistenteException(List<String> mensagem) {
        super(mensagem.toString(), Response.Status.CONFLICT);
    }

    public Response.Status getStatus() {
        return statusCode;
    }
}
