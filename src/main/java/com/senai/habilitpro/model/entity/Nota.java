package com.senai.habilitpro.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Avaliacao avaliacao;

    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Trabalhador trabalhador;

    @Column(nullable = false)
    @Min(value = 0)
    @Max(value = 10)
    private BigDecimal nota;
}
