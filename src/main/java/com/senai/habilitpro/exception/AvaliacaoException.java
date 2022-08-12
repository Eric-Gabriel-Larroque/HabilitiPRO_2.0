package com.senai.habilitpro.exception;

import java.util.List;

public class AvaliacaoException extends GenericException {
    public AvaliacaoException(String erro) {
        super(erro);
    }

    public AvaliacaoException(List<String> erros) {
        super(erros);
    }
}
