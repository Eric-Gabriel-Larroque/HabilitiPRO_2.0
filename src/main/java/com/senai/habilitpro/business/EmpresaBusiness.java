package com.senai.habilitpro.business;

import com.senai.habilitpro.config.mapstruct.EmpresaMapper;
import com.senai.habilitpro.config.modelmapper.ModelMapperConfig;
import com.senai.habilitpro.exception.EmpresaException;
import com.senai.habilitpro.exception.RegistroExistenteException;
import com.senai.habilitpro.exception.RegistroNaoEncontradoException;
import com.senai.habilitpro.model.dto.EmpresaDTO;
import com.senai.habilitpro.model.dto.EmpresaFiltroDTO;
import com.senai.habilitpro.model.dto.TrabalhadorFiltroDTO;
import com.senai.habilitpro.model.entity.*;
import com.senai.habilitpro.model.repository.EmpresaRepository;
import com.senai.habilitpro.service.TrabalhadorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.lang.reflect.Field;
import java.util.List;

public class EmpresaBusiness {

    private static final Logger LOG = LogManager.getLogger(EmpresaBusiness.class);

    @Inject
    private EmpresaRepository empresaRepository;
    @Inject
    private CursoBusiness cursoBusiness;
    @Inject
    private TrabalhadorService trabalhadorService;
    @Inject
    private UsuarioBusiness usuarioBusiness;
    @Inject
    private EnderecoBusiness enderecoBusiness;
    @Inject
    private ModelMapperConfig modelMapper;

    public Empresa cadastrar(EmpresaDTO empresa) throws EmpresaException {

        verificaCnpjRepetido(empresa.getCnpj());
        Empresa empresaEntity = EmpresaMapper.INSTANCE.fromGenericDtoToModel(empresa);
        validaEmpresa(empresaEntity);
        Empresa empresaCadastrada = persistirEmpresa(empresaEntity);
        LOG.info("Dados de Empresa persistidos com sucesso!");
        return empresaCadastrada;
    }

    public void validaEmpresa(Empresa empresa) {
        LOG.info("validando dados de empresa...");
        Endereco endereco = enderecoBusiness.buscarPorId(empresa.getEndereco().getId());
        Usuario usuario = usuarioBusiness.buscarPorId(empresa.getSupervisor().getId());

        LOG.info("Populando dados de usuário e endereço da empresa...");
        endereco.setEmpresa(empresa);
        empresa.setEndereco(endereco);
        usuario.setEmpresa(empresa);
        empresa.setSupervisor(usuario);
    }

    public Empresa persistirEmpresa(Empresa empresa) throws EmpresaException {
        LOG.info("Persistindo dados de Empresa no banco...");
        empresaRepository.persist(empresa);
        return empresa;
    }

    public void verificaCnpjRepetido(String cnpj) throws EmpresaException {
        LOG.info("Iniciando validação de duplicata do CNPJ...");
        String cnpjFormatado = cnpj.replaceAll("\\.", "").replaceAll("-", "")
                .replaceAll("/", "");

        LOG.info("Verificando se já existe empresa com o CNPJ cadastrado...");
        List<Empresa> empresas = empresaRepository.verificaCnpjRepetido(cnpjFormatado);

        if (!empresas.isEmpty()) {
            LOG.error("Empresa com CNPJ informado já se encontra no Banco de Dados.");
            throw new RegistroExistenteException(String.format("Empresa com CNPJ %s já cadastrada no sistema", cnpjFormatado));
        }
    }

    public List<Empresa> buscarComFiltro(EmpresaFiltroDTO filtro) {
        LOG.info("Realizando busca...");
        List<Empresa> empresas = empresaRepository.buscarComFiltro(filtro);
        if (empresas.size() == 0) {
            LOG.error("Não foi possível encontrar empresa com os parâmetros informados");
            throw new RegistroNaoEncontradoException(Empresa.class.getSimpleName());
        }
        return empresas;
    }

    public List<Empresa> getTodasEmpresas() {
        LOG.info("Buscando todas as empresas cadastradas no sistema...");
        return empresaRepository.getTodasEmpresas();
    }

    public void deleteById(Long empresaId) {
        Empresa empresa = buscarPorId(empresaId);
        verificaSeEmpresaPossuiCursos(empresaId);
        verificaSeEmpresaPossuiTrabalhadores(empresaId);

         LOG.info("Removendo empresa...");
        empresaRepository.remove(empresa);
    }

