package com.senai.habilitpro.model.repository;

import com.senai.habilitpro.model.entity.Role;

import java.util.List;

public class RoleRepository extends GenericRepository {
    public List<Role> findAll() {
        String jpql = "SELECT r FROM Role r";
        return entityManager.createQuery(jpql, Role.class).getResultList();
    }
}
