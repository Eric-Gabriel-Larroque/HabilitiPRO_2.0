package com.senai.habilitpro.exception;

import java.util.List;

public class ModuloException extends GenericException {
    public ModuloException(String erro) {
        super(erro);
    }

    public ModuloException(List<String> erros) {
        super(erros);
    }
}
