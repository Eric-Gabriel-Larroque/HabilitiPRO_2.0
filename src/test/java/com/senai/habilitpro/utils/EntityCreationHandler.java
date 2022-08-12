package com.senai.habilitpro.utils;

import com.senai.habilitpro.config.mapstruct.UsuarioMapper;
import com.senai.habilitpro.model.dto.UsuarioDTO;
import com.senai.habilitpro.model.dto.UsuarioFiltroDTO;
import com.senai.habilitpro.model.dto.request.LoginRequestApiDTO;
import com.senai.habilitpro.model.dto.request.UsuarioRequestApiDTO;
import com.senai.habilitpro.model.dto.request.UsuarioUpdateRequestApiDTO;
import com.senai.habilitpro.model.dto.response.UsuarioResponseApiDTO;
import com.senai.habilitpro.model.entity.Empresa;
import com.senai.habilitpro.model.entity.Role;
import com.senai.habilitpro.model.entity.Usuario;

import java.util.Arrays;

public class EntityCreationHandler {

    public static UsuarioRequestApiDTO criarUsuarioReqApiDTO() {
        return UsuarioMapper.INSTANCE.toRequestApiDTO(criarUsuarioDTO());
    }

    public static UsuarioFiltroDTO criarUsuarioFiltroDTO() {
        UsuarioFiltroDTO filtroDTO = new UsuarioFiltroDTO();
        filtroDTO.setId(1L);
        return filtroDTO;
    }
    public static UsuarioUpdateRequestApiDTO criarUsuarioUpdateReqApiDTO() {
        return UsuarioMapper.INSTANCE.toUpdateRequestApiDTO(criarUsuarioDTO());
    }

    public static UsuarioResponseApiDTO criarUsuarioRespApiDTO() {
        return UsuarioMapper.INSTANCE.toApiResponse(criarUsuario());
    }

    public static Usuario criarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setRoles(Arrays.asList(criarRole()));
        usuario.setSenha("senha");
        usuario.setEmail("email");
        usuario.setCpf("111.111.111-11");
        usuario.setEmpresa(new Empresa());
        usuario.setId(1L);
        return usuario;
    }

    public static Role criarRole() {
        Role role = new Role();
        role.setId(1L);
        role.setNome("ADM");
        return role;
    }

    public static UsuarioDTO criarUsuarioDTO() {
        return UsuarioMapper.INSTANCE.toDTO(criarUsuario());
    }

    public static LoginRequestApiDTO criarLoginRequestApiDTO() {
        LoginRequestApiDTO loginDTO = new LoginRequestApiDTO();
        loginDTO.setSenha("senha");
        loginDTO.setEmail("email");
        return loginDTO;
    }

}
