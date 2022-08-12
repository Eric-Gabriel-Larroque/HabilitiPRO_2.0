package com.senai.habilitpro.business;

import com.senai.habilitpro.config.modelmapper.ModelMapperConfig;
import com.senai.habilitpro.exception.CursoException;
import com.senai.habilitpro.exception.GenericException;
import com.senai.habilitpro.model.dto.CursoDTO;
import com.senai.habilitpro.model.dto.CursoFiltroDTO;
import com.senai.habilitpro.model.entity.Curso;
import com.senai.habilitpro.model.entity.Empresa;
import com.senai.habilitpro.model.entity.Trabalhador;
import com.senai.habilitpro.model.repository.CursoRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CursoBusiness {

    private static final Logger LOG = LogManager.getLogger(CursoBusiness.class);

    @Inject
    private CursoRepository cursoRepository;
    @Inject
    private ModelMapperConfig modelMapper;

    public void cadastrar(CursoDTO cursoDTO) throws GenericException {
        persistirCurso(cursoDTO);
        LOG.info("Dados de Curso persistidos com sucesso!...");
    }

    private void persistirCurso(CursoDTO cursoDTO) throws GenericException {
        LOG.info("Persistindo dados de Curso no banco...");

        Curso curso = new Curso();

        LOG.info("Populando atributos de curso...");
        curso.setOcupacao(cursoDTO.getOcupacao());
        curso.setDescricao(cursoDTO.getDescricao());

        LOG.info("Verificando se existe curso com a empresa selecionada...");
        Empresa empresa = cursoRepository.find(Empresa.class, cursoDTO.getIdEmpresa());

        if (empresa == null) {
            LOG.error("Empresa não existente no banco.");
            throw new CursoException("Empresa não encontrada");
        }

        LOG.info("Empresa encontrada! Inserindo empresa...");
        curso.setEmpresa(empresa);

        int numeroSequencial = gerarNumeroSequencial(cursoDTO);

        curso.setNome(cursoDTO.getOcupacao() + empresa.getNome() + numeroSequencial + LocalDate.now().getYear());
        curso.setApelido(cursoDTO.getOcupacao() + numeroSequencial);

        empresa.getCursos().add(curso);
        cursoRepository.persist(curso);
        cursoDTO.setId(curso.getId());

    }

    private int gerarNumeroSequencial(CursoDTO cursoDTO) {
        LOG.info("Iniciando geração de número sequencial para nome e apelido do curso...");

        int numeroSequencial = cursoRepository.gerarNumeroSequencial(cursoDTO.getOcupacao(), cursoDTO.getIdEmpresa()).size();

        return numeroSequencial > 0 ? numeroSequencial + 1 : 1;
    }

    public List<CursoDTO> buscar(CursoFiltroDTO filtro) throws CursoException {
        LOG.info("Inicializando query para busca de cursos...");
        List<Curso> cursosEncontrados = cursoRepository.buscar(filtro);
        return cursosEncontrados.stream()
                .map(curso -> modelMapper.map(curso, CursoDTO.class))
                .collect(Collectors.toList());
    }

    public List<CursoDTO> getTodosCursos() {
        List<Curso> cursos = cursoRepository.findAll();
        return cursos.stream().map(curso -> modelMapper.map(curso, CursoDTO.class)).collect(Collectors.toList());
    }

    public List<Curso> getByEmpresaId(Long empresaId) {
        LOG.info("Iniciando query de busca de cursos pelo id da empresa");
        return cursoRepository.findByEmpresaId(empresaId);
    }

    public void removeTrabalhadorDosCursos(Trabalhador trabalhador) {
        LOG.info("Iniciando remoção de trabalhador dos cursos...");
        Set<Curso> cursos = trabalhador.getCursos();
        cursos.forEach(curso -> {
            curso.removeTrabalhador(trabalhador);
            cursoRepository.merge(curso);
        });
    }
}
