package com.senai.habilitpro.exception;

import java.util.List;

public class CursoException extends GenericException {
    public CursoException(String erro) {
        super(erro);
    }

    public CursoException(List<String> erros) {
        super(erros);
    }
}
