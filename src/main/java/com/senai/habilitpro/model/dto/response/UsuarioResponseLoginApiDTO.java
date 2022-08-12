package com.senai.habilitpro.model.dto.response;

import com.senai.habilitpro.model.dto.RoleDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UsuarioResponseLoginApiDTO implements Serializable {

    private List<RoleDTO> roles = new ArrayList<>();

    private Long empresaId;

    public UsuarioResponseLoginApiDTO() {}

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }
}
