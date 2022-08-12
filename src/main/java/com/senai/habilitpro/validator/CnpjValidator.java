package com.senai.habilitpro.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("com.senai.habilitpro.validator.CnpjValidator")
public class CnpjValidator implements Validator {
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object cnpj) throws ValidatorException {
        if (cnpj == null) {
            return;
        }
        String cnpjString = cnpj.toString();

        cnpjString = cnpjString.replaceAll("[^0-9]", "");

        if (cnpjString.length() != 14) {
            FacesMessage msg = new FacesMessage("CNPJ deve conter 14 dígitos!");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }

        if (cnpjString.equals("00000000000000") || cnpjString.equals("11111111111111") ||
                cnpjString.equals("22222222222222") || cnpjString.equals("33333333333333") ||
                cnpjString.equals("44444444444444") || cnpjString.equals("55555555555555") ||
                cnpjString.equals("66666666666666") || cnpjString.equals("77777777777777") ||
                cnpjString.equals("88888888888888") || cnpjString.equals("99999999999999")) {
            FacesMessage msg = new FacesMessage("CNPJ inválido!");
        }

        int[] cnpjArray = new int[14];
        int[] numerosValidacaoCnpj = new int[]{
                5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2
        };
        for (int i = 0; i < cnpjString.length(); i++) {
            cnpjArray[i] = Integer.parseInt(cnpjString.substring(i, i + 1));
        }

        int soma = 0;
        for (int i = 0; i < 12; i++) {
            soma += cnpjArray[i] * numerosValidacaoCnpj[i];
        }

        int digito1 = soma % 11;
        if (digito1 > 9) {
            digito1 = 0;
        } else {
            digito1 = 11 - digito1;
        }

        if (digito1 != cnpjArray[12]) {
            FacesMessage msg = new FacesMessage("CNPJ inválido!");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }


        numerosValidacaoCnpj = new int[]{
                6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2
        };
        soma = 0;
        for (int i = 0; i < 13; i++) {
            soma += cnpjArray[i] * numerosValidacaoCnpj[i];
        }

        int digito2 = soma % 11;
        if (digito2 < 2) {
            digito2 = 0;
        } else {
            digito2 = 11 - digito2;
        }

        if (digito2 != cnpjArray[13]) {
            FacesMessage msg = new FacesMessage("CNPJ inválido!");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
