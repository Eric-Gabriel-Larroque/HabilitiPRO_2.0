package com.senai.habilitpro.model.dto.request;

import java.io.Serializable;

public class EmpresaUpdateDTO implements Serializable {
    private String nome;
    private String cnpj;
    private boolean filial;
    private String nomeFilial;
    private Long supervisorId;
    private Integer segmento;
    private Integer regionalSenai;
    private Long enderecoId;

    public EmpresaUpdateDTO(String nome, String cnpj, boolean filial, String nomeFilial, Long supervisorId, Integer segmento, Integer regionalSenai, Long enderecoId) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.filial = filial;
        this.nomeFilial = nomeFilial;
        this.supervisorId = supervisorId;
        this.segmento = segmento;
        this.regionalSenai = regionalSenai;
        this.enderecoId = enderecoId;
    }

    public EmpresaUpdateDTO() {
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

    public Long getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(Long supervisorId) {
        this.supervisorId = supervisorId;
    }

    public Integer getSegmento() {
        return segmento;
    }

    public void setSegmento(Integer segmento) {
        this.segmento = segmento;
    }

    public Integer getRegionalSenai() {
        return regionalSenai;
    }

    public void setRegionalSenai(Integer regionalSenai) {
        this.regionalSenai = regionalSenai;
    }

    public Long getEnderecoId() {
        return enderecoId;
    }

    public void setEnderecoId(Long enderecoId) {
        this.enderecoId = enderecoId;
    }
}
