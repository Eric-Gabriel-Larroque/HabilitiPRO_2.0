package com.senai.habilitpro.model.dto.response;

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
public class NotaResponseDTO implements Serializable {
    private Long id;
    private AvaliacaoResponseDTO avaliacao;
    private BigDecimal nota;
}
