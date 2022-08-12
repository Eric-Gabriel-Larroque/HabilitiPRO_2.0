package com.senai.habilitpro.bean;

import com.senai.habilitpro.enumerator.RegionalSenaiEnum;
import com.senai.habilitpro.enumerator.RoleEnum;
import com.senai.habilitpro.enumerator.SegmentoEmpresaEnum;
import com.senai.habilitpro.exception.EmpresaException;
import com.senai.habilitpro.model.dto.EmpresaDTO;
import com.senai.habilitpro.model.dto.EnderecoDTO;
import com.senai.habilitpro.model.dto.UsuarioDTO;
import com.senai.habilitpro.security.UserAuthenticationToken;
import com.senai.habilitpro.service.EmpresaService;
import com.senai.habilitpro.service.EnderecoService;
import com.senai.habilitpro.service.UsuarioService;
import com.senai.habilitpro.utils.MessageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ViewScoped
@Named("empresaCadastroWebBean")
public class EmpresaCadastroWebBean implements Serializable {

    private static final Logger LOG = LogManager.getLogger(EmpresaCadastroWebBean.class);

    private static final long serialVersionUID = 1L;

    @Inject
    private UserAuthenticationToken usuarioLogado;

    @Inject
    private EmpresaService empresaService;

    @Inject
    private UsuarioService usuarioService;

    @Inject
    private EnderecoService enderecoService;

    private EmpresaDTO empresa = new EmpresaDTO();

    private EnderecoDTO endereco = new EnderecoDTO();

    private List<SegmentoEmpresaEnum> segmentos = new ArrayList<>(Arrays.asList(SegmentoEmpresaEnum.values()));

    private List<RegionalSenaiEnum> regionais = new ArrayList<>(Arrays.asList(RegionalSenaiEnum.values()));

    private List<UsuarioDTO> usuarios = new ArrayList<>();

    @PostConstruct
    public void init() {

        this.usuarios = usuarioService.findUsuariosSemEmpresa();
        boolean isAdmin = usuarioLogado.possuiRole(RoleEnum.ADMINISTRADOR);
    }

    public String salvar() {
        LOG.info("Iniciando método salvar...");
        try {
            LOG.info("Iniciando método de cadastro de empresa...");
            enderecoService.cadastrar(endereco);
            empresa.setIdEndereco(endereco.getId());
            empresaService.cadastrar(empresa);
            MessageUtils.returnGlobalMessageOnSuccess("Salvo com sucesso!");
            LOG.info("Empresa persistida com sucesso!");
            return "/secure/empresaCadastro.xhtml?faces-redirect=true";
        } catch (EmpresaException e) {
            LOG.error("Erro ao persistir empresa: " + e.getMessage());
            MessageUtils.returnMessageOnFail(e.getMessage());
        } catch (Exception e) {
            LOG.error("Erro ao persistir empresa: " + e.getMessage());
            MessageUtils.returnMessageOnFail("Erro ao cadastrar empresa");
        }
        return null;
    }

    public EmpresaDTO getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaDTO empresa) {
        this.empresa = empresa;
    }

    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }

    public List<SegmentoEmpresaEnum> getSegmentos() {
        return segmentos;
    }

    public void setSegmentos(List<SegmentoEmpresaEnum> segmentos) {
        this.segmentos = segmentos;
    }

    public List<RegionalSenaiEnum> getRegionais() {
        return regionais;
    }

    public void setRegionais(List<RegionalSenaiEnum> regionais) {
        this.regionais = regionais;
    }

    public List<UsuarioDTO> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioDTO> usuarios) {
        this.usuarios = usuarios;
    }

}
