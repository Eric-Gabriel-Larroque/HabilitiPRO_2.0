package com.senai.habilitpro.rest;

import com.senai.habilitpro.exception.RegistroExistenteException;
import com.senai.habilitpro.exception.RegistroNaoEncontradoException;
import com.senai.habilitpro.model.dto.TrabalhadorFiltroDTO;
import com.senai.habilitpro.model.dto.request.TrabalhadorCreateDTO;
import com.senai.habilitpro.model.dto.request.TrabalhadorUpdateDTO;
import com.senai.habilitpro.model.dto.response.TrabalhadorSimpleResponseDTO;
import com.senai.habilitpro.model.entity.Trabalhador;
import com.senai.habilitpro.service.TrabalhadorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TrabalhadorControllerTest {

    @Mock
    private TrabalhadorService trabalhadorService;

    @InjectMocks
    private TrabalhadorController trabalhadorController;

    @Test
    @DisplayName("Quando buscar todos trabalhadores, retornar status 200 com array não vazio")
    void obterTodosTrabalhadores_sucesso() {
        List<Trabalhador> trabalhadores = List.of(new Trabalhador());
        when(trabalhadorService.buscarComFiltro(any(TrabalhadorFiltroDTO.class))).thenReturn(trabalhadores);
        Response resultado = trabalhadorController.buscarTodos(null);

        assertNotNull(resultado);
        assertNotNull(resultado.getEntity());
        assertEquals(Response.Status.OK.getStatusCode(), resultado.getStatus());
        List<TrabalhadorSimpleResponseDTO> trabalhadoresResponse = (List<TrabalhadorSimpleResponseDTO>) resultado.getEntity();
        assertInstanceOf(trabalhadoresResponse.getClass(), resultado.getEntity());
        assertInstanceOf(TrabalhadorSimpleResponseDTO.class, ((List<?>) resultado.getEntity()).get(0));
    }

    @Test
    @DisplayName("Quando buscar todos trabalhadores, retornar status 404 com array vazio")
    void obterTodosTrabalhadores_falha() {
        List<Trabalhador> trabalhadores = List.of();
        when(trabalhadorService.buscarComFiltro(any(TrabalhadorFiltroDTO.class))).thenThrow(new RegistroNaoEncontradoException(""));

        RegistroNaoEncontradoException erro = assertThrows(RegistroNaoEncontradoException.class, () -> {
            trabalhadorController.buscarTodos(null);
        });

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), erro.getResponse().getStatus());
    }

    @Test
    @DisplayName("Quando buscar trabalhador por id, retornar status 200 com objeto não vazio")
    void obterTrabalhadorPorId_sucesso() {
        Trabalhador trabalhador = new Trabalhador();
        when(trabalhadorService.buscarPorId(any(Long.class))).thenReturn(trabalhador);
        Response resultado = trabalhadorController.buscarPorId(1L);

        assertNotNull(resultado);
        assertNotNull(resultado.getEntity());
        assertEquals(Response.Status.OK.getStatusCode(), resultado.getStatus());
        TrabalhadorSimpleResponseDTO trabalhadorResponse = (TrabalhadorSimpleResponseDTO) resultado.getEntity();
        assertInstanceOf(trabalhadorResponse.getClass(), resultado.getEntity());
    }

    @Test
    @DisplayName("Quando buscar trabalhador por id, retornar status 404 com objeto vazio")
    void obterTrabalhadorPorId_falha() {
        when(trabalhadorService.buscarPorId(any(Long.class))).thenThrow(new RegistroNaoEncontradoException(""));
        RegistroNaoEncontradoException erro = assertThrows(RegistroNaoEncontradoException.class, () -> {
            trabalhadorController.buscarPorId(1L);
        });

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), erro.getResponse().getStatus());
    }

    @Test
    @DisplayName("Quando deletar trabalhador, retornar status 204")
    void deletarTrabalhador_sucesso() {
        Response resultado = trabalhadorController.removePorId(1L);

        assertNotNull(resultado);
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), resultado.getStatus());
    }

    @Test
    @DisplayName("Quando deletar trabalhador e der erro, retornar status 404")
    void deletarTrabalhador_falha() {
        doThrow(new RegistroNaoEncontradoException("")).when(trabalhadorService).removePorId(any(Long.class));
        RegistroNaoEncontradoException erro = assertThrows(RegistroNaoEncontradoException.class, () -> {
            trabalhadorController.removePorId(1L);
        });

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), erro.getResponse().getStatus());
    }

    @Test
    @DisplayName("Quando salvar trabalhador, retornar status 201")
    void salvarTrabalhador_sucesso() {
        Trabalhador trabalhador = new Trabalhador();
        when(trabalhadorService.cadastrar(any(Trabalhador.class))).thenReturn(trabalhador);
        Response resultado = trabalhadorController.cadastrar(new TrabalhadorCreateDTO());

        assertNotNull(resultado);
        assertNotNull(resultado.getEntity());
        assertEquals(Response.Status.CREATED.getStatusCode(), resultado.getStatus());
        TrabalhadorSimpleResponseDTO trabalhadorResponse = (TrabalhadorSimpleResponseDTO) resultado.getEntity();
        assertInstanceOf(trabalhadorResponse.getClass(), resultado.getEntity());
    }

    @Test
    @DisplayName("Quando salvar trabalhador e já tiver cnpj cadastrado, retornar status 409")
    void salvarTrabalhador_falha() {
        doThrow(new RegistroExistenteException("")).when(trabalhadorService).cadastrar(any(Trabalhador.class));
        RegistroExistenteException erro = assertThrows(RegistroExistenteException.class, () -> {
            trabalhadorController.cadastrar(new TrabalhadorCreateDTO());
        });

        assertEquals(Response.Status.CONFLICT.getStatusCode(), erro.getResponse().getStatus());
    }

    @Test
    @DisplayName("Quando atualizar trabalhador, retornar status 200")
    void atualizarTrabalhador_sucesso() throws IllegalAccessException {
        Trabalhador trabalhador = new Trabalhador();
        when(trabalhadorService.atualizar(anyLong(), any(Trabalhador.class))).thenReturn(trabalhador);
        Response resultado = trabalhadorController.atualizar(1L, new TrabalhadorUpdateDTO());

        assertNotNull(resultado);
        assertNotNull(resultado.getEntity());
        assertEquals(Response.Status.OK.getStatusCode(), resultado.getStatus());
        TrabalhadorSimpleResponseDTO trabalhadorResponse = (TrabalhadorSimpleResponseDTO) resultado.getEntity();
        assertInstanceOf(trabalhadorResponse.getClass(), resultado.getEntity());
    }

    @Test
    @DisplayName("Quando atualizar trabalhador e não encontrar, retornar status 404")
    void atualizarTrabalhador_falha() throws IllegalAccessException {
        doThrow(new RegistroNaoEncontradoException("")).when(trabalhadorService).atualizar(anyLong(), any(Trabalhador.class));
        RegistroNaoEncontradoException erro = assertThrows(RegistroNaoEncontradoException.class, () -> {
            trabalhadorController.atualizar(1L, new TrabalhadorUpdateDTO());
        });

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), erro.getResponse().getStatus());
    }
}