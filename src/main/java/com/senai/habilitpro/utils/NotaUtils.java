package com.senai.habilitpro.utils;

import com.senai.habilitpro.model.dto.NotaDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class NotaUtils {
    public static BigDecimal calculaNotaMaxima(List<NotaDTO> notas) {
        if (notas.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal max = BigDecimal.ZERO;
        for (NotaDTO nota : notas) {
            if (nota.getNota().compareTo(max) > 0) {
                max = nota.getNota();
            }
        }
        return max.setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculaNotaMinima(List<NotaDTO> notas) {
        if (notas.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal min = BigDecimal.TEN;
        for (NotaDTO nota : notas) {
            if (nota.getNota().compareTo(min) < 0) {
                min = nota.getNota();
            }
        }
        return min.setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculaNotaMedia(List<NotaDTO> notas) {
        if (notas.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal soma = BigDecimal.ZERO;
        for (NotaDTO nota : notas) {
            soma = soma.add(nota.getNota());
        }
        return soma.divide(new BigDecimal(notas.size())).setScale(2, RoundingMode.HALF_UP);
    }
}
