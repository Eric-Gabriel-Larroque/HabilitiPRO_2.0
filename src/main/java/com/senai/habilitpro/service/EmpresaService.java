package com.senai.habilitpro.service;

import com.senai.habilitpro.business.EmpresaBusiness;
import com.senai.habilitpro.exception.EmpresaException;
import com.senai.habilitpro.exception.RegistroNaoEncontradoException;
import com.senai.habilitpro.model.dto.EmpresaDTO;
import com.senai.habilitpro.model.dto.EmpresaFiltroDTO;
import com.senai.habilitpro.model.entity.Empresa;
import com.senai.habilitpro.model.repository.EmpresaRepository;
import com.senai.habilitpro.security.JwtTokenGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static com.senai.habilitpro.security.JwtTokenGenerator.*;

@Stateless
public class EmpresaService {

    private static final Logger LOG = LogManager.getLogger(EmpresaService.class);

    @Inject
    private EmpresaBusiness empresaBusiness;

    @Inject
    private EmpresaRepository empresaRepository;

    public Empresa cadastrar(EmpresaDTO empresa) throws EmpresaException {
        return empresaBusiness.cadastrar(empresa);
    }

    public List<EmpresaDTO> findAll() {
        LOG.info("buscando todas as empresas disponíveis no banco...");
        ModelMapper modelMapper = new ModelMapper();
        List<Empresa> empresas = empresaRepository.findAll();

        List<EmpresaDTO> empresasDTO = new ArrayList<>();
        for (Empresa empresa : empresas) {
            EmpresaDTO empresaDTO = modelMapper.map(empresa, EmpresaDTO.class);
            empresasDTO.add(empresaDTO);
        }
        return empresasDTO;
    }

    public List<Empresa> buscar(EmpresaFiltroDTO filtro) {
        LOG.info("Verificando se o usuário possui permissão para acessar informação de demais empresas...");
        if(!isAdmin())
            filtro.setId((long) JwtTokenGenerator.getEmpresaId());
        return empresaBusiness.buscarComFiltro(filtro);
    }

    public Empresa buscarPorId(Long idEmpresa) {
        Empresa empresa = empresaBusiness.buscarPorId(idEmpresa);
        LOG.info("Verificando se o usuário possui permissão para acessar informação de demais empresas...");
        if(!isAdmin() && !empresa.getId().equals((long) getEmpresaId())){
            LOG.error("Não foi possível encontrar a empresa com o id especificado.");
            throw new RegistroNaoEncontradoException("Empresa",idEmpresa.toString());
        }
        return empresa;
    }

    public List<Empresa> getTodasEmpresas() {
        return empresaBusiness.getTodasEmpresas();
    }

    public void deleteById(Long empresaId) {
        empresaBusiness.deleteById(empresaId);
    }

    public Empresa atualizaEmpresa(Long empresaId, Empresa empresa) throws IllegalAccessException {
        return empresaBusiness.atualizaEmpresa(empresaId, empresa);
    }
}
