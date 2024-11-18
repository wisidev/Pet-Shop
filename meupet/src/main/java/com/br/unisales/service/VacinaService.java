package com.br.unisales.service;

import com.br.unisales.configuration.ConfigurationManager;
import com.br.unisales.table.Vacina;

import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

public class VacinaService {
    private final ConfigurationManager config;

    public VacinaService() {
        this.config = new ConfigurationManager();
    }

    public List<Vacina> buscarPorFiltro(Integer codigoAnimal, String nomeVacina) {
        String queryStr = "FROM Vacina WHERE 1=1";
        if (codigoAnimal != null) queryStr += " AND codigoAnimal = :codigoAnimal";
        if (nomeVacina != null && !nomeVacina.isEmpty()) queryStr += " AND nome LIKE :nome";

        TypedQuery<Vacina> query = config.getEntityManager().createQuery(queryStr, Vacina.class);
        if (codigoAnimal != null) query.setParameter("codigoAnimal", codigoAnimal);
        if (nomeVacina != null && !nomeVacina.isEmpty()) query.setParameter("nome", "%" + nomeVacina + "%");

        return query.getResultList();
    }

    public void salvar(Integer id, Integer codigoAnimal, String nome, String descricao, LocalDate dataAplicacao) {
        Vacina vacina = Vacina.builder().id(id).codigoAnimal(codigoAnimal).nome(nome).descricao(descricao).dataAplicacao(dataAplicacao).build();
        var em = config.getEntityManager();
        em.getTransaction().begin();
        if (id == null) em.persist(vacina);
        else em.merge(vacina);
        em.getTransaction().commit();
    }
}