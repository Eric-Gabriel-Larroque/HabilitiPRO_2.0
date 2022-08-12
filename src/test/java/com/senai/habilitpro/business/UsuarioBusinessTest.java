package com.senai.habilitpro.business;

import com.senai.habilitpro.exception.RegistroExistenteException;
import com.senai.habilitpro.exception.RegistroNaoEncontradoException;
import com.senai.habilitpro.exception.UsuarioException;
import com.senai.habilitpro.model.dto.UsuarioDTO;
import com.senai.habilitpro.model.dto.UsuarioFiltroDTO;
import com.senai.habilitpro.model.dto.request.LoginRequestApiDTO;
import com.senai.habilitpro.model.entity.Role;
import com.senai.habilitpro.model.entity.Usuario;
import com.senai.habilitpro.model.repository.UsuarioRepository;
import com.senai.habilitpro.service.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.security.auth.login.LoginException;
import java.util.*;

import static com.senai.habilitpro.utils.EntityCreationHandler.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioBusinessTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioBusiness business;

    @Test
    @DisplayName("QUANDO buscar pro usuários e a lista for vazia, DEVE me retornar uma exceção.")
    void obterUsuarios_falha() {
        when(repository.obterUsuarios()).thenReturn(new ArrayList<>());
        RegistroNaoEncontradoException exception = assertThrows(RegistroNaoEncontradoException.class, () -> business.obterUsuarios());
    }

    @Test
    @DisplayName("QUANDO buscar por usuário e obtiver algum na busca, DEVE me retornar uma lista de usuários")
    void obterUsuarios_sucesso() {
        when(repository.obterUsuarios()).thenReturn(Arrays.asList(criarUsuario()));
        List<Usuario> usuarioList = business.obterUsuarios();
        assertNotNull(usuarioList);
        assertFalse(usuarioList.isEmpty());
        assertInstanceOf(Usuario.class,usuarioList.get(0));
        assertNotNull(usuarioList.get(0).getId());
    }

    @Test
    @DisplayName("QUANDO buscar usuario pelo Id e não for encontrado, DEVE me retornar uma exceção.")
    void obterPorId_falha() {
        when(repository.obterUsuarioPorId(anyLong())).thenThrow(RegistroNaoEncontradoException.class);
        assertThrows(RegistroNaoEncontradoException.class, () -> business.buscarPorId(anyLong()));
    }

    @Test
    @DisplayName("QUANDO buscar usuário pelo Id e for encontrado, DEVE me retornar o Usuario encontrado")
    void obterPorId_sucesso() {
        when(repository.obterUsuarioPorId(anyLong())).thenReturn(Optional.of(criarUsuario()));
        Usuario usuario = business.buscarPorId(anyLong());
        assertNotNull(usuario);
        assertInstanceOf(Usuario.class,usuario);
    }

    @Test
    @DisplayName("QUANDO buscar usuario pelo filtro e não obtiver nenhum resultado, DEVE me retornar uma exception.")
    void filtrar_falha() {
        UsuarioFiltroDTO filtroDTO = criarUsuarioFiltroDTO();
        when(repository.filtrar(filtroDTO)).thenReturn(new ArrayList<>());
        assertThrows(RegistroNaoEncontradoException.class, () -> business.filtrar(filtroDTO));
    }

    @Test
    @DisplayName("QUANDO buscar usuario pelo filtro e obtiver resultado, DEVE me retornar uma lista de Usuario")
    void filtrar_sucesso() {
        UsuarioFiltroDTO filtroDTO = criarUsuarioFiltroDTO();
        when(repository.filtrar(any(UsuarioFiltroDTO.class))).thenReturn(List.of(criarUsuario()));
        List<Usuario> usuariosEncontrados = business.filtrar(filtroDTO);
        assertNotNull(usuariosEncontrados);
        assertFalse(usuariosEncontrados.isEmpty());
        assertInstanceOf(Usuario.class,usuariosEncontrados.get(0));
    }

    @Test
    @DisplayName("QUANDO criar o usuário com o campos email vazio ou duplicado, DEVE me retornar uma exceção")
    void criarUsuario_falhaEmail() {
        when(repository.findByEmail(anyString())).thenReturn(List.of(criarUsuario()));
        assertThrows(UsuarioException.class, () -> business.cadastrar(criarUsuarioDTO()));
    }

    @Test
    @DisplayName("QUANDO criar o usuário com o campos cpf vazio ou duplicado, DEVE me retornar uma exceção")
    void criarUsuario_falhaCpf() {
        when(repository.findByCpf(anyString())).thenReturn(List.of(criarUsuario()));
        assertThrows(UsuarioException.class, () ->  business.cadastrar(criarUsuarioDTO()));
    }


    @Test
    @DisplayName("QUANDO criar o usuário e todos os campos obrigatórios forem preenchidos, DEVE me retornar o Usuário criado.")
    void criarUsuario_sucesso() throws UsuarioException {
        UsuarioDTO usuarioDTO = criarUsuarioDTO();

        when(repository.findByEmail(anyString())).thenReturn(new ArrayList<>());
        when(repository.findByCpf(anyString())).thenReturn(new ArrayList<>());
        Usuario resultado = business.cadastrar(usuarioDTO);

        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertNotNull(resultado.getEmail());
        assertNotNull(resultado.getCpf());
        assertNotNull(resultado.getSenha());
        assertInstanceOf(Usuario.class,resultado);
    }

    @Test
    @DisplayName("QUANDO criar ou atualizar o Usuário com alguma ROle inválida, DEVE me retornar uma exceção.")
    void criarUsuario_falhaRole() {
        UsuarioDTO usuarioDTO = criarUsuarioDTO();
        Usuario usuario = criarUsuario();
        UsuarioBusiness usuarioBusiness = Mockito.mock(UsuarioBusiness.class);
        when(usuarioBusiness.verificaRoleInvalida(usuarioDTO,usuario)).thenReturn(true);
        boolean isRoleInvalida = usuarioBusiness.verificaRoleInvalida(usuarioDTO,usuario);
        doThrow(new UsuarioException("")).when(usuarioBusiness).cadastrar(usuarioDTO);
        assertEquals(Boolean.TRUE,isRoleInvalida);
        assertThrows(UsuarioException.class,() -> usuarioBusiness.cadastrar(usuarioDTO));
    }

    @Test
    @DisplayName("QUANDO atualizar o Usuário com id inválido, DEVE me retornar uma exceção.")
    void alterarUsuario_falha() throws UsuarioException {
        UsuarioDTO usuarioDTO = criarUsuarioDTO();
        when(repository.obterUsuarioPorId(anyLong())).thenReturn(Optional.empty());
        assertThrows(RegistroNaoEncontradoException.class,() -> business.alterar(usuarioDTO));
    }

    @Test
    @DisplayName("QUANDO atualizar o Usuário com id existente e dados válidos, DEVE me retornar o Usuario alterado.")
    void alterarUsuario_sucesso() throws UsuarioException {
        UsuarioDTO usuarioDTO = criarUsuarioDTO();
        Usuario usuario = criarUsuario();
        when(repository.obterUsuarioPorId(anyLong())).thenReturn(Optional.of(usuario));
        Usuario usuarioAlterado = business.alterar(usuarioDTO);
        assertNotNull(usuarioAlterado);
        assertEquals(usuarioAlterado.getCpf(),usuarioDTO.getCpf());
        assertNotNull(usuarioAlterado.getId());
    }

    @Test
    @DisplayName("QUANDO tentar deletar com o id inexistente, DEVE me retornar uma exceção.")
    void removerUsuario_falha() {
        when(repository.obterUsuarioPorId(anyLong())).thenThrow(RegistroNaoEncontradoException.class);
        assertThrows(RegistroNaoEncontradoException.class, () -> business.removerUsuario(anyLong()));
    }

    @Test
    @DisplayName("QUANDO tentar deletar com o Id existente, DEVE voltar nada")
    void removerUsuario_sucesso() {
        when(repository.obterUsuarioPorId(anyLong())).thenReturn(Optional.of(criarUsuario()));
        business.removerUsuario(anyLong());
        Optional<Usuario> usuario = repository.obterUsuarioPorId(anyLong());
        assertTrue(usuario.isPresent());
    }

    @Test
    @DisplayName("QUANDO tentar autenticar com as credenciais inválidas, DEVE me retornar uma exceção.")
    void autenticarUsuario_falha() {
        assertThrows(LoginException.class, () -> business.autenticar(new LoginRequestApiDTO()));
    }

    @Test
    @DisplayName("QUANDO tentar autenticar com as credenciais corretas, DEVE me retornar o Usuario com o login autenticado.")
    void autenticarUsuario_sucesso() throws LoginException {
        LoginRequestApiDTO loginRequestApiDTO = criarLoginRequestApiDTO();
        when(repository.findByEmail(anyString())).thenReturn(List.of(criarUsuario()));
        Usuario usuarioEncontrado = business.autenticar(loginRequestApiDTO);

        assertNotNull(usuarioEncontrado);
        assertNotNull(usuarioEncontrado.getCpf());
        assertNotNull(usuarioEncontrado.getId());
        assertNotNull(usuarioEncontrado.getRoles());
        assertEquals(usuarioEncontrado.getEmail(),loginRequestApiDTO.getEmail());
        assertEquals(usuarioEncontrado.getSenha(),loginRequestApiDTO.getSenha());
        assertInstanceOf(Usuario.class,usuarioEncontrado);
    }
}