    public void verificaSeEmpresaPossuiCursos(Long empresaId) {
        LOG.info("Verificando se a empresa possui cursos relacionados ao seu nome...");
        List<Curso> cursosDaEmpresa = cursoBusiness.getByEmpresaId(empresaId);
        if (cursosDaEmpresa.size() > 0) {
            LOG.error("Empresa possui cursos vinculados");
            throw new RegistroExistenteException(String.format("Não é possível excluir a empresa de id %s pois a mesma possui cursos vinculados.", empresaId.toString()));
        }
    }

    public void verificaSeEmpresaPossuiTrabalhadores(Long empresaId) {
        LOG.info("Verificando se a empresa possui trabalhadore relacionados ao seu nome...");
        TrabalhadorFiltroDTO filtro = new TrabalhadorFiltroDTO();
        filtro.setEmpresa(new EmpresaDTO());
        filtro.getEmpresa().setId(empresaId);
        int qtdTrabalhadores = trabalhadorService.buscarComFiltroByEmpresa(filtro);
        if (qtdTrabalhadores > 0) {
            LOG.error("Empresa possui trabalhadores vinculados.");
            throw new RegistroExistenteException(String.format("Não é possível excluir a empresa de id %s pois a mesma possui trabalhadores vinculados.", empresaId.toString()));
        }
    }

    public Empresa buscarPorId(Long empresaId) {
        LOG.info("Verificando se existe a empresa selecionado no banco...");
        Empresa empresa = this.empresaRepository.find(Empresa.class, empresaId);
        if (empresa == null) {
            throw new RegistroNaoEncontradoException("Empresa", empresaId.toString());
        }
        return empresa;
    }

    public Empresa atualizaEmpresa(Long empresaId, Empresa empresaAtualizada) throws IllegalAccessException {
        Empresa empresaDesatualizada = buscarPorId(empresaId);
        verificaCnpjRepetido(empresaAtualizada.getCnpj());
        atualizaSupervisorEmpresa(empresaAtualizada, empresaDesatualizada);
        atualizaEnderecoEmpresa(empresaAtualizada, empresaDesatualizada);
        Empresa empresaParaSalvar = atualizaEntidadeEmpresa(empresaDesatualizada, empresaAtualizada);

        return empresaRepository.merge(empresaParaSalvar);
    }

    public void atualizaEnderecoEmpresa(Empresa empresaAtualizada, Empresa empresaDesatualizada) {
        LOG.info("Atualizando informações de endereço...");
        if (empresaAtualizada.getEndereco().getId() == null || empresaDesatualizada.getEndereco().getId().equals(empresaAtualizada.getEndereco().getId())) {
            empresaAtualizada.setEndereco(new Endereco());
            return;
        }
        Endereco endereco = enderecoBusiness.buscarPorId(empresaAtualizada.getEndereco().getId());
        if (endereco.getEmpresa() != null) {
            throw new EmpresaException("Endereço já vinculado a outra empresa", Response.Status.CONFLICT);
        }
        empresaAtualizada.setEndereco(endereco);
    }

    public void atualizaSupervisorEmpresa(Empresa empresaAtualizada, Empresa empresaDesatualizada) {
        LOG.info("Atualizando informações do supervisor...");
        if (empresaAtualizada.getSupervisor().getId() == null || empresaDesatualizada.getSupervisor().getId().equals(empresaAtualizada.getSupervisor().getId())) {
            empresaAtualizada.setSupervisor(new Usuario());
            return;
        }
        Usuario usuario = usuarioBusiness.buscarPorId(empresaAtualizada.getSupervisor().getId());
        if (usuario.getEmpresa() != null && usuario.getEmpresa().getId() != null) {
            throw new EmpresaException("O supervisor informado já possui empresa vinculada.", Response.Status.CONFLICT);
        }
        empresaAtualizada.setSupervisor(usuario);
    }

    public Empresa atualizaEntidadeEmpresa(Empresa empresaDesatualizada, Empresa empresaAtualizada) throws IllegalAccessException {
        LOG.info("Verificando mudanças e populando campos atualizados de empresa");
        for (Field field : empresaDesatualizada.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object valorFieldEmpresaAtualizada = field.get(empresaAtualizada);
            Object valorFieldEmpresaDesatualizada = field.get(empresaDesatualizada);
            if (valorFieldEmpresaAtualizada != null && !valorFieldEmpresaAtualizada.equals(valorFieldEmpresaDesatualizada)) {
                field.set(empresaDesatualizada, valorFieldEmpresaAtualizada);
            }
        }

        return empresaDesatualizada;
    }
}
