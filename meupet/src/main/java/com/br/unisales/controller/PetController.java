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
        // Solicitar dados do pet
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
    
        // Verificar se algum campo obrigatório está vazio
        if (nome.isEmpty() || sexo.isEmpty() || especie.isEmpty() || raca.isEmpty()) {
            System.out.println("Erro: Dados obrigatórios não foram preenchidos. Todos os campos devem ser preenchidos.");
            return; // Retorna para o menu de gerenciamento de pets
        }
    
        // Chamar o método de salvar para registrar o pet no banco de dados
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
        // Solicitar o ID do pet a ser alterado
        System.out.print("Digite o ID do pet a ser alterado: ");
        int idAlterar = lerInteiro();
    
        // Buscar o pet no banco de dados
        Pet pet = petService.buscarPorId(idAlterar);
        
        // Verificar se o pet existe
        if (pet == null) {
            System.out.println("Pet não encontrado. Verifique se o ID está correto.");
            return;
        }
    
        // Exibir os dados atuais do pet
        System.out.println("\nDados atuais do Pet:");
        System.out.println("Nome: " + pet.getNome());
        System.out.println("Proprietário (ID): " + pet.getIdProprietario());
        System.out.println("Idade: " + pet.getIdade());
        System.out.println("Sexo: " + pet.getSexo());
        System.out.println("Espécie: " + pet.getEspecie());
        System.out.println("Raça: " + pet.getRaca());
    
        // Solicitar alterações dos dados
        System.out.print("Nome do Animal (atual: " + pet.getNome() + "): ");
        String novoNome = scanner.nextLine();
        if (novoNome.isEmpty()) novoNome = pet.getNome();  // Se não informar, manter o valor atual
    
        System.out.print("Proprietário (ID) (atual: " + pet.getIdProprietario() + "): ");
        Integer novoIdProprietario = lerInteiro();  // Podemos deixar a ID do proprietário como está, se desejado
    
        System.out.print("Idade (atual: " + pet.getIdade() + "): ");
        int novaIdade = lerInteiro();
        
        System.out.print("Sexo (M/F) (atual: " + pet.getSexo() + "): ");
        String novoSexo = scanner.nextLine();
        if (novoSexo.isEmpty()) novoSexo = pet.getSexo();  // Se não informar, manter o valor atual
    
        System.out.print("Espécie (atual: " + pet.getEspecie() + "): ");
        String novaEspecie = scanner.nextLine();
        if (novaEspecie.isEmpty()) novaEspecie = pet.getEspecie();  // Se não informar, manter o valor atual
    
        System.out.print("Raça (atual: " + pet.getRaca() + "): ");
        String novaRaca = scanner.nextLine();
        if (novaRaca.isEmpty()) novaRaca = pet.getRaca();  // Se não informar, manter o valor atual
    
        // Verificar se algum campo obrigatório foi deixado em branco
        if (novoNome.isEmpty() || novoSexo.isEmpty() || novaEspecie.isEmpty() || novaRaca.isEmpty()) {
            System.out.println("Erro: Dados obrigatórios não foram preenchidos. Todos os campos devem ser preenchidos.");
            return; // Retorna sem salvar as alterações
        }
    
        // Atualizar pet no banco de dados
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