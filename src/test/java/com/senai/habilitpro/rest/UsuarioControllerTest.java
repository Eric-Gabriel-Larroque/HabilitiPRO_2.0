package com.senai.habilitpro.rest;

import com.senai.habilitpro.exception.RegistroExistenteException;
import com.senai.habilitpro.exception.RegistroNaoEncontradoException;
import com.senai.habilitpro.exception.UsuarioException;
import com.senai.habilitpro.model.dto.UsuarioDTO;
import com.senai.habilitpro.model.dto.UsuarioFiltroDTO;
import com.senai.habilitpro.model.dto.request.UsuarioRequestApiDTO;
import com.senai.habilitpro.model.dto.request.UsuarioUpdateRequestApiDTO;
import com.senai.habilitpro.model.dto.response.UsuarioResponseApiDTO;
import com.senai.habilitpro.model.entity.Usuario;
import com.senai.habilitpro.service.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import java.util.Arrays;
import java.util.List;

import static com.senai.habilitpro.utils.EntityCreationHandler.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {

    @Mock
    private UsuarioService service;

    @InjectMocks
    private UsuarioController controller;

    @Test
    @DisplayName("QUANDO não houver usuários disponíveis, DEVE me retornar uma exceção com Status 404")
    void obterUsuarios_falha() {
        doThrow(new RegistroNaoEncontradoException(Usuario.class.getSimpleName()))
                .when(service).obterUsuarios();
        assertThrows(RegistroNaoEncontradoException.class,() -> controller.obterUsuarios());
        int status = new RegistroNaoEncontradoException(Usuario.class.getSimpleName()).getStatus().getStatusCode();
        assertEquals(status,Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @DisplayName("QUANDO houver usuários disponiveis, DEVE me retornar uma lista de UsuarioApiResponse com Status 200")
    void obterUsuarios_sucesso() {
        Mockito.when(service.obterUsuarios()).thenReturn(Arrays.asList(criarUsuario(),criarUsuario()));
        Response response = controller.obterUsuarios();
        List<UsuarioResponseApiDTO> responseList = (List<UsuarioResponseApiDTO>) response.getEntity();

        assertNotNull(response);
        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
        assertInstanceOf(UsuarioResponseApiDTO.class,responseList.get(0));
        assertFalse(responseList.isEmpty());
    }

    @Test
    @DisplayName("QUANDO o Id for inexistente, DEVE me retornar uma exceção com status 404")
    void obterUsuarioPorId_falha() {
        Long id = 1L;
        Mockito.when(service.filtrar(any(UsuarioFiltroDTO.class))).thenThrow(RegistroNaoEncontradoException.class);
        assertThrows(RegistroNaoEncontradoException.class, () -> controller.obterUsuarioPorId(id));
        int status = new RegistroNaoEncontradoException(Usuario.class.getSimpleName()).getStatus().getStatusCode();
        assertEquals(status,Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @DisplayName("QUANDO o id informado existir, DEVE me retornar um UsuarioRespApiDTO com status 200")
    void obterUsuarioPorId_sucesso() {
        Mockito.when(service.filtrar(any(UsuarioFiltroDTO.class))).thenReturn(List.of(criarUsuario()));
        Response response = controller.obterUsuarioPorId(anyLong());

        UsuarioResponseApiDTO usuarioResponse = (UsuarioResponseApiDTO) response.getEntity();
        assertNotNull(response);
        assertNotNull(usuarioResponse);
        assertInstanceOf(UsuarioResponseApiDTO.class,usuarioResponse);
        assertNotNull(usuarioResponse.getId());
    }

    @Test
    @DisplayName("QUANDO inserir algum dado repetido, DEVE me retornar uma exceção")
    void criarUsuario_falhaRegistroExistenteException() throws UsuarioException {
        Mockito.when(service.cadastrar(Mockito.any(UsuarioDTO.class))).thenThrow(RegistroExistenteException.class);
        assertThrows(RegistroExistenteException.class, () -> controller.criarUsuario(new UsuarioRequestApiDTO()));
        int status = new RegistroExistenteException(Usuario.class.getSimpleName()).getStatus().getStatusCode();
        assertEquals(status,Response.Status.CONFLICT.getStatusCode());
    }

    @Test
    @DisplayName("QUANDO inserir algum dado inválido, DEVE me retornar uma exceção")
    void criarUsuario_falhaUsuarioException() throws UsuarioException {
        Mockito.when(service.cadastrar(Mockito.any(UsuarioDTO.class))).thenThrow(new UsuarioException(anyString()));
        assertThrows(RegistroExistenteException.class, () -> controller.criarUsuario(new UsuarioRequestApiDTO()));
    }

    @Test
    @DisplayName("QUANDO inserir os dados corretamente e sem duplicatas, DEVE me retornar um UsuarioRespApiDTO com status 201")
    void criarUsuario_sucesso() throws UsuarioException {
        Mockito.when(service.cadastrar(Mockito.any(UsuarioDTO.class))).thenReturn(criarUsuario());

        Response response = controller.criarUsuario(new UsuarioRequestApiDTO());
        UsuarioResponseApiDTO usuarioResponseApiDTO = (UsuarioResponseApiDTO) response.getEntity();
        assertNotNull(usuarioResponseApiDTO.getId());
        assertNotNull(response);
        assertInstanceOf(UsuarioResponseApiDTO.class,response.getEntity());
        assertEquals(Response.Status.CREATED.getStatusCode(),response.getStatus());
    }

    @Test
    @DisplayName("QUANDO inserir um dado já existente, DEVE me retornar uma exceção com status CONFLICT")
    void atualizarUsuario_falha() throws UsuarioException {
        long id = 1L;
        UsuarioUpdateRequestApiDTO usuarioUpdateRequestApiDTO = new UsuarioUpdateRequestApiDTO();
        when(service.alterar(any(UsuarioDTO.class))).thenThrow(new UsuarioException("Ocorreu um erro ao alterar usuário"));
        RegistroExistenteException result = assertThrows(RegistroExistenteException.class, () -> controller.alterarUsuario(id,usuarioUpdateRequestApiDTO));
        assertEquals(result.getStatus().getStatusCode(), Response.Status.CONFLICT.getStatusCode());
    }

    @Test
    @DisplayName("QUANDO inserir corretamente os dados não duplicados e com Id existente, DEVE me retornar um DTO de Usuario e Status 200")
    void atualizarUsuario_sucesso() throws UsuarioException {
        long id = 1L;
        UsuarioUpdateRequestApiDTO usuarioUpdateRequestApiDTO = criarUsuarioUpdateReqApiDTO();
        when(service.alterar(any(UsuarioDTO.class))).thenReturn(criarUsuario());
        Response response = controller.alterarUsuario(id,usuarioUpdateRequestApiDTO);
        UsuarioResponseApiDTO responseApiDTO = (UsuarioResponseApiDTO) response.getEntity();
        System.out.println(response.getEntity());
        assertNotNull(response);
        assertNotNull(responseApiDTO);
        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
        assertInstanceOf(UsuarioResponseApiDTO.class,responseApiDTO);
    }

    @Test
    @DisplayName("QUANDO o id for inexistente ou haja impossibilidade ao deletar, DEVE me retornar uma exceção com status 400")
    void deletarUsuario_falha() {
        doThrow(WebApplicationException.class).when(service).removerUsuario(anyLong());
        WebApplicationException exception = assertThrows(WebApplicationException.class, () -> controller.removerUsuario(anyLong()));
        assertEquals(exception.getResponse().getStatus(), Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    @DisplayName("QUANDO o existir e houver possibilidade de remoção, deve me retornar o body vazio com status 204")
    void deletarUsuario_sucesso() {
        doNothing().when(service).removerUsuario(anyLong());
        Response response = controller.removerUsuario(anyLong());
        assertNull(response.getEntity());
        assertEquals(response.getStatus(), Response.Status.NO_CONTENT.getStatusCode());
    }
}