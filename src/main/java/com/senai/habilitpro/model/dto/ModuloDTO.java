package com.senai.habilitpro.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ModuloDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "{modulo.nome.notblank}")
    private String nome;

    @NotNull(message = "{modulo.datainicio.notnull}")
    private LocalDate dataInicio;

    @NotNull(message = "{modulo.datatermino.notnull}")
    private LocalDate dataTermino;

    private List<HabilidadeDTO> habilidades = new ArrayList<>();

    private Long idAvaliacao;

    @NotNull(message = "{modulo.curso.notnull}")
    private Long idCurso;

    private CursoDTO curso;

    private AvaliacaoDTO avaliacao;

    public ModuloDTO() {
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

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(LocalDate dataTermino) {
        this.dataTermino = dataTermino;
    }

    public List<HabilidadeDTO> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(List<HabilidadeDTO> habilidades) {
        this.habilidades = habilidades;
    }

    public Long getIdAvaliacao() {
        return idAvaliacao;
    }

    public void setIdAvaliacao(Long idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }

    public Long getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Long idCurso) {
        this.idCurso = idCurso;
    }

    public CursoDTO getCurso() {
        return curso;
    }

    public void setCurso(CursoDTO curso) {
        this.curso = curso;
    }

    public AvaliacaoDTO getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(AvaliacaoDTO avaliacao) {
        this.avaliacao = avaliacao;
    }
}
