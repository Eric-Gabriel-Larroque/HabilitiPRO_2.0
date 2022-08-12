package com.senai.habilitpro.config.mapstruct;

import com.senai.habilitpro.model.dto.RoleDTO;
import com.senai.habilitpro.model.dto.UsuarioDTO;
import com.senai.habilitpro.model.dto.request.UsuarioRequestApiDTO;
import com.senai.habilitpro.model.dto.request.UsuarioUpdateRequestApiDTO;
import com.senai.habilitpro.model.dto.response.UsuarioResponseApiDTO;
import com.senai.habilitpro.model.dto.response.UsuarioResponseEmpresaApiDTO;
import com.senai.habilitpro.model.dto.response.UsuarioResponseLoginApiDTO;
import com.senai.habilitpro.model.entity.Empresa;
import com.senai.habilitpro.model.entity.Role;
import com.senai.habilitpro.model.entity.Usuario;
import com.senai.habilitpro.utils.Formatador;
import io.swagger.v3.oas.models.media.UUIDSchema;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UsuarioMapper {
    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

     public List<UsuarioDTO> fromModelToDtoList(List<Usuario> usuariosModel);
    UsuarioResponseEmpresaApiDTO toEmpresaApiResponse(Usuario usuarioModel);

    @Mapping(target = "roles", source = "roles", qualifiedByName = "roleModelParaRoleDTO")
    @Mapping(target = "cpf", source = "cpf", qualifiedByName = "formataCpf")
    UsuarioResponseApiDTO toApiResponse(Usuario usuarioModel);

    @Mapping(target = "empresaId", source = "empresa", qualifiedByName = "empresaModelParaEmpresaId")
    @Mapping(target = "roles", source = "roles", qualifiedByName = "roleModelParaRoleDTO")
    UsuarioResponseLoginApiDTO toLoginApiDTO(Usuario usuarioModel);

    UsuarioDTO toDTO(UsuarioRequestApiDTO requestApiDTO);
    UsuarioDTO toDTO(UsuarioUpdateRequestApiDTO requestUpdateApiDTO);

    UsuarioDTO toDTO(Usuario usuarioModel);

    UsuarioRequestApiDTO toRequestApiDTO(UsuarioDTO usuarioDTO);

    UsuarioUpdateRequestApiDTO toUpdateRequestApiDTO(UsuarioDTO usuarioDTO);

    @Named("roleModelParaRoleDTO")
    public static List<RoleDTO> roleModelParaRoleDTO(List<Role> roles) {
        return RoleMapper.INSTANCE.toApiResponse(roles);
    }

    @Named("empresaModelParaEmpresaId")
    public static Long empresaModelParaEmpresaId(Empresa empresa) {
        return EmpresaMapper.empresaModelParaEmpresaId(empresa);
    }
    @Named("formataCpf")
    public static String formataCpf(String cpf) {
        return Formatador.cpf(cpf);
    }
}
