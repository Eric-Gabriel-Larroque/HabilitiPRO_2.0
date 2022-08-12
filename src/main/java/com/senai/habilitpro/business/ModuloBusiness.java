package com.senai.habilitpro.business;

import com.senai.habilitpro.exception.ModuloException;
import com.senai.habilitpro.model.dto.HabilidadeDTO;
import com.senai.habilitpro.model.dto.ModuloDTO;
import com.senai.habilitpro.model.entity.Curso;
import com.senai.habilitpro.model.entity.Habilidade;
import com.senai.habilitpro.model.entity.Modulo;
import com.senai.habilitpro.model.repository.ModuloRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class ModuloBusiness {

    private static final Logger LOG = LogManager.getLogger(ModuloBusiness.class);

    @Inject
    private ModuloRepository moduloRepository;

    @Inject
    private HabilidadeBusiness habilidadeBusiness;

    public void cadastrar(ModuloDTO moduloDTO) throws ModuloException {
        persistirModulo(moduloDTO);
        LOG.info("Dados de Módulo persistidos com sucesso!...");
    }

    private void persistirModulo(ModuloDTO moduloDTO) throws ModuloException {
        LOG.info("Persistindo dados de Modulo no banco...");
        Modulo modulo = new Modulo();

        LOG.info("Populando atributos de Módulo...");
        modulo.setNome(moduloDTO.getNome());
        modulo.setDataInicio(moduloDTO.getDataInicio());
        modulo.setDataTermino(moduloDTO.getDataTermino());

        LOG.info("Verificando se existe o curso selecionado no banco...");
        Curso curso = moduloRepository.find(Curso.class, moduloDTO.getIdCurso());

        if (curso == null) {
            LOG.error("Curso inexistente no banco.");
            throw new ModuloException("Curso não encontrado");
        }

        modulo.setCurso(curso);
        curso.getModulos().add(modulo);

        for (HabilidadeDTO habilidadeDTO : moduloDTO.getHabilidades()) {
            habilidadeBusiness.cadastrar(habilidadeDTO);

            Habilidade habilidade = moduloRepository.find(Habilidade.class, habilidadeDTO.getId());

            if (curso == null) {
                LOG.error("Habilidade inexistente no banco.");
                throw new ModuloException("Habilidade não encontrada");
            }

            modulo.getHabilidades().add(habilidade);
            habilidade.setModulo(modulo);
        }


        moduloRepository.persist(modulo);
        moduloDTO.setId(modulo.getId());


    }
}
