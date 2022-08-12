package com.senai.habilitpro.security;

import com.senai.habilitpro.exception.UsuarioException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
@Provider
@Authorize
@Priority(Priorities.AUTHENTICATION)
public class AuthJwtFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null && JwtTokenGenerator.getEmpresaId() == 0)
            throw new UsuarioException("Faça login para acessar as requisições - Lembre-se de inserir o Bearer Token na Header da requisição.", Response.Status.UNAUTHORIZED);
        if (!JwtTokenGenerator.isAdmin())
            throw new UsuarioException("Acesso exclusivo para administradores", Response.Status.UNAUTHORIZED);
    }
}