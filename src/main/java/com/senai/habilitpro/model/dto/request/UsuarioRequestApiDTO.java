package com.senai.habilitpro.model.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRequestApiDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "{usuario.nome.notblank}")
    private String nome;

    @NotBlank(message = "{usuario.cpf.notblank}")
    private String cpf;

    @NotBlank(message = "{usuario.email.notblank}")
    private String email;

    @NotBlank(message = "{usuario.senha.notblank}")
    private String senha;

    @NotEmpty(message = "{usuario.roles.notempty_api}")
    private List<Long> rolesId = new ArrayList<>();

    public UsuarioRequestApiDTO() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
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

    public List<Long> getRolesId() {
        return rolesId;
    }

    public void setRolesId(List<Long> rolesId) {
        this.rolesId = rolesId;
    }
}
