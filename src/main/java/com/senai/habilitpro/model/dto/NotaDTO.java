package com.senai.habilitpro.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long idAvaliacao;

    private Long idTrabalhador;

    private BigDecimal nota;

    private AvaliacaoDTO avaliacao;
}
