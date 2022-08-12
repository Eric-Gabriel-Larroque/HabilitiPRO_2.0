package com.senai.habilitpro.bean;

import com.senai.habilitpro.config.modelmapper.ModelMapperConfig;
import com.senai.habilitpro.model.dto.EmpresaDTO;
import com.senai.habilitpro.model.dto.NotaDTO;
import com.senai.habilitpro.model.dto.TrabalhadorFiltroDTO;
import com.senai.habilitpro.service.*;
import com.senai.habilitpro.utils.NotaUtils;
import org.omnifaces.cdi.ViewScoped;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ViewScoped
@Named("relatorioAvaliacoesWebBean")
public class RelatorioAvaliacoesWebBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private AvaliacaoService avaliacaoService;
    @Inject
    private CursoService cursoService;
    @Inject
    private TrabalhadorService trabalhadorService;
    @Inject
    private EmpresaService empresaService;
    @Inject
    private RelatorioAvaliacoesService relatorioService;
    @Inject
    private ModelMapperConfig modelMapper;

    private int quantidadeAvaliacoes;
    private int quantidadeCursos;
    private int quantidadeTrabalhadores;
    private List<EmpresaDTO> empresas = new ArrayList<>();

    public int getQuantidadeAvaliacoes() {
        return quantidadeAvaliacoes;
    }

    public int getQuantidadeCursos() {
        return quantidadeCursos;
    }

    public int getQuantidadeTrabalhadores() {
        return quantidadeTrabalhadores;
    }

    public List<EmpresaDTO> getEmpresas() {
        return empresas;
    }

    @PostConstruct
    public void init() {
        quantidadeAvaliacoes = avaliacaoService.getTodasAvaliacoes().size();
        quantidadeCursos = cursoService.getTodosCursos().size();
        quantidadeTrabalhadores = trabalhadorService.buscarComFiltro(new TrabalhadorFiltroDTO()).size();
        empresas = empresaService.getTodasEmpresas().stream()
                .map(empresa -> modelMapper.map(empresa, EmpresaDTO.class))
                .collect(Collectors.toList());;
    }

    public BigDecimal calculaNotaMedia(List<NotaDTO> notas) {
        return NotaUtils.calculaNotaMedia(notas);
    }

    public BigDecimal calculaNotaMaxima(List<NotaDTO> notas) {
        return NotaUtils.calculaNotaMaxima(notas);
    }

    public BigDecimal calculaNotaMinima(List<NotaDTO> notas) {
        return NotaUtils.calculaNotaMinima(notas);
    }

    public void imprimir() throws IOException {
        this.relatorioService.imprimir();
    }
}
