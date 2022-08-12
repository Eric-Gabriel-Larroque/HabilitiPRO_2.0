package com.senai.habilitpro.model.repository;

import com.senai.habilitpro.model.dto.RoleDTO;
import com.senai.habilitpro.model.dto.UsuarioFiltroDTO;
import com.senai.habilitpro.model.dto.request.LoginRequestApiDTO;
import com.senai.habilitpro.model.entity.Usuario;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioRepository extends GenericRepository {
    public List<Usuario> findUsuariosSemEmpresa() {
        String jpql = "SELECT u FROM Usuario u " +
                "WHERE u.empresa IS NULL";
        return entityManager.createQuery(jpql, Usuario.class).getResultList();
    }

    public List<Usuario> findByCpf(String cpf) {
        String jpql = "SELECT u FROM Usuario u WHERE u.cpf = :cpf";
        return entityManager.createQuery(jpql, Usuario.class).setParameter("cpf", cpf).getResultList();
    }

    public List<Usuario> findByEmail(String email) {
        String jpql = "SELECT u FROM Usuario u WHERE u.email = :email";
        return entityManager.createQuery(jpql, Usuario.class).setParameter("email", email).getResultList();
    }

    public List<Usuario> filtrar(UsuarioFiltroDTO usuarioFiltro) {
        String jpql = "SELECT u FROM Usuario u LEFT JOIN u.roles roles ";
        TypedQuery<Usuario> query = setQueryFiltroUsuario(usuarioFiltro, jpql);
        return setQueryParametersFiltroUsuario(usuarioFiltro, query).getResultList();
    }

    private TypedQuery<Usuario> setQueryFiltroUsuario(UsuarioFiltroDTO usuarioFiltro, String jpql) {
        List<String> filtro = new ArrayList<>();
        if (usuarioFiltro.getNome() != null && !usuarioFiltro.getNome().isEmpty()) {
            filtro.add("lower(u.nome) LIKE :nome");
        }
        if (usuarioFiltro.getCpf() != null && !usuarioFiltro.getCpf().isEmpty()) {
            filtro.add("u.cpf = :cpf");
        }
        if (usuarioFiltro.getId() != null) {
            filtro.add("u.id = :id");
        }
        if (usuarioFiltro.getEmpresa() != null && usuarioFiltro.getEmpresa().getId() != null) {
            filtro.add("u.empresa.id = :idEmpresa");
        }
        if (usuarioFiltro.getRoles().size() > 0) {
            filtro.add("roles.id IN :idRoles");
        }
        String condicoes = " WHERE ";
        for (String condicao : filtro) {
            condicoes += condicao + " AND ";
        }
        condicoes = condicoes.substring(0, condicoes.length() - 5);
        if (filtro.size() > 0) {
            jpql += condicoes;
        }
        jpql += " GROUP BY u.id ORDER BY u.id";
        return entityManager.createQuery(jpql, Usuario.class);
    }

    private Query setQueryParametersFiltroUsuario(UsuarioFiltroDTO usuarioFiltro, TypedQuery<Usuario> query) {
        if (usuarioFiltro.getNome() != null && !usuarioFiltro.getNome().isEmpty()) {
            query.setParameter("nome", "%" + usuarioFiltro.getNome().toLowerCase() + "%");
        }
        if (usuarioFiltro.getCpf() != null && !usuarioFiltro.getCpf().isEmpty()) {
            query.setParameter("cpf", usuarioFiltro.getCpf().replaceAll("[^0-9]", ""));
        }
        if (usuarioFiltro.getId() != null) {
            query.setParameter("id", usuarioFiltro.getId());
        }
        if (usuarioFiltro.getEmpresa() != null && usuarioFiltro.getEmpresa().getId() != null) {
            query.setParameter("idEmpresa", usuarioFiltro.getEmpresa().getId());
        }
        if (usuarioFiltro.getRoles().size() > 0) {
            List<Long> roles = new ArrayList<>();
            for (RoleDTO roleDTO : usuarioFiltro.getRoles()) {
                roles.add(roleDTO.getId());
            }
            query.setParameter("idRoles", roles);
        }
        return query;
    }

    public Usuario getUsuarioByEmail(String email) {
        return entityManager.createQuery(
                        "SELECT u " +
                                "FROM Usuario u " +
                                "WHERE u.email = :email", Usuario.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public Usuario consultarRolesPorIdUsuario(Long idUsuario) {
        if (idUsuario == null) {
            return null;
        }

        String jpql = "SELECT u FROM Usuario u " +
                "LEFT JOIN FETCH u.roles roles " +
                "WHERE u.id = :idUsuario";

        return entityManager.createQuery(jpql, Usuario.class)
                .setParameter("idUsuario", idUsuario)
                .getSingleResult();
    }

    public List<Usuario> obterUsuarios() {
        String jpql = "SELECT u FROM Usuario u";
        return entityManager.createQuery(jpql,Usuario.class).getResultList();
    }

    public Optional<Usuario> obterUsuarioPorId(long id) {
        Usuario usuario = entityManager.find(Usuario.class,id);
        return usuario != null ? Optional.of(usuario) : Optional.empty();
    }
}
