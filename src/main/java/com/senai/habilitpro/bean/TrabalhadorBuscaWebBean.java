package com.senai.habilitpro.bean;


import com.senai.habilitpro.config.modelmapper.ModelMapperConfig;
import com.senai.habilitpro.enumerator.RoleEnum;
import com.senai.habilitpro.model.dto.CursoDTO;
import com.senai.habilitpro.model.dto.LoginDTO;
import com.senai.habilitpro.model.dto.TrabalhadorDTO;
import com.senai.habilitpro.model.dto.TrabalhadorFiltroDTO;
import com.senai.habilitpro.model.entity.Trabalhador;
import com.senai.habilitpro.security.UserAuthenticationToken;
import com.senai.habilitpro.service.CursoService;
import com.senai.habilitpro.service.TrabalhadorService;
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
import java.util.stream.Collectors;

@ViewScoped
@Named("trabalhadorBuscaWebBean")
public class TrabalhadorBuscaWebBean implements Serializable {

    private static final Logger LOG = LogManager.getLogger(TrabalhadorBuscaWebBean.class);

    @Inject
    private TrabalhadorService trabalhadorService;
    @Inject
    private CursoService cursoService;
    @Inject
    private UserAuthenticationToken usuarioLogado;
    @Inject
    private ModelMapperConfig modelMapper;

    private static final long serialVersionUID = 1L;

    private TrabalhadorFiltroDTO trabalhadorFiltro = new TrabalhadorFiltroDTO();
    private List<TrabalhadorDTO> trabalhadoresFiltrados = new ArrayList<>();
    private List<CursoDTO> cursos = new ArrayList<>();
    private boolean filtroRealizado = false;

    public List<CursoDTO> getCursos() {
        return cursos;
    }

    public boolean isFiltroRealizado() {
        return filtroRealizado;
    }

    public TrabalhadorFiltroDTO getTrabalhadorFiltro() {
        return trabalhadorFiltro;
    }

    public List<TrabalhadorDTO> getTrabalhadoresFiltrados() {
        return trabalhadoresFiltrados;
    }

    @PostConstruct
    public void init() {
        this.cursos = this.cursoService.getTodosCursos();
    }

    public void filtrar() {
        LOG.info("Filtrando trabalhadores...");

        List<Trabalhador> trabalhadoresFiltrados = this.trabalhadorService.buscarComFiltro(this.trabalhadorFiltro);
        this.trabalhadoresFiltrados = trabalhadoresFiltrados.stream()
                .map(trabalhador -> modelMapper.map(trabalhador, TrabalhadorDTO.class))
                .collect(Collectors.toList());
        this.filtroRealizado = true;

        if(!usuarioLogado.possuiRole(RoleEnum.ADMINISTRADOR)) {
            LoginDTO loginDTO = (LoginDTO) usuarioLogado.getPrincipal();
            this.trabalhadoresFiltrados.removeIf(trabalhador -> !Objects.equals(trabalhador.getEmpresa().getId(), loginDTO.getEmpresa().getId()));
        }
        LOG.info("Busca realizada com sucesso!...");
    }
}
