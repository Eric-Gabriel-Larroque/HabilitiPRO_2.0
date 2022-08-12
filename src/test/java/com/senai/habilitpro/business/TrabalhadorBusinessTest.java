package com.senai.habilitpro.business;

import com.senai.habilitpro.exception.RegistroExistenteException;
import com.senai.habilitpro.exception.RegistroNaoEncontradoException;
import com.senai.habilitpro.model.dto.TrabalhadorFiltroDTO;
import com.senai.habilitpro.model.entity.Empresa;
import com.senai.habilitpro.model.entity.Trabalhador;
import com.senai.habilitpro.model.repository.TrabalhadorRepository;
import com.senai.habilitpro.service.CursoService;
import com.senai.habilitpro.service.EmpresaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrabalhadorBusinessTest {

    @Mock
    private TrabalhadorRepository trabalhadorRepository;
    @Mock
    private EmpresaService empresaService;
    @Mock
    private CursoService cursoService;

    @InjectMocks
    private TrabalhadorBusiness trabalhadorBusiness;

    private Trabalhador trabalhador;
    private Trabalhador trabalhadorAtualizado;

    @BeforeEach
    void setUp() {
        trabalhador = new Trabalhador();
        trabalhador.setId(1L);
        trabalhador.setNome("Teste");
        trabalhador.setCpf("123456789");
        trabalhador.setEmpresa(new Empresa());
        trabalhador.getEmpresa().setId(1L);
        trabalhador.setSetor("Teste Setor");
        trabalhador.setFuncao("Teste Função");

        trabalhadorAtualizado = new Trabalhador();
        trabalhadorAtualizado.setId(1L);
        trabalhadorAtualizado.setNome("Nome Novo");
        trabalhadorAtualizado.setCpf("123456789");
        trabalhadorAtualizado.setEmpresa(new Empresa());
        trabalhadorAtualizado.getEmpresa().setId(2L);
        trabalhadorAtualizado.setSetor("Teste Setor Novo");
        trabalhadorAtualizado.setFuncao("Teste Função Novo");
    }

    @Test
    @DisplayName("Ao cadastrar trabalhador, não encontra id da empresa e joga erro")
    void cadastroTrabalhadorEmpresaNaoEncontrada_falha() {
        when(empresaService.buscarPorId(1L)).thenThrow(RegistroNaoEncontradoException.class);
        assertThrows(RegistroNaoEncontradoException.class, () -> trabalhadorBusiness.cadastrar(trabalhador));
    }

    @Test
    @DisplayName("Ao cadastrar trabalhador, cpf já está cadastrado no banco e joga erro")
    void cadastroTrabalhadorCpfJaCadastrado_falha() {
        when(trabalhadorRepository.findByCpf(anyString())).thenReturn(new Trabalhador());
        assertThrows(RegistroExistenteException.class, () -> trabalhadorBusiness.cadastrar(trabalhador));
    }

    @Test
    @DisplayName("Ao cadastrar trabalhador, cadastra com sucesso")
    void cadastroTrabalhador_sucesso() {
        when(trabalhadorRepository.findByCpf(anyString())).thenReturn(null);
        when(empresaService.buscarPorId(1L)).thenReturn(new Empresa());
        trabalhadorBusiness.cadastrar(trabalhador);
        verify(trabalhadorRepository, times(1)).persist(any(Trabalhador.class));
    }

    @Test
    @DisplayName("Ao buscar trabalhador com filtro, não encontra nenhum trabalhador e joga erro")
    void buscarTrabalhadorComFiltro_falha() {
        when(trabalhadorRepository.filtrar(any(TrabalhadorFiltroDTO.class))).thenReturn(List.of());
        assertThrows(RegistroNaoEncontradoException.class, () -> trabalhadorBusiness.buscarComFiltro(new TrabalhadorFiltroDTO()));
    }

    @Test
    @DisplayName("Ao buscar trabalhador com filtro, encontra trabalhador e retorna")
    void buscarTrabalhadorComFiltro_sucesso() {
        when(trabalhadorRepository.filtrar(any(TrabalhadorFiltroDTO.class))).thenReturn(List.of(trabalhador));
        List<Trabalhador> trabalhadoresEncontrados = trabalhadorBusiness.buscarComFiltro(new TrabalhadorFiltroDTO());
        assertEquals(List.of(trabalhador), trabalhadoresEncontrados);
        assertNotNull(trabalhadoresEncontrados);
        assertEquals(1, trabalhadoresEncontrados.size());
    }

    @Test
    @DisplayName("Ao buscar trabalhador por id, não encontra trabalhador e joga erro")
    void buscarTrabalhadorPorId_falha() {
        when(trabalhadorRepository.find(Trabalhador.class, 1L)).thenReturn(null);
        assertThrows(RegistroNaoEncontradoException.class, () -> trabalhadorBusiness.buscarPorId(1L));
    }

    @Test
    @DisplayName("Ao buscar trabalhador por id, encontra trabalhador e retorna")
    void buscarTrabalhadorPorId_sucesso() {
        when(trabalhadorRepository.find(Trabalhador.class, 1L)).thenReturn(trabalhador);
        Trabalhador trabalhadorEncontrado = trabalhadorBusiness.buscarPorId(1L);
        assertEquals(trabalhador, trabalhadorEncontrado);
        assertNotNull(trabalhadorEncontrado);
    }

    @Test
    @DisplayName("Ao remover trabalhador, não encontra trabalhador e joga erro")
    void removerTrabalhador_falha() {
        when(trabalhadorRepository.find(Trabalhador.class, 1L)).thenReturn(null);
        assertThrows(RegistroNaoEncontradoException.class, () -> trabalhadorBusiness.removePorId(1L));
    }

    @Test
    @DisplayName("Ao remover trabalhador, encontra trabalhador e remove")
    void removerTrabalhador_sucesso() {
        when(trabalhadorRepository.find(Trabalhador.class, 1L)).thenReturn(trabalhador);
        doNothing().when(cursoService).removeTrabalhadorDosCursos(any(Trabalhador.class));
        trabalhadorBusiness.removePorId(1L);
        verify(trabalhadorRepository, times(1)).remove(trabalhador);
    }

    @Test
    @DisplayName("Ao atualizar trabalhador, não encontra trabalhador e joga erro")
    void atualizarTrabalhadorNaoEncontrado_falha() {
        when(trabalhadorRepository.find(Trabalhador.class, 1L)).thenReturn(null);
        assertThrows(RegistroNaoEncontradoException.class, () -> trabalhadorBusiness.atualizar(1L, trabalhadorAtualizado));
    }

    @Test
    @DisplayName("Ao atualizar trabalhador nao encontra empresa e joga erro")
    void atualizarTrabalhadorEmpresaNaoEncontrada_falha() {
        when(trabalhadorRepository.find(Trabalhador.class, 1L)).thenReturn(trabalhador);
        when(empresaService.buscarPorId(2L)).thenThrow(RegistroNaoEncontradoException.class);
        assertThrows(RegistroNaoEncontradoException.class, () -> trabalhadorBusiness.atualizar(1L, trabalhadorAtualizado));
    }

    @Test
    @DisplayName("Ao atualizar trabalhador, encontra trabalhador e atualiza")
    void atualizarTrabalhador_sucesso() throws IllegalAccessException {
        when(trabalhadorRepository.find(Trabalhador.class, 1L)).thenReturn(trabalhador);
        when(empresaService.buscarPorId(2L)).thenReturn(new Empresa());
        Trabalhador trabalhadorAposAtualizar = trabalhadorBusiness.atualizar(1L, trabalhadorAtualizado);
        verify(trabalhadorRepository, times(1)).merge(any(Trabalhador.class));
        assertEquals(trabalhadorAtualizado.getNome(), trabalhadorAposAtualizar.getNome());
        assertEquals(trabalhadorAtualizado.getFuncao(), trabalhadorAposAtualizar.getFuncao());
    }
}