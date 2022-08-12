package com.senai.habilitpro.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("com.senai.habilitpro.validator.CpfValidator")
public class CpfValidator implements Validator {
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object cpf) throws ValidatorException {
        if (cpf == null) {
            return;
        }
        String cpfString = cpf.toString();
        cpfString = cpfString.replaceAll("[^0-9]", "");

        if (cpfString.length() != 11) {
            FacesMessage msg = new FacesMessage("CPF deve conter 11 dígitos!");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }

        int[] cpfArray = new int[11];
        for (int i = 0; i < cpfString.length(); i++) {
            cpfArray[i] = Integer.parseInt(cpfString.substring(i, i + 1));
        }

        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += cpfArray[i] * (10 - i);
        }

        int digito1 = 11 - (soma % 11);
        if (digito1 > 9) {
            digito1 = 0;
        }

        if (digito1 != cpfArray[9]) {
            FacesMessage msg = new FacesMessage("CPF inválido!");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += cpfArray[i] * (11 - i);
        }

        int digito2 = 11 - (soma % 11);
        if (digito2 > 9) {
            digito2 = 0;
        }

        if (digito2 != cpfArray[10]) {
            FacesMessage msg = new FacesMessage("CPF inválido!");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
