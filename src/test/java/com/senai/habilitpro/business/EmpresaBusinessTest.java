package com.senai.habilitpro.business;

import com.senai.habilitpro.config.mapstruct.EmpresaMapper;
import com.senai.habilitpro.enumerator.RegionalSenaiEnum;
import com.senai.habilitpro.enumerator.SegmentoEmpresaEnum;
import com.senai.habilitpro.exception.EmpresaException;
import com.senai.habilitpro.exception.RegistroExistenteException;
import com.senai.habilitpro.exception.RegistroNaoEncontradoException;
import com.senai.habilitpro.model.dto.EmpresaDTO;
import com.senai.habilitpro.model.dto.EmpresaFiltroDTO;
import com.senai.habilitpro.model.dto.TrabalhadorFiltroDTO;
import com.senai.habilitpro.model.entity.*;
import com.senai.habilitpro.model.repository.EmpresaRepository;
import com.senai.habilitpro.service.TrabalhadorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpresaBusinessTest {

    @Mock
    private EmpresaRepository empresaRepository;
    @Mock
    private CursoBusiness cursoBusiness;
    @Mock
    private TrabalhadorService trabalhadorService;
    @Mock
    private UsuarioBusiness usuarioBusiness;
    @Mock
    private EnderecoBusiness enderecoBusiness;

    @InjectMocks
    private EmpresaBusiness empresaBusiness;

    private EmpresaDTO empresaDTO;
    private Empresa empresa;
    private Empresa empresaDesatualizada;
    private Usuario usuario;
    private Endereco endereco;

    @BeforeEach
    void setUp() {
        empresaDTO = new EmpresaDTO();
        empresaDTO.setNome("Empresa Teste");
        empresaDTO.setCnpj("12345678901234");
        empresaDTO.setFilial(false);
        empresaDTO.setNomeFilial("Empresa Teste");
        empresaDTO.setRegionalSenai(RegionalSenaiEnum.CENTRO_NORTE);
        empresaDTO.setSegmento(SegmentoEmpresaEnum.CONSTRUCAO);
        empresaDTO.setIdEndereco(1L);
        empresaDTO.setIdUsuario(1L);

        empresa = EmpresaMapper.INSTANCE.fromGenericDtoToModel(empresaDTO);

        empresaDesatualizada = EmpresaMapper.INSTANCE.fromGenericDtoToModel(empresaDTO);
        empresaDesatualizada.setNome("Empresa Teste Alterada");
        empresaDesatualizada.getSupervisor().setId(2L);
        empresaDesatualizada.getEndereco().setId(2L);


        usuario = new Usuario();

        endereco = new Endereco();
    }

    @Test
    @DisplayName("Ao cadastrar empresa, deve dar erro com CNPJ já cadastrado")
    void cadastrarEmpresaCnpjRepetido_falha() {
        List<Empresa> empresas = List.of(new Empresa());
        when(empresaRepository.verificaCnpjRepetido(anyString())).thenReturn(empresas);
        assertThrows(RegistroExistenteException.class, () -> empresaBusiness.cadastrar(empresaDTO));
    }

    @Test
    @DisplayName("Ao cadastrar empresa, não encontrou endereço e deve dar erro por endereço não encontrado")
    void cadastrarEmpresaEnderecoNaoEncontrado_falha() {
        when(enderecoBusiness.buscarPorId(anyLong())).thenThrow(RegistroNaoEncontradoException.class);
        assertThrows(RegistroNaoEncontradoException.class, () -> empresaBusiness.cadastrar(empresaDTO));
    }

    @Test
    @DisplayName("Ao cadastrar empresa, não encontrou usuário supervisor e deve dar erro por usuário não encontrado")
    void cadastrarEmpresaUsuarioNaoEncontrado_falha() {
        when(usuarioBusiness.buscarPorId(anyLong())).thenThrow(RegistroNaoEncontradoException.class);
        assertThrows(RegistroNaoEncontradoException.class, () -> empresaBusiness.cadastrar(empresaDTO));
    }

    @Test
    @DisplayName("Ao cadastrar empresa, deve retornar um objeto Empresa")
    void cadastrarEmpresa_sucesso() {
        when(empresaRepository.verificaCnpjRepetido(anyString())).thenReturn(new ArrayList<>());
        when(enderecoBusiness.buscarPorId(anyLong())).thenReturn(new Endereco());
        when(usuarioBusiness.buscarPorId(anyLong())).thenReturn(new Usuario());
        doNothing().when(empresaRepository).persist(any(Empresa.class));

        Empresa empresaCadastrada = empresaBusiness.cadastrar(empresaDTO);

        assertNotNull(empresaCadastrada);
        assertEquals(empresaCadastrada.getNome(), empresa.getNome());
        assertEquals(empresaCadastrada.isFilial(), empresa.isFilial());
        assertEquals(empresaCadastrada.getNomeFilial(), empresa.getNomeFilial());
        assertEquals(empresaCadastrada.getRegionalSenai(), empresa.getRegionalSenai());
        assertEquals(empresaCadastrada.getSegmento(), empresa.getSegmento());
    }

    @Test
    @DisplayName("Ao deletar empresa, erro por ter cursos vinculados")
    void deletarEmpresaComCursosVinculados_falha() {
        when(empresaRepository.find(Empresa.class, 1L)).thenReturn(empresa);
        when(cursoBusiness.getByEmpresaId(anyLong())).thenReturn(List.of(new Curso()));
        assertThrows(RegistroExistenteException.class, () -> empresaBusiness.deleteById(1L));
    }

    @Test
    @DisplayName("Ao deletar empresa, erro por ter trabalhadores vinculados")
    void deletarEmpresaComTrabalhadoresVinculados_falha() {
        when(empresaRepository.find(Empresa.class, 1L)).thenReturn(empresa);
        when(trabalhadorService.buscarComFiltro(any(TrabalhadorFiltroDTO.class))).thenReturn(List.of(new Trabalhador()));
        assertThrows(RegistroExistenteException.class, () -> empresaBusiness.deleteById(1L));
    }

    @Test
    @DisplayName("Ao deletar empresa, deve deletar")
    void deletarEmpresa_sucesso() {
        when(empresaRepository.find(Empresa.class, 1L)).thenReturn(empresa);
        doNothing().when(empresaRepository).remove(any(Empresa.class));
        empresaBusiness.deleteById(1L);
    }

    @Test
    @DisplayName("Ao buscar empresa por id e não encontrar, deve dar erro")
    void buscarEmpresaPorIdNaoEncontrado_falha() {
        when(empresaRepository.find(Empresa.class, 1L)).thenReturn(null);
        assertThrows(RegistroNaoEncontradoException.class, () -> empresaBusiness.buscarPorId(1L));
    }

    @Test
    @DisplayName("Ao buscar empresa por id, deve retornar um objeto Empresa")
    void buscarEmpresaPorId_sucesso() {
        when(empresaRepository.find(Empresa.class, 1L)).thenReturn(empresa);
        Empresa empresaBuscada = empresaBusiness.buscarPorId(1L);
        assertNotNull(empresaBuscada);
        assertEquals(empresaBuscada.getNome(), empresa.getNome());
        assertEquals(empresaBuscada.isFilial(), empresa.isFilial());
        assertEquals(empresaBuscada.getNomeFilial(), empresa.getNomeFilial());
        assertEquals(empresaBuscada.getRegionalSenai(), empresa.getRegionalSenai());
        assertEquals(empresaBuscada.getSegmento(), empresa.getSegmento());
    }

    @Test
    @DisplayName("Busca todas as empresas")
    void buscarTodasEmpresas_sucesso() {
        when(empresaRepository.getTodasEmpresas()).thenReturn(List.of(empresa));
        List<Empresa> empresas = empresaBusiness.getTodasEmpresas();
        assertNotNull(empresas);
        assertEquals(empresas.size(), 1);
        assertEquals(empresas.get(0).getNome(), empresa.getNome());
        assertEquals(empresas.get(0).isFilial(), empresa.isFilial());
        assertEquals(empresas.get(0).getNomeFilial(), empresa.getNomeFilial());
        assertEquals(empresas.get(0).getRegionalSenai(), empresa.getRegionalSenai());
        assertEquals(empresas.get(0).getSegmento(), empresa.getSegmento());
    }

    @Test
    @DisplayName("Busca empresa com filtro informado, retorna empresas com sucesso")
    void buscarEmpresaPorFiltro_sucesso() {
        when(empresaRepository.buscarComFiltro(any(EmpresaFiltroDTO.class))).thenReturn(List.of(empresa));
        EmpresaFiltroDTO filtro = new EmpresaFiltroDTO();
        filtro.setNome("nome");
        List<Empresa> empresas = empresaBusiness.buscarComFiltro(filtro);
        assertNotNull(empresas);
        assertEquals(empresas.size(), 1);
        assertEquals(empresas.get(0).getNome(), empresa.getNome());
        assertEquals(empresas.get(0).isFilial(), empresa.isFilial());
        assertEquals(empresas.get(0).getNomeFilial(), empresa.getNomeFilial());
        assertEquals(empresas.get(0).getRegionalSenai(), empresa.getRegionalSenai());
        assertEquals(empresas.get(0).getSegmento(), empresa.getSegmento());
    }

    @Test
    @DisplayName("Busca empresa com filtro informado não retorna nenhuma empresa, deve dar erro")
    void buscarEmpresaPorFiltro_falha() {
        when(empresaRepository.buscarComFiltro(any(EmpresaFiltroDTO.class))).thenReturn(List.of());
        EmpresaFiltroDTO filtro = new EmpresaFiltroDTO();
        filtro.setNome("nome");
        assertThrows(RegistroNaoEncontradoException.class, () -> empresaBusiness.buscarComFiltro(filtro));
    }

    @Test
    @DisplayName("Ao atualizar empresa com CNPJ já cadastrado, deve dar erro")
    void atualizarEmpresaComCnpjJaCadastrado_falha() {
        when(empresaRepository.find(Empresa.class, 1L)).thenReturn(empresa);
        when(empresaRepository.verificaCnpjRepetido(anyString())).thenReturn(List.of(new Empresa()));
        assertThrows(RegistroExistenteException.class, () -> empresaBusiness.atualizaEmpresa(1L, empresa));
    }

    @Test
    @DisplayName("Ao atualizar empresa com supervisor inexistente, deve dar erro")
    void atualizarEmpresaComSupervisorInexistente_falha() {
        when(empresaRepository.find(Empresa.class, 1L)).thenReturn(empresaDesatualizada);
        when(empresaRepository.verificaCnpjRepetido(anyString())).thenReturn(List.of());
        when(usuarioBusiness.buscarPorId(anyLong())).thenThrow(RegistroNaoEncontradoException.class);
        assertThrows(RegistroNaoEncontradoException.class, () -> empresaBusiness.atualizaEmpresa(1L, empresa));
    }

    @Test
    @DisplayName("Ao atualizar empresa com supervisor já cadastrado em outra empresa, deve dar erro")
    void atualizarEmpresaComSupervisorJaCadastradoEmOutraEmpresa_falha() {
        usuario.setEmpresa(new Empresa());
        usuario.getEmpresa().setId(2L);

        when(empresaRepository.find(Empresa.class, 1L)).thenReturn(empresaDesatualizada);
        when(empresaRepository.verificaCnpjRepetido(anyString())).thenReturn(List.of());
        when(usuarioBusiness.buscarPorId(anyLong())).thenReturn(usuario);
        assertThrows(EmpresaException.class, () -> empresaBusiness.atualizaEmpresa(1L, empresa));
    }

    @Test
    @DisplayName("Ao atualizar empresa com endereço não cadastrado, deve dar erro")
    void atualizarEmpresaComEnderecoNaoCadastrado_falha() {
        when(empresaRepository.find(Empresa.class, 1L)).thenReturn(empresaDesatualizada);
        when(empresaRepository.verificaCnpjRepetido(anyString())).thenReturn(List.of());
        when(usuarioBusiness.buscarPorId(anyLong())).thenReturn(usuario);
        when(enderecoBusiness.buscarPorId(anyLong())).thenThrow(RegistroNaoEncontradoException.class);
        assertThrows(RegistroNaoEncontradoException.class, () -> empresaBusiness.atualizaEmpresa(1L, empresa));
    }

    @Test
    @DisplayName("Ao atualizar empresa com endereço já cadastrado em outra empresa, deve dar erro")
    void atualizarEmpresaComEnderecoJaCadastradoEmOutraEmpresa_falha() {
        endereco.setEmpresa(new Empresa());
        endereco.getEmpresa().setId(2L);

        when(empresaRepository.find(Empresa.class, 1L)).thenReturn(empresaDesatualizada);
        when(empresaRepository.verificaCnpjRepetido(anyString())).thenReturn(List.of());
        when(usuarioBusiness.buscarPorId(anyLong())).thenReturn(usuario);
        when(enderecoBusiness.buscarPorId(anyLong())).thenReturn(endereco);
        assertThrows(EmpresaException.class, () -> empresaBusiness.atualizaEmpresa(1L, empresa));
    }

    @Test
    @DisplayName("Atualiza empreza com sucesso e altera usuario supervisor e nome empresa")
    void atualizaEmpresaAlterandoSupervisorENome_sucesso() throws IllegalAccessException {
        when(empresaRepository.find(Empresa.class, 1L)).thenReturn(empresaDesatualizada);
        when(empresaRepository.verificaCnpjRepetido(anyString())).thenReturn(List.of());
        when(usuarioBusiness.buscarPorId(anyLong())).thenReturn(usuario);
        when(enderecoBusiness.buscarPorId(anyLong())).thenReturn(endereco);
        when(empresaRepository.merge(any(Empresa.class))).thenReturn(empresa);

        Empresa empresaAtualizada = empresaBusiness.atualizaEmpresa(1L, empresa);

        assertEquals(empresaAtualizada.getNome(), empresa.getNome());
        assertEquals(empresa.getSupervisor().getId(), empresaAtualizada.getSupervisor().getId());
    }

    @Test
    @DisplayName("Atualiza empreza com sucesso sem alterar supervisor e endereço")
    void atualizaEmpresaSemAlterarSupervisor_sucesso() throws IllegalAccessException {
        empresa.getSupervisor().setId(null);
        empresa.getEndereco().setId(null);
        when(empresaRepository.find(Empresa.class, 1L)).thenReturn(empresaDesatualizada);
        when(empresaRepository.verificaCnpjRepetido(anyString())).thenReturn(List.of());
        when(empresaRepository.merge(any(Empresa.class))).thenReturn(empresa);

        Empresa empresaAtualizada = empresaBusiness.atualizaEmpresa(1L, empresa);

        assertEquals(empresaAtualizada.getNome(), empresa.getNome());
        assertEquals(empresaDesatualizada.getSupervisor().getId(), empresaAtualizada.getSupervisor().getId());
    }
}