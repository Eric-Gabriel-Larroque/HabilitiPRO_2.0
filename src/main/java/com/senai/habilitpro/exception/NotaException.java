package com.senai.habilitpro.exception;

import java.util.List;

public class NotaException extends GenericException {
    public NotaException(String erro) {
        super(erro);
    }

    public NotaException(List<String> erros) {
        super(erros);
    }
}
