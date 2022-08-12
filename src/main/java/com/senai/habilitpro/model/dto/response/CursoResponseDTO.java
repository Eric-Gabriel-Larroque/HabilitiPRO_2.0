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
public class CursoResponseDTO implements Serializable {
    private Long id;
    private String nome;
    private String apelido;
    private String ocupacao;
    private EmpresaResponseDTO empresa;
    private String descricao;
}
