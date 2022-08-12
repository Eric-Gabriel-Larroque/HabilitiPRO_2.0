package com.senai.habilitpro.enumerator;

public enum RoleEnum {
    ADMINISTRADOR("ADMINISTRADOR", "1"),
    OPERADOR("OPERADOR", "2"),
    SUPERVISOR("SUPERVISOR", "3");

    private String nome;
    private String id;

    private RoleEnum(String nome, String id) {
        this.nome = nome;
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getId() {
        return id;
    }
}
