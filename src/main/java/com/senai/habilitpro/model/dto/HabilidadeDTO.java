package com.senai.habilitpro.model.dto;

import com.senai.habilitpro.model.entity.Habilidade;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class HabilidadeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long idModulo;

    @NotBlank(message = "{habilidade.nome.notblank}")
    private String nome;

    public HabilidadeDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdModulo() {
        return idModulo;
    }

    public void setIdModulo(Long idModulo) {
        this.idModulo = idModulo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
