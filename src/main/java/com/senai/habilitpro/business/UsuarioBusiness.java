package com.senai.habilitpro.business;

import com.senai.habilitpro.exception.RegistroNaoEncontradoException;
import com.senai.habilitpro.exception.UsuarioException;
import com.senai.habilitpro.model.dto.*;
import com.senai.habilitpro.model.dto.request.LoginRequestApiDTO;
import com.senai.habilitpro.model.entity.Role;
import com.senai.habilitpro.model.entity.Usuario;
import com.senai.habilitpro.model.repository.UsuarioRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import javax.inject.Inject;
import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.senai.habilitpro.utils.StringUtils.validateCredentials;

public class UsuarioBusiness {

    private static final Logger LOG = LogManager.getLogger(UsuarioBusiness.class);

    private final ModelMapper mapper = new ModelMapper();

    @Inject
    private UsuarioRepository usuarioRepository;

    public Usuario cadastrar(UsuarioDTO usuario) throws UsuarioException {
        LOG.info("Iniciando cadastro de usuário...");

        LOG.info("Verificando se já possui usuário com o CPF informado...");
        if (usuarioRepository.findByCpf(usuario.getCpf()).size() > 0) {
            throw new UsuarioException("Usuário já cadastrado com esse CPF");
        }

        LOG.info("Verificando se já possui usuário com o email informado...");
        if(usuarioRepository.findByEmail(usuario.getEmail()).size() > 0) {
            throw new UsuarioException("Email já cadastrado");
        }

        LOG.info("Populando atributos de usuário...");
        Usuario usuarioEntity = mapper.map(usuario, Usuario.class);
        usuarioEntity.setRoles(new ArrayList<>());
        if(verificaRoleInvalida(usuario,usuarioEntity))
            throw new UsuarioException("Role(s) inválida(s)");

        usuarioRepository.persist(usuarioEntity);
        LOG.info("usuário persistido com sucesso!");

        return usuarioEntity;
    }

    public List<Usuario> filtrar(UsuarioFiltroDTO usuarioFiltro) {
        LOG.info("Iniciando query de busca de usuários...");
        List<Usuario> usuariosEncontrados = usuarioRepository.filtrar(usuarioFiltro);
        if(usuariosEncontrados.isEmpty())
            throw new RegistroNaoEncontradoException(Usuario.class.getSimpleName());
        return usuariosEncontrados;
    }

    public List<Usuario> obterUsuarios() {
        List<Usuario> usuarios = usuarioRepository.obterUsuarios();
        LOG.info("Verificando se há usuários disponíveis");
        if(usuarios.isEmpty()){
            LOG.error("Não há usuários disponíveis para listagem");
            throw new RegistroNaoEncontradoException(Usuario.class.getSimpleName());
        }
        return usuarios;
    }

    public Usuario buscarPorId(long id) {
        LOG.info("Verificando se existe o usuário selecionado no banco...");
        Optional<Usuario> usuarioOpt = usuarioRepository.obterUsuarioPorId(id);
        return usuarioOpt.orElseThrow(()->new RegistroNaoEncontradoException(Usuario.class.getSimpleName(),String.valueOf(id)));
    }

    public Usuario alterar(UsuarioDTO usuarioDTO) throws UsuarioException {
        Usuario usuario = buscarPorId(usuarioDTO.getId());
        verificaEmailExistente(usuarioDTO);
        verificaRoleInvalida(usuarioDTO,usuario);
        LOG.info("Populando atributos...");
        usuario.setNome(usuarioDTO.getNome());
        usuario.setSenha(usuarioDTO.getSenha());
        usuario.setEmail(usuarioDTO.getEmail());
        return usuario;
    }

    public void verificaEmailExistente(UsuarioDTO usuarioDTO) throws UsuarioException {
        LOG.info("Verificando se o email inserido é duplicado...");
        List<Usuario> usuarioEncontrado = usuarioRepository.findByEmail(usuarioDTO.getEmail());
        if(!usuarioEncontrado.isEmpty() && !Objects.equals(usuarioEncontrado.get(0).getId(), usuarioDTO.getId()))
            throw new UsuarioException("Email já cadastrado");
    }

    public boolean verificaRoleInvalida(UsuarioDTO usuarioDTO, Usuario usuario) throws UsuarioException {
        LOG.info("Verificando se alguma role inserido é invalida...");
        usuario.setRoles(new ArrayList<>());
        for(long roleId: usuarioDTO.getRolesId()) {
            usuario.getRoles().add(usuarioRepository.find(Role.class,roleId));
        }
        return usuario.getRoles().stream().anyMatch(Objects::isNull);
    }

    public String removerUsuario(long id) {
        Usuario usuario = buscarPorId(id);
        LOG.error("Removendo usuário...");
        usuarioRepository.remove(usuario);
        return null;
    }

    public Usuario autenticar(LoginRequestApiDTO login) throws LoginException {
        List<Usuario> usuarioEncontrado = usuarioRepository.findByEmail(login.getEmail());
        LOG.info("Validando credenciais...");
        boolean credenciaisCorretas = validateCredentials(usuarioEncontrado,login);
        if(credenciaisCorretas)
            return usuarioEncontrado.get(0);
        throw new LoginException("Erro ao efetuar login: Email ou senha inválidos.");
    }
}