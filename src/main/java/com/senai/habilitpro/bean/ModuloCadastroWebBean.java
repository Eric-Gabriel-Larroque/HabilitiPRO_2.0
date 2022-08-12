package com.senai.habilitpro.bean;

import com.senai.habilitpro.exception.ModuloException;
import com.senai.habilitpro.model.dto.CursoDTO;
import com.senai.habilitpro.model.dto.HabilidadeDTO;
import com.senai.habilitpro.model.dto.ModuloDTO;
import com.senai.habilitpro.service.CursoService;
import com.senai.habilitpro.service.ModuloService;
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

@ViewScoped
@Named("moduloCadastroWebBean")
public class ModuloCadastroWebBean implements Serializable {

    private static final Logger LOG = LogManager.getLogger(ModuloCadastroWebBean.class);

    private static final long serialVersionUID = 1L;

    @Inject
    private ModuloService moduloService;

    @Inject
    private CursoService cursoService;

    private List<CursoDTO> cursos = new ArrayList<>();

    private ModuloDTO moduloDTO = new ModuloDTO();

    @PostConstruct
    public void init() {
        cursos = cursoService.findAll();
        addHabilidade();
    }

    public String salvar() {
        LOG.info("Iniciando método salvar...");
        try {
            LOG.info("Iniciando método de cadastro de módulo...");
            moduloService.cadastrar(moduloDTO);
            MessageUtils.returnGlobalMessageOnSuccess("Salvo com sucesso!");
            LOG.info("Módulo persistido com sucesso!");
            return "/secure/moduloCadastro.xhtml?faces-redirect=true";
        } catch (ModuloException e) {
            LOG.error("Erro ao persistir módulo: " + e.getErros());
            MessageUtils.returnMessageOnFail(e.getErros());
        } catch (Exception e) {
            LOG.error("Erro ao persistir módulo: " + e.getMessage());
            MessageUtils.returnMessageOnFail("Erro ao cadastrar módulo");
        }
        return null;
    }

    public List<CursoDTO> getCursos() {
        return cursos;
    }

    public void setCursos(List<CursoDTO> cursos) {
        this.cursos = cursos;
    }

    public ModuloDTO getModuloDTO() {
        return moduloDTO;
    }

    public void setModuloDTO(ModuloDTO moduloDTO) {
        this.moduloDTO = moduloDTO;
    }

    public void addHabilidade() {
        this.moduloDTO.getHabilidades().add(new HabilidadeDTO());
    }

    public void removerHabilidade() {
        this.moduloDTO.getHabilidades().remove(this.moduloDTO.getHabilidades().size() - 1);
    }
}
