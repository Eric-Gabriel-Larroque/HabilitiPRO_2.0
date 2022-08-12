package com.senai.habilitpro.filter;

import com.senai.habilitpro.enumerator.RoleEnum;
import com.senai.habilitpro.security.UserAuthenticationToken;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/secure/*")
public class AuthenticacaoFilter implements Filter {

    @Inject
    private UserAuthenticationToken usuarioLogado;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (usuarioLogado == null || !usuarioLogado.possuiRole(RoleEnum.ADMINISTRADOR)) {
            res.sendRedirect(req.getServletContext().getContextPath() + "/404.xhtml");
        } else {
            chain.doFilter(request, response);
        }
    }

}
