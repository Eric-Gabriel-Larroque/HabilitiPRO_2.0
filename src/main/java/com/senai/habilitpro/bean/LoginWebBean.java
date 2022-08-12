package com.senai.habilitpro.bean;

import com.senai.habilitpro.model.dto.LoginDTO;
import com.senai.habilitpro.service.UsuarioService;
import com.senai.habilitpro.utils.MessageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named("loginWebBean")
public class LoginWebBean implements Serializable {

    private static final Logger LOG = LogManager.getLogger(LoginWebBean.class);

    private static final long serialVersionUID = 1L;

    @Inject
    private UsuarioService usuarioService;

    private LoginDTO login = new LoginDTO();

    @PostConstruct
    public void init() {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            Faces.redirect("/index.xhtml");
        }
    }

    public void login() {
        LOG.info("Iniciando login...");
        try {
            login = usuarioService.login(login);
            LOG.info("Login realizado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("Erro ao efetuar o login: " + e.getMessage());
            MessageUtils.returnMessageOnFail("Ocorreu um erro no login.");
        }
    }

    public String logout() {
        LOG.info("Iniciando logout...");
        try {
            Subject user = SecurityUtils.getSubject();
            if (user != null) {
                user.logout();
                LOG.info("Deslogando usuário...");
            }
            LOG.info("Deslogado com sucesso!");
            return "/login.xhtml?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            MessageUtils.returnMessageOnFail("Ocorreu um erro no logout.");
            return null;
        }
    }

    public LoginDTO getLogin() {
        return login;
    }

    public void setLogin(LoginDTO login) {
        this.login = login;
    }
}
