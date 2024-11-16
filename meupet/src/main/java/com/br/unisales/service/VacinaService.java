package com.br.unisales.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.br.unisales.configuration.ConfigurationManager;
import com.br.unisales.table.Vacina;

import jakarta.persistence.TypedQuery;

public class VacinaService {

    private final ConfigurationManager config;

    public VacinaService() {
        this.config = new ConfigurationManager();
    }

    public List<Vacina> listar() {
        try {
            TypedQuery<Vacina> query = this.config.getEntityManager().createQuery("FROM Vacina ORDER BY nome", Vacina.class);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Vacina buscarPorId(Integer id) {
        try {
            TypedQuery<Vacina> query = this.config.getEntityManager().createQuery("FROM Vacina WHERE id = :id", Vacina.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            return null;
        }
    }

    public Vacina salvar(Integer id, Integer codigoAnimal, String nome, String descricao, LocalDate dataAplicacao) {
        Vacina vacina = Vacina.builder()
                              .id(id)
                              .codigoAnimal(codigoAnimal)
                              .nome(nome)
                              .descricao(descricao)
                              .dataAplicacao(dataAplicacao)
                              .build();
        this.config.getEntityManager().getTransaction().begin();
        if(id == null) {
            this.config.getEntityManager().persist(vacina);
        } else {
            this.config.getEntityManager().merge(vacina);
        }

        this.config.getEntityManager().getTransaction().commit();
        return vacina;
    }

    public String excluir(Integer id) {
        if(id != null) {
            Vacina vacina = this.buscarPorId(id);
            if (vacina != null) {
                this.config.getEntityManager().getTransaction().begin();
                this.config.getEntityManager().remove(vacina);
                this.config.getEntityManager().getTransaction().commit();
                return "ok";
            }
        }
        
        return "erro";
    }

}