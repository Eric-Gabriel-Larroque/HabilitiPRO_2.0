package com.senai.habilitpro.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TrabalhadorCreateDTO implements Serializable {

    @NotEmpty(message = "{trabalhador.nome.notempty}")
    private String nome;

    @NotEmpty(message = "{trabalhador.cpf.notempty}")
    private String cpf;

    @NotEmpty(message = "{trabalhador.setor.notempty}")
    private String setor;

    @NotEmpty(message = "{trabalhador.funcao.notempty}")
    private String funcao;

    @NotNull(message = "{trabalhador.idEmpresa.notnull}")
    private Long idEmpresa;
}
