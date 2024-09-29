package com.br.unisales.service;

import java.util.ArrayList;
import java.util.List;

import com.br.unisales.configuration.ConfigurationManager;
import com.br.unisales.table.Usuario;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class UsuarioService {

    private final ConfigurationManager config;

    public UsuarioService() {
        this.config = new ConfigurationManager();
    }

    public List<Usuario> listar() {
        try {
            EntityManager em = config.getEntityManager();
            TypedQuery<Usuario> query = em.createQuery("FROM Usuario ORDER BY nome", Usuario.class);
            return query.getResultList();
        } catch (Exception e) {          
            System.err.println("Erro ao listar usuários: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Usuario buscarPorId(Integer id) {
        try {
            EntityManager em = config.getEntityManager();
            return em.find(Usuario.class, id);
        } catch (Exception e) {
            System.err.println("Erro ao buscar usuário por ID: " + e.getMessage());
            return null;
        }
    }

    public Usuario salvar(Integer id, String nome, String sexo, String email, String senha, String grupo) {
        try {
            EntityManager em = config.getEntityManager();
            em.getTransaction().begin();

            Usuario usuario;
            if (id == null) {
                usuario = Usuario.builder()
                                 .nome(nome)
                                 .sexo(sexo)
                                 .email(email)
                                 .senha(senha)
                                 .grupo(grupo)
                                 .build();
                em.persist(usuario);
            } else {
                usuario = em.find(Usuario.class, id);
                if (usuario != null) {
                    usuario.setNome(nome);
                    usuario.setSexo(sexo);
                    usuario.setEmail(email);
                    usuario.setSenha(senha);
                    usuario.setGrupo(grupo);
                }
            }

            em.getTransaction().commit();
            return usuario;
        } catch (Exception e) {
            System.err.println("Erro ao salvar/atualizar usuário: " + e.getMessage());
            return null;
        }
    }

    public String excluir(Integer id) {
        try {
            EntityManager em = config.getEntityManager();
            em.getTransaction().begin();

            Usuario usuario = em.find(Usuario.class, id);
            if (usuario != null) {
                em.remove(usuario);
                em.getTransaction().commit();
                return "ok";
            }

            return "Usuário não encontrado.";
        } catch (Exception e) {
            System.err.println("Erro ao excluir usuário: " + e.getMessage());
            return "Erro ao excluir usuário.";
        }
    }
}
