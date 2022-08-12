package com.senai.habilitpro.rest;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.senai.habilitpro.model.dto.request.LoginRequestApiDTO;
import com.senai.habilitpro.service.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.security.auth.login.LoginException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import static com.senai.habilitpro.utils.EntityCreationHandler.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {

    @Mock
    private UsuarioService service;

    @InjectMocks
    private LoginController controller;

    @Test
    @DisplayName("QUANDO tentar logar com as credenciais inválidas, DEVE me retornar uma exception com status 401")
    void login_falha() throws Exception {
        LoginRequestApiDTO credenciais = criarLoginRequestApiDTO();
        assertNotNull(credenciais);
        when(service.autenticar(credenciais)).thenThrow(new LoginException("Erro ao efetuar login: Email ou senha inválidos."));
        WebApplicationException exception = assertThrows(WebApplicationException.class,()->controller.autenticar(credenciais));
        assertEquals(exception.getResponse().getStatus(), Response.Status.UNAUTHORIZED.getStatusCode());
    }

    @Test
    @DisplayName("QUANDO as credenciais forem válidas, DEVE me retornar um token JWT com status 200")
    void login_sucesso() throws LoginException, JsonProcessingException {
        LoginRequestApiDTO credenciais = criarLoginRequestApiDTO();

        when(service.autenticar(credenciais)).thenReturn(criarUsuario());
        Response response = controller.autenticar(credenciais);
        assertNotNull(response);
        assertNotNull(response.getEntity());
        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
    }

}
