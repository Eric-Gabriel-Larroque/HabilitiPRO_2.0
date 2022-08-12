package com.senai.habilitpro.config.mapstruct;

import com.senai.habilitpro.model.dto.RoleDTO;
import com.senai.habilitpro.model.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);
    public List<RoleDTO> toApiResponse(List<Role> RoleModel);
}
