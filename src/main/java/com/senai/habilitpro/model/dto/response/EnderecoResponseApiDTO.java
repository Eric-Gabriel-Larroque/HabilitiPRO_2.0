package com.senai.habilitpro.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EnderecoResponseApiDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String numero;
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private String pais;
}
