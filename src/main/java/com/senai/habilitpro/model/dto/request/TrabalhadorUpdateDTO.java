package com.senai.habilitpro.model.dto.request;

import java.io.Serializable;

public class TrabalhadorUpdateDTO implements Serializable {

    private String nome;
    private String cpf;
    private String setor;
    private String funcao;
    private Long idEmpresa;

    public TrabalhadorUpdateDTO(String nome, String cpf, String setor, String funcao, Long idEmpresa) {
        this.nome = nome;
        this.cpf = cpf;
        this.setor = setor;
        this.funcao = funcao;
        this.idEmpresa = idEmpresa;
    }

    public TrabalhadorUpdateDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
}
