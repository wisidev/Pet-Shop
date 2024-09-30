package com.br.unisales.service;

import com.br.unisales.configuration.ConfigurationManager;
import com.br.unisales.table.HistoricoEvolucao;

import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.List;

public class HistoricoEvolucaoService {

    private final ConfigurationManager config;

    public HistoricoEvolucaoService() {
        this.config = new ConfigurationManager();
    }

    public List<HistoricoEvolucao> listar() {
        try {
            TypedQuery<HistoricoEvolucao> query = this.config.getEntityManager().createQuery("FROM HistoricoEvolucao ORDER BY dataHora", HistoricoEvolucao.class);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro ao listar históricos: " + e.getMessage());
            return List.of();
        }
    }

    public HistoricoEvolucao buscarPorId(Integer id) {
        try {
            TypedQuery<HistoricoEvolucao> query = this.config.getEntityManager().createQuery("FROM HistoricoEvolucao WHERE id = :id", HistoricoEvolucao.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (Exception e) {
            System.err.println("Erro ao buscar histórico: " + e.getMessage());
            return null;
        }
    }

    public HistoricoEvolucao salvar(Integer idPet, Double peso, Double altura, LocalDateTime dataHora) {
        HistoricoEvolucao historico = HistoricoEvolucao.builder()
                .idPet(idPet)
                .peso(peso)
                .altura(altura)
                .dataHora(dataHora)
                .build();

        this.config.getEntityManager().getTransaction().begin();
        this.config.getEntityManager().persist(historico);
        this.config.getEntityManager().getTransaction().commit();

        return historico;
    }
}