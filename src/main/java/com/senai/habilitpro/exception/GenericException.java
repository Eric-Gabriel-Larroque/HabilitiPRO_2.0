package com.senai.habilitpro.exception;

import javax.ejb.ApplicationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ApplicationException(rollback = true)
public class GenericException extends Exception {

    private static final long serialVersionUID = 1L;

    private List<String> erros = new ArrayList<>();

    public GenericException(String erro) {
        this.erros.add(erro);
    }

    public GenericException(List<String> erros) {
        this.erros.addAll(erros);
    }

    public List<String> getErros() {
        return erros;
    }

    public void setErros(List<String> erros) {
        this.erros = erros;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericException genericException = (GenericException) o;
        return Objects.equals(erros, genericException.erros);
    }

    @Override
    public int hashCode() {
        return Objects.hash(erros);
    }
}