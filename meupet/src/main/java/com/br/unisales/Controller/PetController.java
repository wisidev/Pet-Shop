package com.br.unisales.controller;
import com.br.unisales.service.PetService;
import com.br.unisales.table.Pet;

import java.util.List;
import java.util.Scanner;

public class PetController {
    private final PetService petService = new PetService();
    private final Scanner scanner;

    public PetController(Scanner scanner) {
        this.scanner = scanner;
    }

    public void gerenciarPets() {
        while (true) {
            switch (mostrarMenuPets()) {
                case 1:
                    cadastrarPet();
                    break;
                case 2:
                    listarPets();
                    break;
                case 3:
                    excluirPet();
                    break;
                case 4:
                    alterarPet();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private int mostrarMenuPets() {
        System.out.println("\nMenu Pets:");
        System.out.println("1. Cadastrar dados do Pet");
        System.out.println("2. Listar dados do Pet");
        System.out.println("3. Excluir dados do Pet");
        System.out.println("4. Alterar dados do Pet");
        System.out.println("5. Voltar ao menu principal");
        System.out.print("Escolha uma opção: ");
        return lerInteiro();
    }

    private void cadastrarPet() {
        System.out.print("Nome do Animal: ");
        String nome = scanner.nextLine();

        System.out.print("Proprietário (ID): ");
        int idProprietario = lerInteiro();

        System.out.print("Idade: ");
        int idade = lerInteiro();

        System.out.print("Sexo (M/F): ");
        String sexo = scanner.nextLine();

        System.out.print("Espécie: ");
        String especie = scanner.nextLine();

        System.out.print("Raça: ");
        String raca = scanner.nextLine();

        petService.salvar(null, nome, idProprietario, idade, sexo, especie, raca);
        System.out.println("Pet cadastrado com sucesso!");
    }

    private void listarPets() {
        List<Pet> lista = petService.listar();
        if (lista.isEmpty()) {
            System.out.println("Não há pets cadastrados.");
        } else {
            lista.forEach(pet -> System.out.println("Id: " + pet.getId() + " - Nome: " + pet.getNome() +
                    " - Proprietário (ID): " + pet.getIdProprietario() + " - Idade: " + pet.getIdade() +
                    " - Sexo: " + pet.getSexo() + " - Espécie: " + pet.getEspecie() + " - Raça: " + pet.getRaca()));
        }
    }

    private void excluirPet() {
        System.out.print("Digite o ID do pet a ser excluído: ");
        int idExcluir = lerInteiro();
        String resultadoExcluir = petService.excluir(idExcluir);
        if ("ok".equals(resultadoExcluir)) {
            System.out.println("Pet excluído com sucesso!");
        } else {
            System.out.println("Erro ao excluir o pet. Verifique se o ID está correto.");
        }
    }

    private void alterarPet() {
        System.out.print("Digite o ID do pet a ser alterado: ");
        int idAlterar = lerInteiro();

        Pet pet = petService.buscarPorId(idAlterar);
        if (pet == null) {
            System.out.println("Pet não encontrado. Verifique se o ID está correto.");
            return;
        }

        System.out.print("Nome do Animal (atual: " + pet.getNome() + "): ");
        String novoNome = scanner.nextLine();

        System.out.print("Proprietário (ID) (atual: " + pet.getIdProprietario() + "): ");
        Integer novoIdProprietario = lerInteiro();

        System.out.print("Idade (atual: " + pet.getIdade() + "): ");
        int novaIdade = lerInteiro();

        System.out.print("Sexo (M/F) (atual: " + pet.getSexo() + "): ");
        String novoSexo = scanner.nextLine();

        System.out.print("Espécie (atual: " + pet.getEspecie() + "): ");
        String novaEspecie = scanner.nextLine();

        System.out.print("Raça (atual: " + pet.getRaca() + "): ");
        String novaRaca = scanner.nextLine();

        petService.salvar(idAlterar, novoNome, novoIdProprietario, novaIdade, novoSexo, novaEspecie, novaRaca);
        System.out.println("Pet alterado com sucesso!");
    }

    private int lerInteiro() {
        while (!scanner.hasNextInt()) {
            System.out.print("Entrada inválida. Digite um número inteiro: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();  // Consumir a nova linha restante após a leitura do número
        return valor;
    }
}
