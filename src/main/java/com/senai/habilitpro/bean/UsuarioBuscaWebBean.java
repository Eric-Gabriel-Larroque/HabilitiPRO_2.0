package com.senai.habilitpro.bean;


import com.senai.habilitpro.config.mapstruct.UsuarioMapper;
import com.senai.habilitpro.enumerator.RoleEnum;
import com.senai.habilitpro.exception.EmpresaException;
import com.senai.habilitpro.model.dto.*;
import com.senai.habilitpro.model.entity.Usuario;
import com.senai.habilitpro.security.UserAuthenticationToken;
import com.senai.habilitpro.service.EmpresaService;
import com.senai.habilitpro.service.RoleService;
import com.senai.habilitpro.service.UsuarioService;
import com.senai.habilitpro.utils.MessageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ViewScoped
@Named("usuarioBuscaWebBean")
public class UsuarioBuscaWebBean implements Serializable {

    @Inject
    private UsuarioService usuarioService;
    @Inject
    private RoleService roleService;
    @Inject
    private EmpresaService empresaService;

    @Inject
    private UserAuthenticationToken usuarioLogado;

    private static final Logger LOG = LogManager.getLogger(UsuarioBuscaWebBean.class);

    private static final long serialVersionUID = 1L;

    private UsuarioFiltroDTO usuarioFiltro = new UsuarioFiltroDTO();
    private List<UsuarioDTO> usuariosFiltrados = new ArrayList<>();
    private List<RoleDTO> roles = new ArrayList<>();
    private boolean filtroRealizado = false;

    public boolean isFiltroRealizado() {
        return filtroRealizado;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public List<UsuarioDTO> getUsuariosFiltrados() {
        return usuariosFiltrados;
    }

    public UsuarioFiltroDTO getUsuarioFiltro() {
        return usuarioFiltro;
    }

    @PostConstruct
    public void init() {
        this.roles = roleService.findAll();
    }

    public void filtrar() {
        LOG.info("Filtrando usuários...");
        List<Usuario> usuariosEncontrados = this.usuarioService.filtrar(this.usuarioFiltro);
        this.usuariosFiltrados = UsuarioMapper.INSTANCE.fromModelToDtoList(usuariosEncontrados);
        this.filtroRealizado = true;
        LOG.info("Busca realizada com sucesso!");
    }

    public List<EmpresaDTO> autoCompleteEmpresa(String nomeOuId) {
        EmpresaFiltroDTO empresaFiltroDTO = new EmpresaFiltroDTO();
        if (nomeOuId.matches("\\d+")) {
            empresaFiltroDTO.setId(Long.parseLong(nomeOuId));
        }
        if (empresaFiltroDTO.getId() == null) {
            empresaFiltroDTO.setNome(nomeOuId);
        }
        List<EmpresaDTO> empresas = new ArrayList<>();
        try {
            LOG.info("Auto-complete empresa...");
            ModelMapper modelMapper = new ModelMapper();

            List<EmpresaDTO> empresasEncontradas = this.empresaService.buscar(empresaFiltroDTO).stream()
                    .map(empresa -> modelMapper.map(empresa, EmpresaDTO.class))
                    .collect(Collectors.toList());

            empresas.addAll(empresasEncontradas);
        } catch (EmpresaException e) {
            LOG.error("Erro ao buscar empresa: " + e.getMessage());
            MessageUtils.returnMessageOnFail(e.getMessage());
        }
        return empresas;
    }
}
