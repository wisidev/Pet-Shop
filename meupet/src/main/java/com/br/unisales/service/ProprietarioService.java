package com.br.unisales.service;

import java.util.ArrayList;
import java.util.List;

import com.br.unisales.configuration.ConfigurationManager;
import com.br.unisales.table.Proprietario;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class ProprietarioService {

    private final ConfigurationManager config;

    public ProprietarioService() {
        this.config = new ConfigurationManager();
    }

    public List<Proprietario> listar() {
        try {
            EntityManager em = config.getEntityManager();
            TypedQuery<Proprietario> query = em.createQuery("FROM Proprietario ORDER BY nome", Proprietario.class);
            return query.getResultList();
        } catch (Exception e) {          
            System.err.println("Erro ao listar proprietários: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Proprietario buscarPorId(Integer id) {
        try {
            EntityManager em = config.getEntityManager();
            return em.find(Proprietario.class, id);
        } catch (Exception e) {
            System.err.println("Erro ao buscar proprietário por ID: " + e.getMessage());
            return null;
        }
    }

    public Proprietario salvar(Integer id, String nome, String sexo, String cpf, String email, String celular) {
        try {
            EntityManager em = config.getEntityManager();
            em.getTransaction().begin();

            Proprietario proprietario;
            if (id == null) {
                proprietario = Proprietario.builder()
                                          .nome(nome)
                                          .sexo(sexo)
                                          .cpf(cpf)
                                          .email(email)
                                          .celular(celular)
                                          .build();
                em.persist(proprietario);
            } else {
                proprietario = em.find(Proprietario.class, id);
                if (proprietario != null) {
                    proprietario.setNome(nome);
                    proprietario.setSexo(sexo);
                    proprietario.setCpf(cpf);
                    proprietario.setEmail(email);
                    proprietario.setCelular(celular);
                }
            }

            em.getTransaction().commit();
            return proprietario;
        } catch (Exception e) {
            System.err.println("Erro ao salvar/atualizar proprietário: " + e.getMessage());
            return null;
        }
    }

    public String excluir(Integer id) {
        try {
            EntityManager em = config.getEntityManager();
            em.getTransaction().begin();

            Proprietario proprietario = em.find(Proprietario.class, id);
            if (proprietario != null) {
                em.remove(proprietario);
                em.getTransaction().commit();
                return "ok";
            }

            return "Proprietário não encontrado.";
        } catch (Exception e) {
            System.err.println("Erro ao excluir proprietário: " + e.getMessage());
            return "Erro ao excluir proprietário.";
        }
    }
}
