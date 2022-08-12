package com.senai.habilitpro.model.dto;

import com.senai.habilitpro.enumerator.RegionalSenaiEnum;
import com.senai.habilitpro.enumerator.SegmentoEmpresaEnum;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EmpresaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "{empresa.nome.notblank}")
    private String nome;

    @NotBlank(message = "{empresa.cnpj.notblank}")
    private String cnpj;

    private boolean filial;

    private String nomeFilial;

    private UsuarioDTO supervisor;

    @NotNull(message = "{empresa.segmento.notnull}")
    @Enumerated(EnumType.STRING)
    private SegmentoEmpresaEnum segmento;

    @NotNull(message = "{empresa.regional.notnull}")
    @Enumerated(EnumType.STRING)
    private RegionalSenaiEnum regionalSenai;

    @NotNull(message = "{empresa.supervisor.notnull}")
    private Long idUsuario;

    private List<CursoDTO> cursos = new ArrayList<>();

    private List<TrabalhadorDTO> trabalhadores = new ArrayList<>();

    @NotNull(message = "{empresa.endereco.notnull}")
    private Long idEndereco;

    public EmpresaDTO() {
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public boolean isFilial() {
        return filial;
    }

    public void setFilial(boolean filial) {
        this.filial = filial;
    }

    public String getNomeFilial() {
        return nomeFilial;
    }

    public void setNomeFilial(String nomeFilial) {
        this.nomeFilial = nomeFilial;
    }

    public SegmentoEmpresaEnum getSegmento() {
        return segmento;
    }

    public void setSegmento(SegmentoEmpresaEnum segmento) {
        this.segmento = segmento;
    }

    public RegionalSenaiEnum getRegionalSenai() {
        return regionalSenai;
    }

    public void setRegionalSenai(RegionalSenaiEnum regionalSenai) {
        this.regionalSenai = regionalSenai;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public List<CursoDTO> getCursos() {
        return cursos;
    }

    public void setCursos(List<CursoDTO> cursos) {
        this.cursos = cursos;
    }

    public List<TrabalhadorDTO> getTrabalhadores() {
        return trabalhadores;
    }

    public void setTrabalhadores(List<TrabalhadorDTO> trabalhadores) {
        this.trabalhadores = trabalhadores;
    }

    public Long getIdEndereco() {
        return idEndereco;
    }

    public UsuarioDTO getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(UsuarioDTO supervisor) {
        this.supervisor = supervisor;
    }

    public void setIdEndereco(Long idEndereco) {
        this.idEndereco = idEndereco;
    }
}
