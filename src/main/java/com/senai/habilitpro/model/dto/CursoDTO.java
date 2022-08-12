package com.senai.habilitpro.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CursoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String nome;

    private String apelido;

    @NotBlank(message = "{curso.ocupacao.notblank}")
    private String ocupacao;

    @NotNull(message = "{curso.empresa.notnull}")
    private Long idEmpresa;

    private EmpresaDTO empresa;

    private List<ModuloDTO> modulos = new ArrayList<>();

    private List<TrabalhadorDTO> trabalhadores = new ArrayList<>();

    @NotBlank(message = "{curso.descricao.notblank}")
    @Size(min = 10, max = 100, message = "{curso.descricao.minmax}")
    private String descricao;

    public CursoDTO() {
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

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getOcupacao() {
        return ocupacao;
    }

    public EmpresaDTO getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaDTO empresa) {
        this.empresa = empresa;
    }

    public void setOcupacao(String ocupacao) {
        this.ocupacao = ocupacao;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public List<ModuloDTO> getModulos() {
        return modulos;
    }

    public void setModulos(List<ModuloDTO> modulos) {
        this.modulos = modulos;
    }

    public List<TrabalhadorDTO> getTrabalhadores() {
        return trabalhadores;
    }

    public void setTrabalhadores(List<TrabalhadorDTO> trabalhadores) {
        this.trabalhadores = trabalhadores;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
