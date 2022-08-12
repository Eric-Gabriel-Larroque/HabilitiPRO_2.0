package com.senai.habilitpro.model.dto.request;

import com.senai.habilitpro.enumerator.RegionalSenaiEnum;
import com.senai.habilitpro.enumerator.SegmentoEmpresaEnum;
import com.senai.habilitpro.model.dto.CursoDTO;
import com.senai.habilitpro.model.dto.TrabalhadorDTO;
import com.senai.habilitpro.model.dto.UsuarioDTO;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EmpresaCreateApiDTO implements Serializable {
    @NotBlank(message = "{empresa.nome.notblank}")
    private String nome;

    @NotBlank(message = "{empresa.cnpj.notblank}")
    private String cnpj;

    private boolean filial;

    private String nomeFilial;

    @NotNull(message = "{empresa.supervisor.notnull}")
    private Long supervisorId;

    @NotNull(message = "{empresa.segmento.notnull}")
    private Integer segmento;

    @NotNull(message = "{empresa.regional.notnull}")
    private Integer regionalSenai;

    @NotNull(message = "{empresa.endereco.notnull}")
    private Long enderecoId;

    public EmpresaCreateApiDTO(String nome, String cnpj, boolean filial, String nomeFilial, Long supervisorId, Integer segmento, Integer regionalSenai, Long enderecoId) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.filial = filial;
        this.nomeFilial = nomeFilial;
        this.supervisorId = supervisorId;
        this.segmento = segmento;
        this.regionalSenai = regionalSenai;
        this.enderecoId = enderecoId;
    }

    public EmpresaCreateApiDTO() {
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
