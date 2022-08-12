package com.senai.habilitpro.bean;

import com.senai.habilitpro.config.mapstruct.TrabalhadorMapper;
import com.senai.habilitpro.exception.TrabalhadorException;
import com.senai.habilitpro.model.dto.CursoDTO;
import com.senai.habilitpro.model.dto.EmpresaDTO;
import com.senai.habilitpro.model.dto.TrabalhadorDTO;
import com.senai.habilitpro.model.entity.Curso;
import com.senai.habilitpro.service.CursoService;
import com.senai.habilitpro.service.EmpresaService;
import com.senai.habilitpro.service.TrabalhadorService;
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
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ViewScoped
@Named("trabalhadorCadastroWebBean")
public class TrabalhadorCadastroWebBean implements Serializable {

    private static final Logger LOG = LogManager.getLogger(TrabalhadorCadastroWebBean.class);

    @Inject
    private TrabalhadorService trabalhadorService;
    @Inject
    private EmpresaService empresaService;
    @Inject
    private CursoService cursoService;

    private static final long serialVersionUID = 1L;

    private TrabalhadorDTO trabalhadorDTO = new TrabalhadorDTO();
    private List<EmpresaDTO> empresaList = new ArrayList<>();
    private List<CursoDTO> cursosByEmpresa = new ArrayList<>();
    private Date dataAtual = new Date();
    private boolean isEmpresaSelecinada = false;

    public List<CursoDTO> getCursosByEmpresa() {
        return cursosByEmpresa;
    }

    public boolean isEmpresaSelecinada() {
        return isEmpresaSelecinada;
    }

    public Date getDataAtual() {
        return dataAtual;
    }

    public TrabalhadorDTO getTrabalhadorDTO() {
        return trabalhadorDTO;
    }

    public List<EmpresaDTO> getEmpresaList() {
        return empresaList;
    }

    @PostConstruct
    public void init() {
        this.empresaList = empresaService.findAll();
    }

    public void buscaByEmpresa() {
        Long empresaId = this.trabalhadorDTO.getIdEmpresa();
        if (empresaId == 0) {
            this.cursosByEmpresa = new ArrayList<>();
            this.isEmpresaSelecinada = false;
            return;
        }
        LOG.info("Iniciando busca de curso pelo id da empresa...");
        List<Curso> cursos = this.cursoService.getByEmpresaId(empresaId);
        ModelMapper modelMapper = new ModelMapper();
        this.cursosByEmpresa = cursos.stream().map(curso -> modelMapper.map(curso, CursoDTO.class)).collect(Collectors.toList());
        this.isEmpresaSelecinada = true;
    }

    public String cadastrar() {
        LOG.info("Iniciando método cadastrar trabalhador...");
        try {
            this.trabalhadorService.cadastrar(TrabalhadorMapper.INSTANCE.fromGenericDtoToEntity(this.trabalhadorDTO));
            MessageUtils.returnGlobalMessageOnSuccess("Salvo com sucesso!");
            LOG.info("Trabalhador persistido com sucesso!");
            return "/secure/trabalhadorCadastro?faces-redirect=true";
        } catch (TrabalhadorException e) {
            LOG.error("Erro ao persistir trabalhador: " + e.getMessage());
            MessageUtils.returnMessageOnFail(e.getMessage());
        } catch (Exception e) {
            LOG.error("Erro ao persistir trabalhador: " + e.getMessage());
            MessageUtils.returnMessageOnFail("Erro ao cadastrar curso");
        }
        return null;
    }
}
