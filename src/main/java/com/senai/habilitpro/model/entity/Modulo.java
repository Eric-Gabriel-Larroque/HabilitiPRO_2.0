package com.senai.habilitpro.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Modulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, name = "data_inicio")
    private LocalDate dataInicio;

    @Column(nullable = false, name = "data_termino")
    private LocalDate dataTermino;

    @Column(nullable = false)
    @OneToMany(mappedBy = "modulo", fetch = FetchType.EAGER)
    private List<Habilidade> habilidades = new ArrayList<>();

    @OneToOne(mappedBy = "modulo", fetch = FetchType.LAZY)
    private Avaliacao avaliacao;

    @JoinColumn(nullable = false, name = "curso_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Curso curso;

}
