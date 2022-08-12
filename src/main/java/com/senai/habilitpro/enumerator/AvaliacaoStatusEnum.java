package com.senai.habilitpro.enumerator;

import java.util.ArrayList;
import java.util.List;

public enum AvaliacaoStatusEnum {
    EM_ESPERA("Em Espera"),
    EM_AVALIACAO("Em Avaliação"),
    CONCLUIDA("Concluida");

    private String descricao;

    private AvaliacaoStatusEnum(String descricao) {
        this.descricao = descricao;
    }

    public static List<AvaliacaoStatusEnum> getAll() {
        List<AvaliacaoStatusEnum> list = new ArrayList<>();
        list.add(EM_ESPERA);
        list.add(EM_AVALIACAO);
        list.add(CONCLUIDA);
        return list;
    }

    public String getDescricao() {
        return descricao;
    }
}
