package com.senai.habilitpro.rest;

import com.senai.habilitpro.config.mapstruct.EmpresaMapper;
import com.senai.habilitpro.exception.EmpresaException;
import com.senai.habilitpro.model.dto.EmpresaDTO;
import com.senai.habilitpro.model.dto.EmpresaFiltroDTO;
import com.senai.habilitpro.model.dto.request.EmpresaCreateApiDTO;
import com.senai.habilitpro.model.dto.request.EmpresaUpdateDTO;
import com.senai.habilitpro.model.dto.response.EmpresaResponseDTO;
import com.senai.habilitpro.model.entity.Empresa;
import com.senai.habilitpro.service.EmpresaService;
import com.senai.habilitpro.utils.Formatador;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class EmpresaControllerTest {

    @Mock
    private EmpresaService empresaService;

    @Mock
    private EmpresaMapper empresaMapper;

    @InjectMocks
    private EmpresaController empresaController;

    @Test
    @DisplayName("Quando buscar por empresas, retornar status OK com array não vazio")
    void obterTodasEmpresas_sucesso() {
        List<Empresa> empresas = List.of(new Empresa());

        when(empresaService.buscar(any(EmpresaFiltroDTO.class))).thenReturn(empresas);

        try (MockedStatic<Formatador> mockFormatador = mockStatic(Formatador.class)) {
            mockFormatador.when(() -> Formatador.cnpj(anyString())).thenReturn(anyString());
            Response resultado = empresaController.getTodasEmpresas(null);

            assertNotNull(resultado);
            assertNotNull(resultado.getEntity());
            assertEquals(Response.Status.OK.getStatusCode(), resultado.getStatus());
            List<EmpresaResponseDTO> empresasResponse = (List<EmpresaResponseDTO>) resultado.getEntity();
            assertInstanceOf(empresasResponse.getClass(), resultado.getEntity());
            assertInstanceOf(EmpresaResponseDTO.class, ((List<?>) resultado.getEntity()).get(0));
        }
    }

    @Test
    @DisplayName("Quando buscar por empresas, deve retornar status 404 ao não encontrar nenhuma empresa")
    void obterTodasEmpresas_falha() {
        String mensagemErro = "Não foi encontrado nenhuma empresa com os parâmetros informados";
        when(empresaService.buscar(any(EmpresaFiltroDTO.class))).thenThrow(new EmpresaException(mensagemErro, Response.Status.NOT_FOUND));

        EmpresaException erro = assertThrows(EmpresaException.class, () -> empresaController.getTodasEmpresas(null));
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), erro.getResponse().getStatus());
        assertEquals(mensagemErro, erro.getMessage());
    }

    @Test
    @DisplayName("Quando buscar empresa por id, devolver uma empresa com status 200")
    void obterEmpresaPorId_sucesso() {
        Long empresaId = 1L;
        lenient().when(empresaService.buscarPorId(empresaId)).thenReturn(new Empresa());
        try (MockedStatic<Formatador> mockFormatador = mockStatic(Formatador.class)) {
            mockFormatador.when(() -> Formatador.cnpj(anyString())).thenReturn(anyString());
            Response resposta = empresaController.getEmpresaById(empresaId);
            assertEquals(Response.Status.OK.getStatusCode(), resposta.getStatus());
            assertNotNull(resposta.getEntity());
            assertInstanceOf(EmpresaResponseDTO.class, resposta.getEntity());
        }
    }

    @Test
    @DisplayName("Quando buscar empresa por id e não encontrar, retorna erro 404")
    void obterEmpresaPorId_falha() {
        Long empresaId = 1L;
        String mensagemErro = "Empresa: Registro não encontrado com identificador: 1";
        when(empresaService.buscarPorId(empresaId)).thenThrow(new EmpresaException(mensagemErro, Response.Status.NOT_FOUND));

        EmpresaException erro = assertThrows(EmpresaException.class, () -> empresaController.getEmpresaById(empresaId));
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), erro.getResponse().getStatus());
        assertEquals(mensagemErro, erro.getMessage());
    }

    @Test
    @DisplayName("Ao deletar empresa com sucesso, retorna 204")
    void deletarEmpresa_sucesso() {
        Long empresaId = 1L;
        Response resposta = empresaController.removeEmpresaById(empresaId);
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), resposta.getStatus());
    }

    @Test
    @DisplayName("Ao deletar empresa com curso associado, retorna 409 com erro")
    void deletarEmpresa_falha() {
        Long empresaId = 1L;
        String mensagemErro = String.format("Não é possível excluir a empresa de id %s pois a mesma possui cursos vinculados.", empresaId.toString());
        doThrow(new EmpresaException(mensagemErro, Response.Status.CONFLICT)).when(empresaService).deleteById(empresaId);

        EmpresaException erro = assertThrows(EmpresaException.class, () -> empresaController.removeEmpresaById(empresaId));
        assertEquals(Response.Status.CONFLICT.getStatusCode(), erro.getResponse().getStatus());
        assertEquals(mensagemErro, erro.getMessage());
    }

    @Test
    @DisplayName("Ao cadastrar empresa com sucesso, retorna 201")
    void cadastrarEmpresa_sucesso() {
        when(empresaService.cadastrar(any(EmpresaDTO.class))).thenReturn(new Empresa());
        when(empresaMapper.toApiResponse(any(Empresa.class))).thenReturn(new EmpresaResponseDTO());
        try (MockedStatic<Formatador> mockFormatador = mockStatic(Formatador.class)) {
            mockFormatador.when(() -> Formatador.cnpj(anyString())).thenReturn("");
            Response resposta = empresaController.cadastrar(new EmpresaCreateApiDTO());
            assertEquals(Response.Status.CREATED.getStatusCode(), resposta.getStatus());
            assertNotNull(resposta.getEntity());
            assertInstanceOf(EmpresaResponseDTO.class, resposta.getEntity());
        }
    }

    @Test
    @DisplayName("Ao cadastrar empresa com erro, retorna status 409")
    void cadastrarEmpresa_falha() {
        when(empresaService.cadastrar(any(EmpresaDTO.class))).thenThrow(new EmpresaException("", Response.Status.CONFLICT));
        EmpresaException erro = assertThrows(EmpresaException.class, () -> empresaController.cadastrar(new EmpresaCreateApiDTO()));
        assertEquals(Response.Status.CONFLICT.getStatusCode(), erro.getResponse().getStatus());
    }

    @Test
    @DisplayName("Ao atualizar empresa com sucesso, retorna 200")
    void atualizarEmpresa_sucesso() throws NoSuchFieldException, IllegalAccessException {
        when(empresaService.atualizaEmpresa(anyLong(), any(Empresa.class))).thenReturn(new Empresa());
        when(empresaMapper.toApiResponse(any(Empresa.class))).thenReturn(new EmpresaResponseDTO());
        try (MockedStatic<Formatador> mockFormatador = mockStatic(Formatador.class)) {
            mockFormatador.when(() -> Formatador.cnpj(anyString())).thenReturn("");
            Response resposta = empresaController.atualizaEmpresaById(1L, new EmpresaUpdateDTO());
            assertEquals(Response.Status.OK.getStatusCode(), resposta.getStatus());
            assertNotNull(resposta.getEntity());
            assertInstanceOf(EmpresaResponseDTO.class, resposta.getEntity());
        }
    }

    @Test
    @DisplayName("Ao atualizar empresa com erro, retorna status 409")
    void atualizarEmpresa_falha() throws NoSuchFieldException, IllegalAccessException {
        when(empresaService.atualizaEmpresa(anyLong(), any(Empresa.class))).thenThrow(new EmpresaException("", Response.Status.CONFLICT));
        EmpresaException erro = assertThrows(EmpresaException.class, () -> empresaController.atualizaEmpresaById(1L, new EmpresaUpdateDTO()));
        assertEquals(Response.Status.CONFLICT.getStatusCode(), erro.getResponse().getStatus());
    }
}