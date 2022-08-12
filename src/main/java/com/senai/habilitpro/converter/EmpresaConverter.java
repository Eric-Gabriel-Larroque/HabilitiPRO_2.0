package com.senai.habilitpro.converter;

import com.senai.habilitpro.model.dto.EmpresaDTO;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

@Named
@FacesConverter(value = "empresaConverter", managed = true)
public class EmpresaConverter implements Converter<EmpresaDTO> {
    @Override
    public EmpresaDTO getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            return (EmpresaDTO) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, EmpresaDTO empresaDTO) {
        if (empresaDTO != null) {
            uiComponent.getAttributes().put(empresaDTO.getId().toString(), empresaDTO);
            return empresaDTO.getId().toString();
        }
        return null;
    }
}
