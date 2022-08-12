package com.senai.habilitpro.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.PERSIST;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String apelido;

    @NotBlank
    @Column(nullable = false)
    private String ocupacao;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Empresa empresa;

    @OneToMany(mappedBy = "curso", fetch = FetchType.LAZY)
    private Set<Modulo> modulos;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "cursos")
    private Set<Trabalhador> trabalhadores;

    @NotBlank
    @Column(nullable = false)
    private String descricao;

    public void removeTrabalhador(Trabalhador trabalhador) {
        this.trabalhadores.remove(trabalhador);
        trabalhador.getCursos().remove(this);
    }
}
