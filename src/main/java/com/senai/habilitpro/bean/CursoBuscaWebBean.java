package com.senai.habilitpro.bean;

import com.senai.habilitpro.enumerator.RoleEnum;
import com.senai.habilitpro.exception.CursoException;
import com.senai.habilitpro.model.dto.CursoDTO;
import com.senai.habilitpro.model.dto.EmpresaDTO;
import com.senai.habilitpro.model.dto.CursoFiltroDTO;
import com.senai.habilitpro.model.dto.LoginDTO;
import com.senai.habilitpro.security.UserAuthenticationToken;
import com.senai.habilitpro.service.CursoService;
import com.senai.habilitpro.service.EmpresaService;
import com.senai.habilitpro.utils.MessageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ViewScoped
@Named("buscaCursoWebBean")
public class CursoBuscaWebBean implements Serializable {

    private static final Logger LOG = LogManager.getLogger(CursoBuscaWebBean.class);

    private static final long serialVersionUID = 1L;

    @Inject
    private CursoService cursoService;
    @Inject
    private EmpresaService empresaService;
    @Inject
    private UserAuthenticationToken usuarioLogado;

    private CursoFiltroDTO filtro = new CursoFiltroDTO();

    private List<EmpresaDTO> empresas = new ArrayList<>();

    private List<CursoDTO> cursosEncontrados = new ArrayList<>();

    private boolean buscaRealizada = false;

    @PostConstruct
    public void init() {
        empresas = empresaService.findAll();
    }

    public void buscar() {
        try {
            LOG.info("Iniciando busca de cursos com o(s) atributo(s) selecionado(s)...");
            cursosEncontrados = cursoService.buscar(filtro);

            if(!usuarioLogado.possuiRole(RoleEnum.ADMINISTRADOR)) {
                LoginDTO loginDTO = (LoginDTO) usuarioLogado.getPrincipal();
                cursosEncontrados.removeIf(cursoDTO -> !Objects.equals(cursoDTO.getEmpresa().getId(), loginDTO.getEmpresa().getId()));
            }

            buscaRealizada = true;
            LOG.info("Busca realizada com sucesso!");
        } catch (CursoException e) {
            LOG.error("Erro ao buscar curso: "+e.getErros());
            MessageUtils.returnMessageOnFail(e.getErros());
        }
    }

    public CursoFiltroDTO getFiltro() {
        return filtro;
    }

    public void setFiltro(CursoFiltroDTO filtro) {
        this.filtro = filtro;
    }

    public List<EmpresaDTO> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(List<EmpresaDTO> empresas) {
        this.empresas = empresas;
    }

    public boolean isBuscaRealizada() {
        return buscaRealizada;
    }

    public void setBuscaRealizada(boolean buscaRealizada) {
        this.buscaRealizada = buscaRealizada;
    }

    public List<CursoDTO> getCursosEncontrados() {
        return cursosEncontrados;
    }

    public void setCursosEncontrados(List<CursoDTO> cursosEncontrados) {
        this.cursosEncontrados = cursosEncontrados;
    }
}
