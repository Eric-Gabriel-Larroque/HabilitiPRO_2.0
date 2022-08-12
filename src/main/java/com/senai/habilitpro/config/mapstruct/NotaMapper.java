package com.senai.habilitpro.config.mapstruct;

import com.senai.habilitpro.model.dto.NotaDTO;
import com.senai.habilitpro.model.entity.Nota;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NotaMapper {
    NotaMapper INSTANCE = Mappers.getMapper(NotaMapper.class);

    NotaDTO toDTO(Nota nota);
    Nota toEntity(NotaDTO notaDTO);
}
