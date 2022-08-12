package com.senai.habilitpro.service;

import com.senai.habilitpro.business.ShiroAuthenticationBusiness;
import com.senai.habilitpro.business.UsuarioBusiness;
import com.senai.habilitpro.exception.UsuarioException;
import com.senai.habilitpro.model.dto.*;
import com.senai.habilitpro.model.dto.request.LoginRequestApiDTO;
import com.senai.habilitpro.model.entity.Usuario;
import com.senai.habilitpro.model.repository.UsuarioRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.auth.login.LoginException;
import java.util.List;

@Stateless
public class UsuarioService {

    private static final Logger LOG = LogManager.getLogger(UsuarioService.class);

    private ModelMapper mapper = new ModelMapper();

    @Inject
    private UsuarioBusiness usuarioBusiness;

    @Inject
    private ShiroAuthenticationBusiness authenticationBusiness;

    @Inject
    private UsuarioRepository usuarioRepository;

    public List<UsuarioDTO> findUsuariosSemEmpresa() {
        LOG.info("Iniciando listagem de usuários que não possuem empresa...");
        List<Usuario> usuarios = usuarioRepository.findUsuariosSemEmpresa();
        return mapper.map(usuarios, List.class);
    }

    public Usuario cadastrar(UsuarioDTO usuario) throws UsuarioException {
        return usuarioBusiness.cadastrar(usuario);
    }

    public List<Usuario> filtrar(UsuarioFiltroDTO usuarioFiltro) {
        return this.usuarioBusiness.filtrar(usuarioFiltro);
    }

    public LoginDTO login(LoginDTO login) throws Exception {
        return authenticationBusiness.login(login);
    }

    public List<Usuario> obterUsuarios() {
        return usuarioBusiness.obterUsuarios();
    }

    public Usuario obterUsuarioPorId(long id) {
        return usuarioBusiness.buscarPorId(id);
    }

    public Usuario alterar(UsuarioDTO usuarioDTO) throws UsuarioException {
        return usuarioBusiness.alterar(usuarioDTO);
    }

    public void removerUsuario(long id) {
        usuarioBusiness.removerUsuario(id);
    }

    public Usuario autenticar(LoginRequestApiDTO login) throws LoginException {
        return usuarioBusiness.autenticar(login);
    }
}
