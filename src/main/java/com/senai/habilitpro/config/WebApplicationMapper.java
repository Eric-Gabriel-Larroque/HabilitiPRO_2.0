package com.senai.habilitpro.config;

import com.senai.habilitpro.model.dto.response.ErroResponseDTO;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WebApplicationMapper implements ExceptionMapper<WebApplicationException> {

    @Override
    public Response toResponse(WebApplicationException exception) {
        String message = exception.getMessage();
        Response response = exception.getResponse();
        Response.Status status = response.getStatusInfo().toEnum();
        return Response.status(status).entity(new ErroResponseDTO(status.getStatusCode(), message)).type(MediaType.APPLICATION_JSON).build();
    }
}