package com.senai.habilitpro.model.dto.response;

import com.senai.habilitpro.enumerator.AvaliacaoStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AvaliacaoResponseDTO implements Serializable {
    private Long id;
    private ModuloResponseDTO modulo;
    private AvaliacaoStatusEnum status;
}
