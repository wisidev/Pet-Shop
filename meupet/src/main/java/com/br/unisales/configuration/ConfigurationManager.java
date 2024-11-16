package com.br.unisales.configuration;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ConfigurationManager {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    public ConfigurationManager() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("meupet_jpa");
        this.entityManager = this.entityManagerFactory.createEntityManager();
    }

    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    public void closeManager() {
        this.entityManager.close();
        this.entityManagerFactory.close();
    }
}
