package com.senai.habilitpro.model.dto.response;

import com.senai.habilitpro.enumerator.RegionalSenaiEnum;
import com.senai.habilitpro.enumerator.SegmentoEmpresaEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmpresaResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private String cnpj;
    private boolean filial;
    private String nomeFilial;
    private UsuarioResponseEmpresaApiDTO supervisor;
    private EnderecoResponseApiDTO endereco;
    private SegmentoEmpresaEnum segmento;
    private RegionalSenaiEnum regionalSenai;
}
