package com.senai.habilitpro.config.mapstruct;

import com.senai.habilitpro.model.dto.response.EnderecoResponseApiDTO;
import com.senai.habilitpro.model.entity.Endereco;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EnderecoMapper {
    EnderecoMapper INSTANCE = Mappers.getMapper(EnderecoMapper.class);

    EnderecoResponseApiDTO toApiResponse(Endereco enderecoModel);
}
