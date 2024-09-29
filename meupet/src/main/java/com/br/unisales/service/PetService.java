package com.br.unisales.service;

import java.util.ArrayList;
import java.util.List;

import com.br.unisales.configuration.ConfigurationManager;
import com.br.unisales.table.Pet;

import jakarta.persistence.TypedQuery;

public class PetService {

    private final ConfigurationManager config;

    public PetService() {
        this.config = new ConfigurationManager();
    }

    public List<Pet> listar() {
        try {
            TypedQuery<Pet> query = this.config.getEntityManager().createQuery("FROM Pet ORDER BY nome", Pet.class);
            return query.getResultList();
        } catch (Exception e) {          
            System.err.println("Erro: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Pet buscarPorId(Integer id) {
        try {
            TypedQuery<Pet> query = this.config.getEntityManager().createQuery("FROM Pet WHERE id = :id", Pet.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
            return null;
        }
    }

    public Pet salvar(Integer id, String nome, int idProprietario, Integer idade, String sexo, String especie, String raca) {
        if(id!=null) {
            Pet petOld = this.buscarPorId(id);
            idProprietario = petOld.getIdProprietario();
        }
        
        Pet pet = Pet.builder()
                     .id(id)
                     .nome(nome)
                     .idProprietario(idProprietario)
                     .idade(idade)
                     .sexo(sexo)
                     .especie(especie)
                     .raca(raca)
                     .build();
        this.config.getEntityManager().getTransaction().begin();
        if(id == null) {
            this.config.getEntityManager().persist(pet);
        } else {
            this.config.getEntityManager().merge(pet);
        }

        this.config.getEntityManager().getTransaction().commit();
        return pet;
    }

    public String excluir(Integer id) {
        if(id != null) {
            Pet pet = this.buscarPorId(id);
            if (pet != null) {
                this.config.getEntityManager().getTransaction().begin();
                this.config.getEntityManager().remove(pet);
                this.config.getEntityManager().getTransaction().commit();
                return "ok";
            }
        }
        
        return "erro";
    }

}
