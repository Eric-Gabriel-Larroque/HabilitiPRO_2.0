package com.senai.habilitpro.rest;

import com.senai.habilitpro.config.mapstruct.UsuarioMapper;
import com.senai.habilitpro.model.dto.request.LoginRequestApiDTO;
import com.senai.habilitpro.model.entity.Usuario;
import com.senai.habilitpro.service.UsuarioService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.security.auth.login.LoginException;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.net.URI;

import static com.senai.habilitpro.security.JwtTokenGenerator.gerarLoginWebToken;

@Path("/v1/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.TEXT_PLAIN)
public class LoginController {

        private static final Logger LOG = LogManager.getLogger(LoginController.class);
        @Inject
        private UsuarioService service;
        @POST
        public Response autenticar(@Valid LoginRequestApiDTO login) {
                try {
                        LOG.info("Iniciando processo de autenticação de usuário");
                        Usuario usuarioEncontrado = service.autenticar(login);

                        LOG.info("Gerando JWT Token...");
                        String token = gerarLoginWebToken(UsuarioMapper.INSTANCE.toLoginApiDTO(usuarioEncontrado));
                        return Response.ok(URI.create(token)).entity(token).build();
                } catch (LoginException e) {
                        LOG.error("Não foi possível autenticar o usuário");
                        throw new WebApplicationException(e.getMessage(),Response.Status.UNAUTHORIZED);
                }
        }
}
