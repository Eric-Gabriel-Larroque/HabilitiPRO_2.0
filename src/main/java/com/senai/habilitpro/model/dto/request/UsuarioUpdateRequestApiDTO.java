package com.senai.habilitpro.model.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

public class UsuarioUpdateRequestApiDTO {

    @NotBlank(message = "{usuario.nome.notblank}")
    private String nome;

    @NotBlank(message = "{usuario.email.notblank}")
    @Pattern(regexp =  "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$",
            message = "{usuario.email.regex}")
    private String email;

    @NotBlank(message = "{usuario.senha.notblank}")
    private String senha;

    @NotEmpty(message = "{usuario.roles.notempty_api}")
    private List<Long> rolesId = new ArrayList<>();

    public UsuarioUpdateRequestApiDTO(){}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
