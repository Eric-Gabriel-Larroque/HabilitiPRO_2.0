package com.senai.habilitpro.model.dto;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotEmpty(message = "{usuario.nome.notempty}")
    private String nome;

    @NotEmpty(message = "{usuario.cpf.notempty}")
    private String cpf;

    @NotEmpty(message = "{usuario.email.notempty}")
    private String email;

    @NotEmpty(message = "{usuario.senha.notempty}")
    private String senha;

    private Long idEmpresa;

    @NotEmpty(message = "{usuario.roles.notempty}")
    private List<Long> rolesId = new ArrayList<>();

    private List<RoleDTO> roles = new ArrayList<>();

    private EmpresaDTO empresa;

    public UsuarioDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        if (cpf != null) {
            return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
        }
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public List<Long> getRolesId() {
        return rolesId;
    }

    public void setRolesId(List<Long> rolesId) {
        this.rolesId = rolesId;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }

    public EmpresaDTO getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaDTO empresa) {
        this.empresa = empresa;
    }
}
