package com.senai.habilitpro.model.repository;

import com.senai.habilitpro.enumerator.AvaliacaoStatusEnum;
import com.senai.habilitpro.model.entity.Avaliacao;

import java.util.List;

public class AvaliacaoRepository extends GenericRepository {
    public List<Avaliacao> getAvaliacoesByStatus(AvaliacaoStatusEnum statusSelecionado) {
        String jpql = "SELECT a FROM Avaliacao a WHERE a.status = :statusSelecionado ";

        return entityManager.createQuery(jpql, Avaliacao.class)
                .setParameter("statusSelecionado", statusSelecionado)
                .getResultList();
    }

    public List<Avaliacao> getTodasAvaliacoes() {
        String jpql = "SELECT a FROM Avaliacao a";

        return entityManager.createQuery(jpql, Avaliacao.class)
                .getResultList();
    }
}
