package com.senai.habilitpro.utils;

public class Formatador {
    public static String cpf(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");
        return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
    }

    public static String cnpj(String cnpj) {
        cnpj = cnpj.replaceAll("[^0-9]", "");
        return cnpj.substring(0,2) + "." + cnpj.substring(2,5) + "." + cnpj.substring(5, 8) + "/" + cnpj.substring(8, 12) + "-" + cnpj.substring(12, 14);
    }
}
