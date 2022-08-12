package com.senai.habilitpro.enumerator;

public enum ModuloStatusEnum {
    AGUARDANDO_INICIO("Aguardando início"),
    EM_ANDAMENTO("Em andamento"),
    FINALIZADO("Finalizado");

    private String descricao;

    ModuloStatusEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
