package com.senai.habilitpro.model.dto;

import com.senai.habilitpro.enumerator.RegionalSenaiEnum;
import com.senai.habilitpro.enumerator.SegmentoEmpresaEnum;
import com.senai.habilitpro.model.entity.Empresa;

import java.io.Serializable;

public class EmpresaFiltroDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String cnpj;

    private String nome;

    private boolean filial;

    private String nomeFilial;

    private SegmentoEmpresaEnum segmento;

    private RegionalSenaiEnum regionalSenai;

    private Long idUsuario;

    private String nomeSupervisor;

    private int quantidadeCursos;

    public EmpresaFiltroDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getNomeSupervisor() {
        return nomeSupervisor;
    }

    public void setNomeSupervisor(String nomeSupervisor) {
        this.nomeSupervisor = nomeSupervisor;
    }

    public int getQuantidadeCursos() {
        return quantidadeCursos;
    }

    public void setQuantidadeCursos(int quantidadeCursos) {
        this.quantidadeCursos = quantidadeCursos;
    }
}
