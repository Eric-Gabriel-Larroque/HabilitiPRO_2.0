package com.senai.habilitpro.model.repository;

import com.senai.habilitpro.model.dto.CursoFiltroDTO;
import com.senai.habilitpro.model.entity.Curso;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class CursoRepository extends GenericRepository {

    public List<Curso> gerarNumeroSequencial(String ocupacao, Long idEmpresa) {
        String jpql = "SELECT c FROM Curso c WHERE c.empresa.id = :idEmpresa AND c.ocupacao = :ocupacao";

        return entityManager.createQuery(jpql, Curso.class)
                .setParameter("ocupacao", ocupacao)
                .setParameter("idEmpresa", idEmpresa)
                .getResultList();
    }

    public List<Curso> buscar(CursoFiltroDTO filtro) {
        String hql = montarHqlBuscaCurso(filtro);
        Query query = entityManager.createQuery(hql, Curso.class);
        popularParametrosBuscaCurso(filtro, query);
        return query.getResultList();
    }

    private void popularParametrosBuscaCurso(CursoFiltroDTO filtro, Query query) {
        if (filtro.getId() != null) {
            query.setParameter("id", filtro.getId());
        }

        if (filtro.getIdEmpresa() != null) {
            query.setParameter("idEmpresa", filtro.getIdEmpresa());
        }

        if (!StringUtils.isBlank(filtro.getNome())) {
            String nome = "%" + filtro.getNome() + "%";
            nome = nome.toLowerCase();
            query.setParameter("nome", nome);
        }

        if (!StringUtils.isBlank(filtro.getOcupacao())) {
            String ocupacao = "%" + filtro.getOcupacao() + "%";
            ocupacao = ocupacao.toLowerCase();
            query.setParameter("ocupacao", ocupacao);
        }
    }

    private String montarHqlBuscaCurso(CursoFiltroDTO filtro) {
        String hql = "SELECT c " +
                "FROM Curso c ";
        String andOrWhere = "WHERE ";

        if (filtro.getIdEmpresa() != null) {
            hql = hql.concat("JOIN c.empresa e ");

            hql = hql.concat(andOrWhere).concat("e.id = :idEmpresa ");
            andOrWhere = "AND ";
        }

        if (filtro.getId() != null) {
            hql = hql.concat(andOrWhere).concat("c.id = :id ");
            andOrWhere = "AND ";
        }

        if (!StringUtils.isBlank(filtro.getNome())) {
            hql = hql.concat(andOrWhere).concat("LOWER(c.nome) LIKE :nome ");
            andOrWhere = "AND ";
        }

        if (!StringUtils.isBlank(filtro.getOcupacao())) {
            hql = hql.concat(andOrWhere).concat("LOWER(c.ocupacao) LIKE :ocupacao ");
        }

        return hql.concat("ORDER BY c.id");

    }

    public List<Curso> findByEmpresaId(Object empresaId) {
        Long id = (Long) empresaId;
        String jpql = "SELECT c FROM Curso c WHERE c.empresa.id = :id";
        return entityManager.createQuery(jpql, Curso.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Curso> findAll() {
        String jpql = "SELECT c FROM Curso c";

        return entityManager.createQuery(jpql, Curso.class)
                .getResultList();
    }
}
