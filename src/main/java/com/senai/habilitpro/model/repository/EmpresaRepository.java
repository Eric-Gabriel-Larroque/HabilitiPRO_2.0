package com.senai.habilitpro.model.repository;

import com.senai.habilitpro.model.dto.EmpresaFiltroDTO;
import com.senai.habilitpro.model.entity.Empresa;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class EmpresaRepository extends GenericRepository {
    public List<Empresa> verificaCnpjRepetido(String cnpj) {
        String jpql = "SELECT e FROM Empresa e WHERE e.cnpj = :cnpj";
        return entityManager.createQuery(jpql, Empresa.class)
                .setParameter("cnpj", cnpj)
                .getResultList();
    }

    public List<Empresa> findAll() {
        String jpql = "SELECT e FROM Empresa e";
        return entityManager.createQuery(jpql, Empresa.class).getResultList();
    }

    public List<Empresa> buscarComFiltro(EmpresaFiltroDTO filtro) {
        String jpql = "SELECT e FROM Empresa e ";
        String hql = montarHqlBuscaEmpresa(filtro, jpql);
        Query query = entityManager.createQuery(hql, Empresa.class);
        popularParametrosBuscaEmpresa(filtro, query);

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>();
        }
    }

    private String montarHqlBuscaEmpresa(EmpresaFiltroDTO filtro, String hql) {
        String andOrWhere = "WHERE ";


        if (filtro.getId() != null) {
            hql = hql.concat(andOrWhere).concat("e.id = :id ");
            andOrWhere = "AND ";
        }

        if (!StringUtils.isBlank(filtro.getCnpj())) {
            hql = hql.concat(andOrWhere).concat("e.cnpj = :cnpj ");
            andOrWhere = "AND ";
        }

        if (!StringUtils.isBlank(filtro.getNome())) {
            hql = hql.concat(andOrWhere).concat("LOWER(e.nome) LIKE :nome ");
            andOrWhere = "AND ";
        }

        if (!StringUtils.isBlank(filtro.getNomeFilial())) {
            hql = hql.concat(andOrWhere).concat("LOWER(e.nomeFilial) LIKE :nomeFilial ");
            andOrWhere = "AND ";
        }

        if (filtro.getSegmento() != null) {
            hql = hql.concat(andOrWhere).concat("e.segmento = :segmento ");
            andOrWhere = "AND ";
        }

        if (filtro.getRegionalSenai() != null) {
            hql = hql.concat(andOrWhere).concat("e.regionalSenai = :regionalSenai ");
        }

        return hql.concat("ORDER BY e.id");
    }

    private void popularParametrosBuscaEmpresa(EmpresaFiltroDTO filtro, Query query) {

        if (filtro.getId() != null) {
            query.setParameter("id", filtro.getId());
        }

        if (!StringUtils.isBlank(filtro.getCnpj())) {
            String cnpjFormatado = filtro.getCnpj().replaceAll("\\.", "")
                    .replaceAll("/", "")
                    .replaceAll("-", "");
            query.setParameter("cnpj", cnpjFormatado);
        }

        if (!StringUtils.isBlank(filtro.getNome())) {
            String nome = filtro.getNome() + "%";
            nome = nome.toLowerCase();
            query.setParameter("nome", nome);
        }

        if (!StringUtils.isBlank(filtro.getNomeFilial())) {
            String nomeFilial = filtro.getNomeFilial() + "%";
            nomeFilial = nomeFilial.toLowerCase();
            query.setParameter("nomeFilial", nomeFilial);
        }

        if (filtro.getSegmento() != null) {
            query.setParameter("segmento", filtro.getSegmento());
        }

        if (filtro.getRegionalSenai() != null) {
            query.setParameter("regionalSenai", filtro.getRegionalSenai());
        }

    }

    public Empresa getEmpresaByAvaliacao(Long avaliacaoId) {
        String jpql = "SELECT e FROM Empresa e " +
                "LEFT JOIN e.cursos c " +
                "LEFT JOIN c.modulos m " +
                "LEFT JOIN m.avaliacao a " +
                "WHERE a.id = :avaliacaoId";
        return entityManager.createQuery(jpql, Empresa.class)
                .setParameter("avaliacaoId", avaliacaoId)
                .getSingleResult();
    }

    public List<Empresa> getTodasEmpresas() {
        String jpql = "SELECT e FROM Empresa e ";
        return entityManager.createQuery(jpql, Empresa.class)
                .getResultList();
    }
}
