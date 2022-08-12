package com.senai.habilitpro.business;

import com.senai.habilitpro.model.dto.LoginDTO;
import com.senai.habilitpro.security.UserAuthenticationToken;
import com.senai.habilitpro.utils.MessageUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.subject.Subject;
import org.omnifaces.util.Faces;

import javax.annotation.PostConstruct;

public class ShiroAuthenticationBusiness {

    private static final Logger LOG = LogManager.getLogger(ShiroAuthenticationBusiness.class);

    Subject user;

    @PostConstruct
    public void init() {
        user = SecurityUtils.getSubject();
    }

    public LoginDTO login(LoginDTO loginDTO) {
        LOG.info("Verificando se o usuário já está autenticado...");
        user = SecurityUtils.getSubject();
        if (user.isAuthenticated()) {
            LOG.info("Usuário autenticado. Redirecionando à página principal...");
            Faces.redirect("/index.xhtml");
            return null;
        }

        String errorMsg = validateFields(loginDTO);

        if (errorMsg == null) {
            UserAuthenticationToken token = new UserAuthenticationToken(loginDTO);
            try {
                user.login(token);
                if (user.isAuthenticated()) {
                    Faces.redirect("/index.xhtml");
                }
                return loginDTO;
            } catch (IncorrectCredentialsException e) {
                MessageUtils.returnMessageOnFail(e.getMessage());
                LOG.error("Erro ao efetuar o login: " + e.getMessage());
            } catch (Exception e) {
                LOG.error("Erro ao efetuar o login: " + e.getMessage());
                MessageUtils.returnMessageOnFail("Ocorreu um erro ao efetuar o login.");
            }
        } else {
            MessageUtils.returnMessageOnFail(errorMsg);
        }
        LOG.info("Login efetuado com sucesso!");
        return loginDTO;
    }

    private String validateFields(LoginDTO loginDto) {
        String message = null;
        LOG.info("Validando se os campos estão preenchidos...");
        if (StringUtils.isBlank(loginDto.getEmail()) && StringUtils.isBlank(loginDto.getSenha())) {
            LOG.warn("Campos vazios! Preencha para efetuar o login");
            message = "Por favor, preencha os campos";
        } else if (StringUtils.isBlank(loginDto.getEmail())) {
            LOG.warn("Campos de e-mail vazio! Preencha para efetuar o login");
            message = "E-mail não informado";
        } else if (StringUtils.isBlank(loginDto.getSenha())) {
            LOG.warn("Campos de senha vazio! Preencha para efetuar o login");
            message = "Senha não informada";
        }

        return message;
    }

}
