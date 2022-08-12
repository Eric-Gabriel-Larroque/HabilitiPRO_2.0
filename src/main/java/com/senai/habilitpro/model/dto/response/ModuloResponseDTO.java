package com.senai.habilitpro.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ModuloResponseDTO implements Serializable {
    private Long id;
    private String nome;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private List<HabilidadeResponseDTO> habilidades = new ArrayList<>();
    private CursoResponseDTO curso;
}
