package com.senai.habilitpro.model.dto.request;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class LoginRequestApiDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "{usuario.email.notblank}")
    private String email;

    @NotBlank(message = "{usuario.senha.notblank}")
    private String senha;

    public LoginRequestApiDTO() {}

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
}
