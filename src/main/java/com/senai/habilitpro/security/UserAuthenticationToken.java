package com.senai.habilitpro.security;

import com.senai.habilitpro.enumerator.RoleEnum;
import com.senai.habilitpro.model.dto.LoginDTO;
import com.senai.habilitpro.model.dto.RoleDTO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

@Named
@RequestScoped
public class UserAuthenticationToken implements AuthenticationToken {

    private static final long serialVersionUID = 1756334491268582481L;

    private final LoginDTO loginDTO;

    public UserAuthenticationToken() {
        this.loginDTO = (LoginDTO) SecurityUtils.getSubject().getPrincipal();
    }

    public UserAuthenticationToken(LoginDTO loginDTO) {
        this.loginDTO = loginDTO;
    }

    public boolean possuiRole(RoleEnum role) {
        for (RoleDTO roleDTO : loginDTO.getRoles()) {
            if (roleDTO.getNome().equals(role.getNome())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object getPrincipal() {
        return this.loginDTO;
    }

    @Override
    public Object getCredentials() {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", loginDTO.getEmail());
        credentials.put("senha", loginDTO.getSenha());

        return credentials;
    }

    public LoginDTO getLoginDTO() {
        return loginDTO;
    }
}