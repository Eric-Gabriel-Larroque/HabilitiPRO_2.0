package com.senai.habilitpro.service;

import com.senai.habilitpro.business.RoleBusiness;
import com.senai.habilitpro.model.dto.RoleDTO;
import com.senai.habilitpro.model.entity.Role;
import com.senai.habilitpro.model.repository.RoleRepository;
import org.modelmapper.ModelMapper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class RoleService {

    @Inject
    private RoleBusiness roleBusiness;

    @Inject
    private RoleRepository roleRepository;

    public List<RoleDTO> findAll() {
        ModelMapper modelMapper = new ModelMapper();
        List<Role> roles = roleRepository.findAll();
        List<RoleDTO> rolesDTO = new ArrayList<>();
        for (Role role : roles) {
            rolesDTO.add(modelMapper.map(role, RoleDTO.class));
        }
        return rolesDTO;
    }
}
