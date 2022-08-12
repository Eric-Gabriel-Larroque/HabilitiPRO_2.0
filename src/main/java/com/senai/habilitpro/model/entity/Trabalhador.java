package com.senai.habilitpro.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Set;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.PERSIST;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Trabalhador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotBlank
    @Column(nullable = false)
    private String nome;
    @NotBlank
    @Column(nullable = false)
    private String cpf;
    @NotBlank
    @Column(nullable = false)
    private String setor;
    @NotBlank
    @Column(nullable = false)
    private String funcao;
    @Column(name = "alteracao_funcao")
    private Date alteracaoFuncao;

    @OneToMany(mappedBy = "trabalhador", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Nota> notas;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "trabalhador_curso",
            joinColumns = @JoinColumn(name = "trabalhador_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "curso_id", nullable = false))
    private Set<Curso> cursos;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;
}
