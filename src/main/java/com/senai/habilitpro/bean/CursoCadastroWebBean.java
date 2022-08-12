package com.senai.habilitpro.bean;

import com.senai.habilitpro.exception.CursoException;
import com.senai.habilitpro.model.dto.CursoDTO;
import com.senai.habilitpro.model.dto.EmpresaDTO;
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

@ViewScoped
@Named("cursoCadastroWebBean")
public class CursoCadastroWebBean implements Serializable {

    private static final Logger LOG = LogManager.getLogger(CursoCadastroWebBean.class);

    private static final long serialVersionUID = 1L;

    @Inject
    private CursoService cursoService;

    @Inject
    private EmpresaService empresaService;

    private CursoDTO cursoDTO = new CursoDTO();

    private List<EmpresaDTO> empresas = new ArrayList<>();

    @PostConstruct
    public void init() {
        this.empresas = empresaService.findAll();
    }

    public String salvar() {
        LOG.info("Iniciando método salvar...");
        try {
            LOG.info("Iniciando método de cadastro de curso...");
            cursoService.cadastrar(cursoDTO);
            MessageUtils.returnGlobalMessageOnSuccess("Salvo com sucesso!");
            LOG.info("Curso persistido com sucesso!");
            return "/secure/cursoCadastro.xhtml?faces-redirect=true";
        } catch (CursoException e) {
            LOG.error("Erro ao persistir curso: "+e.getErros());
            MessageUtils.returnMessageOnFail(e.getErros());
        } catch (Exception e) {
            LOG.error("Erro ao persistir curso: "+e.getMessage());
            MessageUtils.returnMessageOnFail("Erro ao cadastrar curso");
        }
        return null;
    }

    public CursoDTO getCursoDTO() {
        return cursoDTO;
    }

    public void setCursoDTO(CursoDTO cursoDTO) {
        this.cursoDTO = cursoDTO;
    }

    public List<EmpresaDTO> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(List<EmpresaDTO> empresas) {
        this.empresas = empresas;
    }
}
