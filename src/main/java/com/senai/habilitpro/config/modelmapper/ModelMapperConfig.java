package com.senai.habilitpro.config.modelmapper;

import org.modelmapper.ModelMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

@ApplicationScoped
public class ModelMapperConfig extends ModelMapper {
    public ModelMapperConfig() {
        super();
    }
}
