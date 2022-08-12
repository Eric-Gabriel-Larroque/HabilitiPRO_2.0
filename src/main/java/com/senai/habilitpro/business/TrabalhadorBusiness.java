package com.senai.habilitpro.business;

import com.senai.habilitpro.config.modelmapper.ModelMapperConfig;
import com.senai.habilitpro.exception.RegistroExistenteException;
import com.senai.habilitpro.exception.RegistroNaoEncontradoException;
import com.senai.habilitpro.model.dto.TrabalhadorFiltroDTO;
import com.senai.habilitpro.model.entity.Empresa;
import com.senai.habilitpro.model.entity.Nota;
import com.senai.habilitpro.model.entity.Trabalhador;
import com.senai.habilitpro.model.repository.TrabalhadorRepository;
import com.senai.habilitpro.service.CursoService;
import com.senai.habilitpro.service.EmpresaService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TrabalhadorBusiness {

    private static final Logger LOG = LogManager.getLogger(TrabalhadorBusiness.class);

    @Inject
    private TrabalhadorRepository trabalhadorRepository;
    @Inject
    private EmpresaService empresaService;
    @Inject
    private CursoService cursoService;
    @Inject
    private ModelMapperConfig modelMapper;

    public Trabalhador cadastrar(Trabalhador trabalhador) {
        LOG.info("Persistindo dados de Trabalhador no banco...");
        Empresa empresa = empresaService.buscarPorId(trabalhador.getEmpresa().getId());
        verificaSeExisteTrabalhadorComCpf(trabalhador.getCpf());

        trabalhador.setEmpresa(empresa);
        LOG.info("Persistindo trabalhador...");
        trabalhadorRepository.persist(trabalhador);
        LOG.info("Dados de Trabalhador persistidos com sucesso!");
        return trabalhador;
    }

    public void verificaSeExisteTrabalhadorComCpf(String cpf) {
        LOG.info("Verificando se já existe Trabalhador com o CPF informado no banco...");
        if (trabalhadorRepository.findByCpf(cpf) != null) {
            LOG.error("Já existe trabalhador com o CPF informado!");
            throw new RegistroExistenteException("Já existe trabalhador com o CPF informado: " + cpf);
        }
    }

    public List<Trabalhador> buscarComFiltro(TrabalhadorFiltroDTO trabalhadorFiltro) {
        LOG.info("Iniciando query de busca de Trabalhador...");
        List<Trabalhador> trabalhadores = trabalhadorRepository.filtrar(trabalhadorFiltro);
        if (trabalhadores.size() == 0) {
            throw new RegistroNaoEncontradoException(Trabalhador.class.getSimpleName());
        }
        return trabalhadores;
    }

    public int buscaComFiltroByEmpresa(TrabalhadorFiltroDTO trabalhadorFiltro) {
        LOG.info("Iniciando query de busca de Trabalhador...");
        List<Trabalhador> trabalhadores = trabalhadorRepository.filtrar(trabalhadorFiltro);
        return trabalhadores.size();
    }

    public List<Trabalhador> getTrabalhadoresByAvaliacaoId(Long avaliacaoId) {
        LOG.info("Iniciando query de busca de Trabalhadores por Id da Avaliação...");
        List<Trabalhador> trabalhadores = trabalhadorRepository.getTrabalhadoresByAvaliacaoId(avaliacaoId);
        trabalhadores = trabalhadores.stream().map(trabalhador -> {
            Set<Nota> notas = trabalhador.getNotas().stream().filter(nota -> nota.getAvaliacao().getId().equals(avaliacaoId)).collect(Collectors.toSet());
            trabalhador.setNotas(notas);
            return trabalhador;
        }).collect(Collectors.toList());
        return trabalhadores;
    }

    public Trabalhador buscarPorId(Long trabalhadorId) {
        LOG.info("Buscando trabalhador por id...");
        Trabalhador trabalhador = trabalhadorRepository.find(Trabalhador.class, trabalhadorId);
        if (trabalhador == null) {
            LOG.error("Trabalhador não encontrado.");
            throw new RegistroNaoEncontradoException("Trabalhador", trabalhadorId.toString());
        }
        return trabalhador;
    }

    public void removeDosCursos(Long trabalhadorId) {
        LOG.info("Removendo trabalhador por id...");
        Trabalhador trabalhador = buscarPorId(trabalhadorId);
        cursoService.removeTrabalhadorDosCursos(trabalhador);
    }

    public void removePorId(Long trabalhadorId) {
        LOG.info("Removendo trabalhador por id...");
        Trabalhador trabalhador = buscarPorId(trabalhadorId);
        cursoService.removeTrabalhadorDosCursos(trabalhador);
        LOG.info("Removendo trabalhador encontrado...");
        trabalhadorRepository.remove(trabalhador);
    }


    public Trabalhador atualizar(Long trabalhadorId, Trabalhador trabalhadorAtualizado) throws IllegalAccessException {
        Trabalhador trabalhadorDesatualizado = buscarPorId(trabalhadorId);
        if (trabalhadorAtualizado.getCpf() != null) {
            verificaSeExisteTrabalhadorComCpf(trabalhadorAtualizado.getCpf());
        }
        Trabalhador trabalhadorNovo = atualizaEntidadeTrabalhador(trabalhadorDesatualizado, trabalhadorAtualizado);
        trabalhadorRepository.merge(trabalhadorNovo);
        LOG.info("Trabalhador atualizado com sucesso!");
        return trabalhadorNovo;
    }

    public Trabalhador atualizaEntidadeTrabalhador(Trabalhador trabalhadorDesatualizado, Trabalhador trabalhadorAtualizado) throws IllegalAccessException {
            LOG.info("Atualizando campos de trabalhador...");
        for (Field field : trabalhadorDesatualizado.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object valorFieldAtualizado = field.get(trabalhadorAtualizado);
            Object valorFieldDesatualizado = field.get(trabalhadorDesatualizado);
            if ("empresa".equals(field.getName()) && trabalhadorAtualizado.getEmpresa().getId() != null) {
                Empresa empresa = empresaService.buscarPorId(trabalhadorAtualizado.getEmpresa().getId());
                field.set(trabalhadorDesatualizado, empresa);
                continue;
            } else if ("empresa".equals(field.getName())) {
                field.set(trabalhadorDesatualizado, trabalhadorDesatualizado.getEmpresa());
                continue;
            }
            if (valorFieldAtualizado != null && !valorFieldAtualizado.equals(valorFieldDesatualizado)) {
                field.set(trabalhadorDesatualizado, valorFieldAtualizado);
            }
        }

        return trabalhadorDesatualizado;
    }
}
