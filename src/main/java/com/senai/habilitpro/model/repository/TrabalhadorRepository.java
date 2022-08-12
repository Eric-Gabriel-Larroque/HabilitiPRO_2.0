package com.senai.habilitpro.model.repository;

import com.senai.habilitpro.model.dto.TrabalhadorFiltroDTO;
import com.senai.habilitpro.model.entity.Curso;
import com.senai.habilitpro.model.entity.Trabalhador;
import org.modelmapper.ModelMapper;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TrabalhadorRepository extends GenericRepository {

    public Trabalhador findByCpf(String cpf) {
        String jpql = "SELECT t FROM Trabalhador t WHERE t.cpf = :cpf";
        try {
            return entityManager.createQuery(jpql, Trabalhador.class)
                    .setParameter("cpf", cpf)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Trabalhador> filtrar(TrabalhadorFiltroDTO trabalhadorFiltro) {
        String jpql = "SELECT t FROM Trabalhador t ";
        TypedQuery<Trabalhador> query = setQueryFiltroTrabalhador(trabalhadorFiltro, jpql);
        return setQueryParametersFiltroTrabalhador(trabalhadorFiltro, query).getResultList();
    }

    private TypedQuery<Trabalhador> setQueryFiltroTrabalhador(TrabalhadorFiltroDTO trabalhadorFiltro, String jpql) {
        List<String> filtro = new ArrayList<>();

        if (trabalhadorFiltro.getNome() != null && !trabalhadorFiltro.getNome().isEmpty()) {
            filtro.add("lower(t.nome) LIKE :nome");
        }
        if (trabalhadorFiltro.getCpf() != null && !trabalhadorFiltro.getCpf().isEmpty()) {
            filtro.add("t.cpf = :cpf");
        }
        if (trabalhadorFiltro.getId() != null) {
            filtro.add("t.id = :id");
        }
        if (trabalhadorFiltro.getEmpresa() != null && trabalhadorFiltro.getEmpresa().getId() != null) {
            filtro.add("t.empresa.id = :idEmpresa");
        }
        if (trabalhadorFiltro.getFuncao() != null && !trabalhadorFiltro.getFuncao().isEmpty()) {
            filtro.add("lower(t.funcao) LIKE :funcao");
        }
        if (trabalhadorFiltro.getSetor() != null && !trabalhadorFiltro.getSetor().isEmpty()) {
            filtro.add("lower(t.setor) LIKE :setor");
        }
        if (trabalhadorFiltro.getCursos() != null && !trabalhadorFiltro.getCursos().isEmpty()) {
            filtro.add("cursos IN :cursos");
        }

        String condicoes = " WHERE ";
        for (String condicao : filtro) {
            condicoes += condicao + " AND ";
        }
        condicoes = condicoes.substring(0, condicoes.length() - 5);
        if (filtro.size() > 0) {
            jpql += condicoes;
        }
        jpql += " GROUP BY t.id ";
        String orderBy = setOrderByFiltroTrabalhador(jpql, trabalhadorFiltro);
        jpql += orderBy;
        return entityManager.createQuery(jpql, Trabalhador.class);
    }

    private String setOrderByFiltroTrabalhador(String jpql, TrabalhadorFiltroDTO trabalhadorFiltro) {
        String orderBy = " ORDER BY t.id ";
        String nomeOrderBy = trabalhadorFiltro.getOrderBy();
        if (nomeOrderBy == null) return orderBy;
        return " ORDER BY " + nomeOrderBy;
    }

    private Query setQueryParametersFiltroTrabalhador(TrabalhadorFiltroDTO trabalhadorFiltro, TypedQuery<Trabalhador> query) {
        if (trabalhadorFiltro.getNome() != null && !trabalhadorFiltro.getNome().isEmpty()) {
            query.setParameter("nome", "%" + trabalhadorFiltro.getNome().toLowerCase() + "%");
        }
        if (trabalhadorFiltro.getCpf() != null && !trabalhadorFiltro.getCpf().isEmpty()) {
            query.setParameter("cpf", trabalhadorFiltro.getCpf().replaceAll("\\D", ""));
        }
        if (trabalhadorFiltro.getId() != null) {
            query.setParameter("id", trabalhadorFiltro.getId());
        }
        if (trabalhadorFiltro.getEmpresa() != null && trabalhadorFiltro.getEmpresa().getId() != null) {
            query.setParameter("idEmpresa", trabalhadorFiltro.getEmpresa().getId());
        }
        if (trabalhadorFiltro.getFuncao() != null && !trabalhadorFiltro.getFuncao().isEmpty()) {
            query.setParameter("funcao", "%" + trabalhadorFiltro.getFuncao().toLowerCase() + "%");
        }
        if (trabalhadorFiltro.getSetor() != null && !trabalhadorFiltro.getSetor().isEmpty()) {
            query.setParameter("setor", "%" + trabalhadorFiltro.getSetor().toLowerCase() + "%");
        }
        if (trabalhadorFiltro.getCursos() != null && !trabalhadorFiltro.getCursos().isEmpty()) {
            List<Curso> cursos = new ArrayList<>();
            ModelMapper modelMapper = new ModelMapper();
            cursos.stream().map(curso -> modelMapper.map(curso, Curso.class)).collect(Collectors.toList());
            query.setParameter("cursos", trabalhadorFiltro.getCursos());
        }
        return query;
    }

    public List<Trabalhador> getTrabalhadoresByAvaliacaoId(Long avaliacaoId) {
        String jpql = "SELECT t FROM Trabalhador t " +
                "JOIN t.cursos cursos " +
                "JOIN cursos.modulos modulos " +
                "JOIN modulos.avaliacao avaliacoes " +
                "LEFT JOIN avaliacoes.notas notas " +
                "WHERE avaliacoes.id = :avaliacaoId " +
                "GROUP BY t.id " +
                "ORDER BY t.id";
        return entityManager.createQuery(jpql, Trabalhador.class)
                .setParameter("avaliacaoId", avaliacaoId)
                .getResultList();
    }

    public List<Trabalhador> getTodosTrabalhadores() {
        String jpql = "SELECT t FROM Trabalhador t ";
        return entityManager.createQuery(jpql, Trabalhador.class)
                .getResultList();
    }

    public List<Trabalhador> getTrabalhadoresByEmpresaId(Long empresaId) {
        String jpql = "SELECT t FROM Trabalhador t " +
                "WHERE t.empresa.id = :empresaId";
        return entityManager.createQuery(jpql, Trabalhador.class)
                .setParameter("empresaId", empresaId)
                .getResultList();
    }
}
