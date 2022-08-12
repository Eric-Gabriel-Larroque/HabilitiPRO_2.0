package com.senai.habilitpro.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TrabalhadorCompleteResponseDTO implements Serializable {
    private Long id;
    private String nome;
    private String cpf;
    private String setor;
    private String funcao;
    private Date alteracaoFuncao;
    private EmpresaResponseDTO empresa;
    private List<CursoResponseDTO> cursos;
    private List<NotaResponseDTO> notas;
}
