package com.senai.habilitpro.model.entity;

import com.senai.habilitpro.enumerator.RegionalSenaiEnum;
import com.senai.habilitpro.enumerator.SegmentoEmpresaEnum;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, unique = true)
    private String cnpj;
    @Column(name = "is_filial", columnDefinition = "boolean default false")
    private boolean filial = false;
    @Column(name = "nome_filial")
    private String nomeFilial;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private SegmentoEmpresaEnum segmento;
    @Column(nullable = false, name = "regional_senai")
    @Enumerated(value = EnumType.STRING)
    private RegionalSenaiEnum regionalSenai;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false)
    private Usuario supervisor;
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Curso> cursos;
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Trabalhador> trabalhadores;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;

    public Empresa() {
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

    public Usuario getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Usuario supervisor) {
        this.supervisor = supervisor;
    }

    public Set<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(Set<Curso> cursos) {
        this.cursos = cursos;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Set<Trabalhador> getTrabalhadores() {
        return trabalhadores;
    }

    public void setTrabalhadores(Set<Trabalhador> trabalhadores) {
        this.trabalhadores = trabalhadores;
    }
}
