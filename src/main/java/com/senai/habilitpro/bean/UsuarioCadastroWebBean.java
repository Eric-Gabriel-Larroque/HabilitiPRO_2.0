package com.senai.habilitpro.bean;

import com.senai.habilitpro.exception.UsuarioException;
import com.senai.habilitpro.model.dto.RoleDTO;
import com.senai.habilitpro.model.dto.UsuarioDTO;
import com.senai.habilitpro.service.RoleService;
import com.senai.habilitpro.service.UsuarioService;
import com.senai.habilitpro.utils.MessageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@Named("usuarioCadastroWebBean")
public class UsuarioCadastroWebBean implements Serializable {

    private static final Logger LOG = LogManager.getLogger(UsuarioBuscaWebBean.class);

    @Inject
    private RoleService roleService;
    @Inject
    private UsuarioService usuarioService;

    private static final long serialVersionUID = 1L;

    private UsuarioDTO usuario = new UsuarioDTO();
    private List<RoleDTO> roles = new ArrayList<>();

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    @PostConstruct
    public void init() {
        LOG.info("Listando todas as Roles cadastradas no banco...");
        this.roles = roleService.findAll();
    }

    public String salvar() {
        LOG.info("Iniciando método salvar...");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        try {
            LOG.info("Iniciando método de cadastro de usuário...");
            this.usuarioService.cadastrar(this.usuario);
            MessageUtils.returnGlobalMessageOnSuccess("Usuário cadastrado com sucesso!");
            LOG.info("Usuário persistido com sucesso!");
            return "/secure/usuarioCadastro?faces-redirect=true";
        } catch (UsuarioException e) {
            LOG.error("Erro ao persistir usuário: " + e.getErros());
            for (String erro : e.getErros()) {
                facesContext.addMessage("facesMsg", new FacesMessage(FacesMessage.SEVERITY_ERROR, erro, ""));
            }
        } catch (Exception e) {
            LOG.error("Erro ao persistir usuário: " + e.getMessage());
            facesContext.addMessage("facesMsg", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao cadastrar usuário, atualize a página e tente novamente!", ""));
        }
        return null;
    }
}
