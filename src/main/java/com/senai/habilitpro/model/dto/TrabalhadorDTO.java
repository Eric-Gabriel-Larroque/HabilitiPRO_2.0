package com.senai.habilitpro.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrabalhadorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotEmpty(message = "{trabalhador.nome.notempty}")
    private String nome;

    @NotEmpty(message = "{trabalhador.cpf.notempty}")
    private String cpf;

    @NotEmpty(message = "{trabalhador.setor.notempty}")
    private String setor;

    @NotEmpty(message = "{trabalhador.funcao.notempty}")
    private String funcao;

    private Date alteracaoFuncao;

    private List<Long> idCursos = new ArrayList<>();

    private List<CursoDTO> cursos = new ArrayList<>();

    private EmpresaDTO empresa;

    @NotNull(message = "{trabalhador.idEmpresa.notnull}")
    private Long idEmpresa;

    private NotaDTO nota = new NotaDTO();

    private List<NotaDTO> notas = new ArrayList<>();

    public TrabalhadorDTO() {
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

    public String getCpf() {
        if (cpf != null) {
            return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
        }
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

    public Date getAlteracaoFuncao() {
        return alteracaoFuncao;
    }

    public void setAlteracaoFuncao(Date alteracaoFuncao) {
        this.alteracaoFuncao = alteracaoFuncao;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public List<Long> getIdCursos() {
        return idCursos;
    }

    public void setIdCursos(List<Long> idCursos) {
        this.idCursos = idCursos;
    }

    public List<CursoDTO> getCursos() {
        return cursos;
    }

    public void setCursos(List<CursoDTO> cursos) {
        this.cursos = cursos;
    }

    public EmpresaDTO getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaDTO empresa) {
        this.empresa = empresa;
    }

    public NotaDTO getNota() {
        return nota;
    }

    public void setNota(NotaDTO nota) {
        this.nota = nota;
    }

    public List<NotaDTO> getNotas() {
        return notas;
    }

    public void setNotas(List<NotaDTO> notas) {
        this.notas = notas;
    }
}
