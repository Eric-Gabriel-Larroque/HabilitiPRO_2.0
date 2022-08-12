package com.senai.habilitpro.model.dto;

import com.senai.habilitpro.enumerator.AvaliacaoStatusEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long idModulo;

    private ModuloDTO modulo;

    private List<NotaDTO> notas = new ArrayList<>();

    private AvaliacaoStatusEnum status;

    public AvaliacaoDTO() {
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

    public List<NotaDTO> getNotas() {
        return notas;
    }

    public void setNotas(List<NotaDTO> notas) {
        this.notas = notas;
    }

    public AvaliacaoStatusEnum getStatus() {
        return status;
    }

    public void setStatus(AvaliacaoStatusEnum status) {
        this.status = status;
    }


    public ModuloDTO getModulo() {
        return modulo;
    }

    public void setModulo(ModuloDTO modulo) {
        this.modulo = modulo;
    }

    public String getNomeCurso() {
        return this.modulo.getCurso().getNome();
    }

    public String getNomeModulo() {
        return this.modulo.getNome();
    }

    public String getNomeEmpresa() {
        return this.modulo.getCurso().getEmpresa().getNome();
    }
}
