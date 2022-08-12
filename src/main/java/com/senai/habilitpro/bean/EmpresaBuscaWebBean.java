package com.senai.habilitpro.bean;

import com.senai.habilitpro.enumerator.RegionalSenaiEnum;
import com.senai.habilitpro.enumerator.RoleEnum;
import com.senai.habilitpro.enumerator.SegmentoEmpresaEnum;
import com.senai.habilitpro.exception.EmpresaException;
import com.senai.habilitpro.model.dto.EmpresaDTO;
import com.senai.habilitpro.model.dto.EmpresaFiltroDTO;
import com.senai.habilitpro.model.dto.LoginDTO;
import com.senai.habilitpro.security.UserAuthenticationToken;
import com.senai.habilitpro.service.EmpresaService;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ViewScoped
@Named("buscaEmpresaWebBean")
public class EmpresaBuscaWebBean implements Serializable {

    private static final Logger LOG = LogManager.getLogger(EmpresaBuscaWebBean.class);

    private static final long serialVersionUID = 1L;

    @Inject
    private EmpresaService empresaService;
    @Inject
    private UserAuthenticationToken usuarioLogado;

    private EmpresaFiltroDTO filtro = new EmpresaFiltroDTO();

    private List<EmpresaDTO> empresasEncontradas = new ArrayList<>();

    private boolean buscaRealizada = false;

    private boolean isAdmin = false;

    private List<SegmentoEmpresaEnum> segmentos = new ArrayList<>(Arrays.asList(SegmentoEmpresaEnum.values()));

    private List<RegionalSenaiEnum> regionais = new ArrayList<>(Arrays.asList(RegionalSenaiEnum.values()));

    @PostConstruct
    public void init() {
        isAdmin = usuarioLogado.possuiRole(RoleEnum.ADMINISTRADOR);
    }

    public void buscar() {
        try {
            LOG.info("Iniciando busca de empresa com o(s) atributo(s) selecionado(s)...");
            ModelMapper modelMapper = new ModelMapper();
            empresasEncontradas = empresaService.buscar(filtro).stream()
                    .map(empresa -> modelMapper.map(empresa, EmpresaDTO.class))
                    .collect(Collectors.toList());
            if(!isAdmin) {
                LoginDTO loginDTO = (LoginDTO) usuarioLogado.getPrincipal();
                empresasEncontradas.removeIf(empresaDTO -> !Objects.equals(empresaDTO.getId(), loginDTO.getEmpresa().getId()));
            }
            buscaRealizada = true;
            LOG.info("Busca realizada com sucesso!");
        } catch (EmpresaException e) {
            LOG.error("Erro ao buscar empresa: " + e.getMessage());
            MessageUtils.returnMessageOnFail(e.getMessage());
        }
    }

    public EmpresaFiltroDTO getFiltro() {
        return filtro;
    }

    public void setFiltro(EmpresaFiltroDTO filtro) {
        this.filtro = filtro;
    }

    public boolean isBuscaRealizada() {
        return buscaRealizada;
    }

    public void setBuscaRealizada(boolean buscaRealizada) {
        this.buscaRealizada = buscaRealizada;
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

    public List<EmpresaDTO> getEmpresasEncontradas() {
        return empresasEncontradas;
    }

    public void setEmpresasEncontradas(List<EmpresaDTO> empresasEncontradas) {
        this.empresasEncontradas = empresasEncontradas;
    }
}
