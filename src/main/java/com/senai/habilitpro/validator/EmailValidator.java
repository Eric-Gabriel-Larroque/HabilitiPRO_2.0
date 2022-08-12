package com.senai.habilitpro.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("com.senai.habilitpro.validator.EmailValidator")
public class EmailValidator implements Validator {
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object emailObject) throws ValidatorException {
        if (emailObject == null) {
            return;
        }
        String email = (String) emailObject;
        String pattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
        if (!email.matches(pattern)) {
            FacesMessage msg = new FacesMessage("Formato de e-mail inválido, por favor, forneça um e-mail em um formato válido!");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
