package com.senai.habilitpro.service;

import com.senai.habilitpro.business.TrabalhadorBusiness;
import com.senai.habilitpro.exception.RegistroNaoEncontradoException;
import com.senai.habilitpro.model.dto.EmpresaDTO;
import com.senai.habilitpro.model.dto.TrabalhadorFiltroDTO;
import com.senai.habilitpro.model.entity.Trabalhador;
import com.senai.habilitpro.model.repository.TrabalhadorRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

import static com.senai.habilitpro.security.JwtTokenGenerator.getEmpresaId;
import static com.senai.habilitpro.security.JwtTokenGenerator.isAdmin;

@Stateless
public class TrabalhadorService {

    private static final Logger LOG = LogManager.getLogger(TrabalhadorService.class);

    @Inject
    private TrabalhadorBusiness trabalhadorBusiness;

    @Inject
    private TrabalhadorRepository trabalhadorRepository;

    public Trabalhador cadastrar(Trabalhador trabalhador) {
        return trabalhadorBusiness.cadastrar(trabalhador);
    }

    public List<Trabalhador> buscarComFiltro(TrabalhadorFiltroDTO trabalhadorFiltro) {
        LOG.info("Verificando se usuário possui permissões de administrador...");
        if(!isAdmin()) {
            trabalhadorFiltro.setEmpresa(new EmpresaDTO());
            trabalhadorFiltro.getEmpresa().setId((long) getEmpresaId());
        }
        return trabalhadorBusiness.buscarComFiltro(trabalhadorFiltro);
    }

    public int buscarComFiltroByEmpresa(TrabalhadorFiltroDTO filtroDTO) {
        return trabalhadorBusiness.buscaComFiltroByEmpresa(filtroDTO);
    }

    public List<Trabalhador> getTrabalhadoresByAvaliacaoId(Long avaliacaoId) {
        return trabalhadorBusiness.getTrabalhadoresByAvaliacaoId(avaliacaoId);
    }

    public Trabalhador buscarPorId(Long trabalhadorId) {
        Trabalhador trabalhador = trabalhadorBusiness.buscarPorId(trabalhadorId);
        LOG.info("Verificando permissões de administrador do usuário...");
        if (!isAdmin() && !trabalhador.getEmpresa().getId().equals((long) getEmpresaId())) {
            LOG.error("Trabalhador com o id informado não encontrado.");
            throw new RegistroNaoEncontradoException("Trabalhador",trabalhadorId.toString());
        }
        return trabalhador;
    }

    public void removePorId(Long trabalhadorId) {
        trabalhadorBusiness.removePorId(trabalhadorId);
    }

    public Trabalhador atualizar(Long trabalhadorId, Trabalhador trabalhador) throws IllegalAccessException {
        return trabalhadorBusiness.atualizar(trabalhadorId, trabalhador);
    }

    public void removeDosCursos(Long trabalhadorId) {
        trabalhadorBusiness.removeDosCursos(trabalhadorId);
    }
}
