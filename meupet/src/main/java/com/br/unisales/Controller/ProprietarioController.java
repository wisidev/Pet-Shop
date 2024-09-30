package com.br.unisales.controller;

import java.util.List;
import java.util.Scanner;
import com.br.unisales.service.ProprietarioService;
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

        Proprietario novoProprietario = proprietarioService.salvar(null, nome, sexo, cpf, email, celular);
        if (novoProprietario != null) {
            System.out.println("Proprietário cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar o proprietário.");
        }
    }

    private void listarProprietarios() {
        List<Proprietario> lista = proprietarioService.listar();
        if (lista.isEmpty()) {
            System.out.println("Não há proprietários cadastrados.");
        } else {
            lista.forEach(proprietario -> System.out.println("Id: " + proprietario.getId() + " - Nome: " + proprietario.getNome() +
                    " - Sexo: " + proprietario.getSexo() + " - CPF: " + proprietario.getCpf() + " - E-mail: " + proprietario.getEmail() +
                    " - Celular: " + proprietario.getCelular()));
        }
    }

    private void excluirProprietario() {
        System.out.print("Digite o ID do proprietário a ser excluído: ");
        int idExcluir = lerInteiro();
        String resultadoExcluir = proprietarioService.excluir(idExcluir);
        if ("ok".equals(resultadoExcluir)) {
            System.out.println("Proprietário excluído com sucesso!");
        } else {
            System.out.println("Erro ao excluir o proprietário. Verifique se o ID está correto.");
        }
    }

    private void alterarProprietario() {
        System.out.print("Digite o ID do proprietário a ser alterado: ");
        int idAlterar = lerInteiro();

        Proprietario proprietario = proprietarioService.buscarPorId(idAlterar);
        if (proprietario == null) {
            System.out.println("Proprietário não encontrado. Verifique se o ID está correto.");
            return;
        }

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