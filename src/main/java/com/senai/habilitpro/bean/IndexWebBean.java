package com.senai.habilitpro.bean;

import com.senai.habilitpro.enumerator.AvaliacaoStatusEnum;
import com.senai.habilitpro.enumerator.RoleEnum;
import com.senai.habilitpro.model.dto.AvaliacaoDTO;
import com.senai.habilitpro.security.UserAuthenticationToken;
import com.senai.habilitpro.service.AvaliacaoService;
import com.senai.habilitpro.utils.MessageUtils;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@Named("indexWebBean")
public class IndexWebBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private AvaliacaoService avaliacaoService;

    private boolean isAdmin = false;
    private List<AvaliacaoStatusEnum> statusList = new ArrayList<>();
    private AvaliacaoStatusEnum statusSelecionado;
    private List<AvaliacaoDTO> avaliacoes = new ArrayList<>();
    private boolean filtrou = false;

    public void setStatusSelecionado(AvaliacaoStatusEnum statusSelecionado) {
        this.statusSelecionado = statusSelecionado;
    }

    public List<AvaliacaoStatusEnum> getStatusList() {
        return statusList;
    }

    public AvaliacaoStatusEnum getStatusSelecionado() {
        return statusSelecionado;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public List<AvaliacaoDTO> getAvaliacoes() {
        return avaliacoes;
    }

    public boolean isFiltrou() {
        return filtrou;
    }

    @PostConstruct
    public void init() {
        this.isAdmin = new UserAuthenticationToken().possuiRole(RoleEnum.ADMINISTRADOR);
        statusList = AvaliacaoStatusEnum.getAll();
        if (!isAdmin) {
            statusList.remove(AvaliacaoStatusEnum.CONCLUIDA);
        }

    }

    public void filtrar() {
        if (statusSelecionado == null) {
            MessageUtils.returnMessageOnFail("Selecione um status para filtrar.");
            return;
        }
        this.filtrou = true;
        avaliacoes = avaliacaoService.getAvaliacoesByStatus(statusSelecionado);
    }
}
