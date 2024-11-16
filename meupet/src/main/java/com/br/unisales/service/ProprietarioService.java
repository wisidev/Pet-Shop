package com.br.unisales.service;

import java.util.ArrayList;
import java.util.List;

import com.br.unisales.configuration.ConfigurationManager;
import com.br.unisales.table.Pet;
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

    public List<Proprietario> listarPorNome(String nome) {
        try {
            EntityManager em = config.getEntityManager();
            TypedQuery<Proprietario> query = em.createQuery("FROM Proprietario WHERE nome LIKE :nome ORDER BY nome", Proprietario.class);
            query.setParameter("nome", "%" + nome + "%");  // Usando LIKE para busca parcial
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro ao listar proprietários por nome: " + e.getMessage());
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

    public Proprietario buscarPorCpf(String cpf) {
        try {
            EntityManager em = config.getEntityManager();
            TypedQuery<Proprietario> query = em.createQuery("FROM Proprietario WHERE cpf = :cpf", Proprietario.class);
            query.setParameter("cpf", cpf);
            List<Proprietario> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } catch (Exception e) {
            System.err.println("Erro ao buscar proprietário pelo CPF: " + e.getMessage());
            return null;
        }
    }
    
    public Proprietario buscarPorEmail(String email) {
        try {
            EntityManager em = config.getEntityManager();
            TypedQuery<Proprietario> query = em.createQuery("FROM Proprietario WHERE email = :email", Proprietario.class);
            query.setParameter("email", email);
            List<Proprietario> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } catch (Exception e) {
            System.err.println("Erro ao buscar proprietário pelo e-mail: " + e.getMessage());
            return null;
        }
    }
    
    public Proprietario buscarPorCelular(String celular) {
        try {
            EntityManager em = config.getEntityManager();
            TypedQuery<Proprietario> query = em.createQuery("FROM Proprietario WHERE celular = :celular", Proprietario.class);
            query.setParameter("celular", celular);
            List<Proprietario> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } catch (Exception e) {
            System.err.println("Erro ao buscar proprietário pelo celular: " + e.getMessage());
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

     // Método para buscar os pets associados a um proprietário
    public List<Pet> buscarPetsPorProprietario(int idProprietario) {
        try {
            EntityManager em = config.getEntityManager();
            TypedQuery<Pet> query = em.createQuery("FROM Pet WHERE proprietario.id = :idProprietario", Pet.class);
            query.setParameter("idProprietario", idProprietario);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro ao buscar pets por proprietário: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Método para excluir um pet
    public void excluirPet(int idPet) {
        try {
            EntityManager em = config.getEntityManager();
            em.getTransaction().begin();

            Pet pet = em.find(Pet.class, idPet);
            if (pet != null) {
                em.remove(pet);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Erro ao excluir pet: " + e.getMessage());
        }
    }

    // Método para verificar se o proprietário tem pets
    public boolean temPets(int idProprietario) {
        List<Pet> pets = buscarPetsPorProprietario(idProprietario);
        return !pets.isEmpty();
    }
}
