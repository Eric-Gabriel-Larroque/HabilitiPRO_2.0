package com.senai.habilitpro.security;

import com.senai.habilitpro.config.modelmapper.ModelMapperConfig;
import com.senai.habilitpro.model.dto.EmpresaDTO;
import com.senai.habilitpro.model.dto.LoginDTO;
import com.senai.habilitpro.model.dto.RoleDTO;
import com.senai.habilitpro.model.entity.Usuario;
import com.senai.habilitpro.model.repository.UsuarioRepository;
import com.senai.habilitpro.utils.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.SimpleAccountRealm;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShiroRealm extends SimpleAccountRealm {

    @Inject
    private UsuarioRepository usuarioRepository;
    @Inject
    private ModelMapperConfig modelMapper;

    public ShiroRealm() {
        setName("loginDTO");
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UserAuthenticationToken authToken = (UserAuthenticationToken) token;

        LoginDTO loginDTO = getUsuarioByEmail(authToken.getLoginDTO().getEmail());

        if (!StringUtils.validatePassword(loginDTO.getSenha(), authToken.getLoginDTO().getSenha())) {
            throw new IncorrectCredentialsException("Senha incorreta!");
        }

        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", loginDTO.getEmail());
        credentials.put("senha", loginDTO.getSenha());

        authToken.getLoginDTO().setSenha(loginDTO.getSenha());
        List<RoleDTO> roleDTOList = new ArrayList<>();
        try {
            Usuario usuario = usuarioRepository.consultarRolesPorIdUsuario(loginDTO.getId());
            roleDTOList = usuario.getRoles().stream().map(role -> modelMapper.map(role, RoleDTO.class)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new IncorrectCredentialsException("Usuário não encontrado!");
        }
        authToken.getLoginDTO().setRoles(roleDTOList);
        authToken.getLoginDTO().setEmpresa(loginDTO.getEmpresa());

        return new SimpleAuthenticationInfo(authToken.getLoginDTO(), credentials, ShiroRealm.class.getName());
    }

    private LoginDTO getUsuarioByEmail(String email) {
        Usuario usuario = null;

        try {
            usuario = usuarioRepository.getUsuarioByEmail(email);
        } catch (NoResultException e) {
            throw new IncorrectCredentialsException("Usuário não encontrado com esse e-mail!");
        }

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setId(usuario.getId());
        loginDTO.setEmail(usuario.getEmail());
        loginDTO.setSenha(usuario.getSenha());
        if (usuario.getEmpresa() != null) {
            loginDTO.setEmpresa(modelMapper.map(usuario.getEmpresa(), EmpresaDTO.class));
        }
        return loginDTO;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return UserAuthenticationToken.class.equals(token.getClass());
    }
}
