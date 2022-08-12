package com.senai.habilitpro.model.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class EnderecoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "{endereco.numero.notblank}")
    private String numero;
    @NotBlank(message = "{endereco.rua.notblank}")
    private String rua;
    @NotBlank(message = "{endereco.bairro.notblank}")
    private String bairro;
    @NotBlank(message = "{endereco.cidade.notblank}")
    private String cidade;
    @NotBlank(message = "{endereco.estado.notblank}")
    private String estado;
    @NotBlank(message = "{endereco.pais.notblank}")
    private String pais;

    private Long idEmpresa;

    public EnderecoDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
}
