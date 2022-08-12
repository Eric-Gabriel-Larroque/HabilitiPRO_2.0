package com.senai.habilitpro.model.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class RoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "{role.id.notnull}")
    private Long id;

    private String nome;

    public RoleDTO() {
    }

    public RoleDTO(Long idRole, String nome) {
        this.id = idRole;
        this.nome = nome;
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
}
