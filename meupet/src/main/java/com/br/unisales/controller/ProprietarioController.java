package com.br.unisales.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.br.unisales.service.ProprietarioService;
import com.br.unisales.table.Pet;
import com.br.unisales.table.Proprietario;

public class ProprietarioController {
    
    private final Scanner scanner;
    private final ProprietarioService proprietarioService;

    public ProprietarioController(Scanner scanner) {
        this.scanner = scanner;
        this.proprietarioService = new ProprietarioService(); // Inicialize o serviço aqui
    }

    public void gerenciarProprietarios() {
        while (true) {
            switch (mostrarMenuProprietarios()) {
                case 1:
                    cadastrarProprietario();
                    break;
                case 2:
                    listarProprietarios();
                    break;
                case 3:
                    excluirProprietario();
                    break;
                case 4:
                    alterarProprietario();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private int mostrarMenuProprietarios() {
        System.out.println("\nMenu Proprietários:");
        System.out.println("1. Cadastrar dados do Proprietário");
        System.out.println("2. Listar dados dos Proprietários");
        System.out.println("3. Excluir dados do Proprietário");
        System.out.println("4. Alterar dados do Proprietário");
        System.out.println("5. Voltar ao menu principal");
        System.out.print("Escolha uma opção: ");
        return lerInteiro();
    }

    private void cadastrarProprietario() {
        // Fluxo Principal: Solicitar as informações obrigatórias
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
    
        System.out.print("Sexo (M/F): ");
        String sexo = scanner.nextLine();
    
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
    
        System.out.print("E-mail: ");
        String email = scanner.nextLine();
    
        System.out.print("Celular: ");
        String celular = scanner.nextLine();
    
        // Fluxo Secundário 1: Verificar se todos os campos obrigatórios foram preenchidos
        if (nome.isEmpty() || sexo.isEmpty() || cpf.isEmpty() || email.isEmpty() || celular.isEmpty()) {
            System.out.println("Erro: Todos os campos obrigatórios (nome, sexo, CPF, e-mail, celular) devem ser preenchidos.");
            return;
        }
    
        // Fluxo Secundário 2: Verificar se o CPF já está cadastrado no banco de dados
        if (proprietarioService.buscarPorCpf(cpf) != null) {
            System.out.println("Erro: O CPF já está cadastrado no sistema.");
            return;
        }
    
        // Fluxo Secundário 3: Verificar se o e-mail já está cadastrado no banco de dados
        if (proprietarioService.buscarPorEmail(email) != null) {
            System.out.println("Erro: O e-mail já está cadastrado no sistema.");
            return;
        }
    
        // Fluxo Secundário 4: Verificar se o celular já está cadastrado no banco de dados
        if (proprietarioService.buscarPorCelular(celular) != null) {
            System.out.println("Erro: O número de celular já está cadastrado no sistema.");
            return;
        }
    
        // Fluxo Principal: Cadastro do Proprietário
        Proprietario novoProprietario = proprietarioService.salvar(null, nome, sexo, cpf, email, celular);
        if (novoProprietario != null) {
            System.out.println("Proprietário cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar o proprietário.");
        }
    }    

    private void listarProprietarios() {
    System.out.println("\nBusque os proprietários com os parâmetros abaixo:");

    // Solicitar parâmetros de busca
    System.out.print("Código do Proprietário (opcional, pressione Enter para pular): ");
    Integer idBusca = lerInteiroOuNull();

    System.out.print("Nome do Proprietário (opcional, pressione Enter para pular): ");
    String nomeBusca = scanner.nextLine();

    System.out.print("E-mail do Proprietário (opcional, pressione Enter para pular): ");
    String emailBusca = scanner.nextLine();

    // Fluxo principal: buscar os proprietários com os parâmetros fornecidos
    List<Proprietario> lista = new ArrayList<>();

    if (idBusca != null) {
        Proprietario proprietario = proprietarioService.buscarPorId(idBusca);
        if (proprietario != null) {
            lista.add(proprietario);
        }
    } else {
        // Buscar por nome ou e-mail, se informados
        if (!nomeBusca.isEmpty()) {
            lista.addAll(proprietarioService.listarPorNome(nomeBusca));
        }
        if (!emailBusca.isEmpty()) {
            Proprietario proprietarioEmail = proprietarioService.buscarPorEmail(emailBusca);
            if (proprietarioEmail != null) {
                lista.add(proprietarioEmail);
            }
        }
    }

    // Fluxo Secundário 1: Caso não encontre nenhum proprietário
    if (lista.isEmpty()) {
        System.out.println("Nenhum proprietário encontrado com os parâmetros fornecidos.");
    } else {
        // Exibir os dados dos proprietários encontrados
        lista.forEach(proprietario -> System.out.println("Id: " + proprietario.getId() + " - Nome: " + proprietario.getNome() +
                " - Sexo: " + proprietario.getSexo() + " - CPF: " + proprietario.getCpf() + " - E-mail: " + proprietario.getEmail() +
                " - Celular: " + proprietario.getCelular()));
    }
}

// Método auxiliar para ler um inteiro ou retornar null
private Integer lerInteiroOuNull() {
    String input = scanner.nextLine();
    if (input.isEmpty()) {
        return null;
    }
    try {
        return Integer.parseInt(input);
    } catch (NumberFormatException e) {
        System.out.println("Entrada inválida, esperando um número inteiro.");
        return null;
    }
}

    private void excluirProprietario() {
    // Fluxo Principal: Solicitar o ID do proprietário
    System.out.print("Digite o ID do proprietário a ser excluído: ");
    int idExcluir = lerInteiro();

    // Verificar se o proprietário existe (Fluxo Secundário 1)
    Proprietario proprietario = proprietarioService.buscarPorId(idExcluir);
    if (proprietario == null) {
        // Proprietário não encontrado
        System.out.println("Erro: Proprietário não encontrado. Verifique se o ID está correto.");
        return;
    }

    // Fluxo Secundário 2: Verificar se o proprietário tem pets cadastrados
    List<Pet> pets = proprietarioService.buscarPetsPorProprietario(idExcluir);
    if (!pets.isEmpty()) {
        System.out.println("Este proprietário tem " + pets.size() + " pet(s) cadastrado(s).");

        // Confirmar com o usuário se deseja excluir também os pets
        System.out.println("Deseja excluir os pets associados a este proprietário?");
        System.out.println("1. Excluir pets e proprietário");
        System.out.println("2. Cancelar exclusão");
        int opcao = lerInteiro();

        switch (opcao) {
            case 1:
                // Excluir pets primeiro
                for (Pet pet : pets) {
                    proprietarioService.excluirPet(pet.getId());
                }
                // Depois excluir o proprietário
                excluirProprietarioDoBanco(idExcluir);
                break;
            case 2:
                System.out.println("Exclusão cancelada. Proprietário e pets não foram excluídos.");
                break;
            default:
                System.out.println("Opção inválida. Exclusão cancelada.");
        }
    } else {
        // Não tem pets, pode excluir o proprietário diretamente
        excluirProprietarioDoBanco(idExcluir);
    }
}

private void excluirProprietarioDoBanco(int idExcluir) {
    String resultadoExcluir = proprietarioService.excluir(idExcluir);
    if ("ok".equals(resultadoExcluir)) {
        System.out.println("Proprietário excluído com sucesso!");
    } else {
        System.out.println("Erro ao excluir o proprietário. Verifique se o ID está correto.");
    }
}


    private void alterarProprietario() {
        // Fluxo Secundário 5: Verificar se o código do proprietário existe
        System.out.print("Digite o ID do proprietário a ser alterado: ");
        int idAlterar = lerInteiro();
    
        Proprietario proprietario = proprietarioService.buscarPorId(idAlterar);
        if (proprietario == null) {
            // Fluxo Secundário 5: Código de usuário inválido
            System.out.println("Erro: Proprietário não encontrado. Verifique se o ID está correto.");
            return;
        }
    
        // Fluxo Principal: Alterar os dados do proprietário
        System.out.print("Nome (atual: " + proprietario.getNome() + "): ");
        String novoNome = scanner.nextLine();
    
        System.out.print("Sexo (M/F) (atual: " + proprietario.getSexo() + "): ");
        String novoSexo = scanner.nextLine();
    
        System.out.print("CPF (atual: " + proprietario.getCpf() + "): ");
        String novoCpf = scanner.nextLine();
    
        System.out.print("E-mail (atual: " + proprietario.getEmail() + "): ");
        String novoEmail = scanner.nextLine();
    
        System.out.print("Celular (atual: " + proprietario.getCelular() + "): ");
        String novoCelular = scanner.nextLine();
    
        // Fluxo Secundário 1: Verificar se campos obrigatórios estão preenchidos
        if (novoNome.isEmpty() || novoSexo.isEmpty() || novoCpf.isEmpty() || novoEmail.isEmpty() || novoCelular.isEmpty()) {
            System.out.println("Erro: Todos os campos obrigatórios (nome, sexo, CPF, e-mail, celular) devem ser preenchidos.");
            return;
        }
    
        // Fluxo Secundário 2: Verificar se o CPF já está cadastrado no banco de dados
        if (!novoCpf.equals(proprietario.getCpf()) && proprietarioService.buscarPorCpf(novoCpf) != null) {
            System.out.println("Erro: O CPF já está cadastrado no sistema.");
            return;
        }
    
        // Fluxo Secundário 3: Verificar se o E-mail já está cadastrado no banco de dados
        if (!novoEmail.equals(proprietario.getEmail()) && proprietarioService.buscarPorEmail(novoEmail) != null) {
            System.out.println("Erro: O e-mail já está cadastrado no sistema.");
            return;
        }
    
        // Fluxo Secundário 4: Verificar se o celular já está cadastrado no banco de dados
        if (!novoCelular.equals(proprietario.getCelular()) && proprietarioService.buscarPorCelular(novoCelular) != null) {
            System.out.println("Erro: O número de celular já está cadastrado no sistema.");
            return;
        }
    
        // Fluxo Principal: Salvar as alterações no banco de dados
        Proprietario atualizado = proprietarioService.salvar(idAlterar, novoNome, novoSexo, novoCpf, novoEmail, novoCelular);
        if (atualizado != null) {
            System.out.println("Proprietário alterado com sucesso!");
        } else {
            System.out.println("Erro ao alterar o proprietário.");
        }
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