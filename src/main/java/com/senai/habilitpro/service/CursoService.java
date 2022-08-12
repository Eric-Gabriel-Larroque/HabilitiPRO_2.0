package com.senai.habilitpro.service;

import com.senai.habilitpro.business.CursoBusiness;
import com.senai.habilitpro.exception.CursoException;
import com.senai.habilitpro.exception.GenericException;
import com.senai.habilitpro.model.dto.CursoDTO;
import com.senai.habilitpro.model.dto.CursoFiltroDTO;
import com.senai.habilitpro.model.entity.Curso;
import com.senai.habilitpro.model.entity.Trabalhador;
import com.senai.habilitpro.model.repository.CursoRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class CursoService {

    private static final Logger LOG = LogManager.getLogger(CursoService.class);

    @Inject
    private CursoBusiness cursoBusiness;

    @Inject
    private CursoRepository cursoRepository;

    public void cadastrar(CursoDTO cursoDTO) throws GenericException {
        cursoBusiness.cadastrar(cursoDTO);
    }

    public List<CursoDTO> findAll() {
        LOG.info("Iniciando listagem de todos os cursos disponíveis...");
        ModelMapper modelMapper = new ModelMapper();
        List<Curso> cursos = cursoRepository.findAll();

        List<CursoDTO> cursosDTO = new ArrayList<>();
        for (Curso curso : cursos) {
            CursoDTO cursoDTO = modelMapper.map(curso, CursoDTO.class);
            cursosDTO.add(cursoDTO);
        }
        return cursosDTO;
    }

    public List<CursoDTO> buscar(CursoFiltroDTO filtro) throws CursoException {
        return cursoBusiness.buscar(filtro);
    }

    public List<Curso> getByEmpresaId(Long empresaId) {
        return cursoBusiness.getByEmpresaId(empresaId);
    }

    public List<CursoDTO> getTodosCursos() {
        LOG.info("Iniciando busca de todos os cursos cadastrados no sistema...");
        return cursoBusiness.getTodosCursos();
    }

    public void removeTrabalhadorDosCursos(Trabalhador trabalhador) {
        cursoBusiness.removeTrabalhadorDosCursos(trabalhador);
    }
}
