package com.senai.habilitpro.bean;

import com.senai.habilitpro.config.mapstruct.TrabalhadorMapper;
import com.senai.habilitpro.exception.AvaliacaoException;
import com.senai.habilitpro.exception.NotaException;
import com.senai.habilitpro.model.dto.AvaliacaoDTO;
import com.senai.habilitpro.model.dto.response.NotaResponseDTO;
import com.senai.habilitpro.model.dto.response.TrabalhadorCompleteResponseDTO;
import com.senai.habilitpro.model.entity.Trabalhador;
import com.senai.habilitpro.security.UserAuthenticationToken;
import com.senai.habilitpro.service.AvaliacaoService;
import com.senai.habilitpro.service.EmpresaService;
import com.senai.habilitpro.service.NotaService;
import com.senai.habilitpro.service.TrabalhadorService;
import com.senai.habilitpro.utils.MessageUtils;
import org.omnifaces.cdi.Param;
import org.omnifaces.cdi.ViewScoped;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ViewScoped
@Named("avaliacaoWebBean")
public class AvaliacaoWebBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private TrabalhadorService trabalhadorService;
    @Inject
    private UserAuthenticationToken userAuthenticationToken;
    @Inject
    private EmpresaService empresaService;
    @Inject
    private AvaliacaoService avaliacaoService;
    @Inject
    private NotaService notaService;

    private List<TrabalhadorCompleteResponseDTO> trabalhadores = new ArrayList<>();
    private AvaliacaoDTO avaliacao;

    @Param(name = "id")
    private Long avaliacaoId;

    public Long getAvaliacaoId() {
        return avaliacaoId;
    }

    public AvaliacaoDTO getAvaliacao() {
        return avaliacao;
    }

    public List<TrabalhadorCompleteResponseDTO> getTrabalhadores() {
        return trabalhadores;
    }

    public void init() throws IOException {
        try {
            this.avaliacao = avaliacaoService.findById(avaliacaoId);
            List<Trabalhador> trabalhadoresByAvaliacaoId = trabalhadorService.getTrabalhadoresByAvaliacaoId(avaliacaoId);
            this.trabalhadores = trabalhadoresByAvaliacaoId.stream().map(TrabalhadorMapper.INSTANCE::fromEntityToCompleteResponse).collect(Collectors.toList());
            this.trabalhadores = this.trabalhadores.stream().map(trabalhador -> {
                if (trabalhador.getNotas().size() < 1) {
                    trabalhador.setNotas(List.of(new NotaResponseDTO()));
                }
                return trabalhador;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            MessageUtils.returnGlobalMessageOnFail("Avaliação com id " + avaliacaoId + " não encontrada!");
            FacesContext.getCurrentInstance().getExternalContext().redirect("/index.xhtml");
        }
    }

    public void salvar() throws IOException {
        try {
            List<Trabalhador> trabalhadoresEntity = trabalhadores.stream().map(TrabalhadorMapper.INSTANCE::fromCompleteResponseToEntity).collect(Collectors.toList());
            this.notaService.salvar(trabalhadoresEntity, avaliacaoId);
            MessageUtils.returnGlobalMessageOnSuccess("Notas salvas com sucesso!");
            FacesContext.getCurrentInstance().getExternalContext().redirect("/avaliacao.xhtml?id=" + avaliacaoId);
        } catch (NotaException | AvaliacaoException e) {
            MessageUtils.returnGlobalMessageOnFail(e.getErros());
            FacesContext.getCurrentInstance().getExternalContext().redirect("/avaliacao.xhtml?id=" + avaliacaoId);
        } catch (Exception e) {
            MessageUtils.returnGlobalMessageOnFail("Houve um erro ao salvar as notas, atualize a página e tente novamente!");
        }
    }

    public void concluir() {
        try {
            this.avaliacaoService.concluir(avaliacaoId);
            MessageUtils.returnGlobalMessageOnSuccess("Avaliação concluída com sucesso!");
            FacesContext.getCurrentInstance().getExternalContext().redirect("/avaliacao.xhtml?id=" + avaliacaoId);
        } catch (AvaliacaoException e) {
            MessageUtils.returnGlobalMessageOnFail(e.getErros());
        } catch (Exception e) {
            MessageUtils.returnGlobalMessageOnFail("Houve um erro ao concluir a avaliação, atualize a página e tente novamente!");
        }
    }

}
