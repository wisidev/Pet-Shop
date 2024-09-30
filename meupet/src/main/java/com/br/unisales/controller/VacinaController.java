package com.br.unisales.controller;

import com.br.unisales.service.VacinaService;
import com.br.unisales.table.Vacina;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class VacinaController {
    private final VacinaService vacinaService = new VacinaService();
    private final Scanner scanner;

    public VacinaController(Scanner scanner) {
        this.scanner = scanner;
    }

    public void gerenciarVacinas() {
        while (true) {
            switch (mostrarMenuVacinas()) {
                case 1:
                    cadastrarVacina();
                    break;
                case 2:
                    listarVacinas();
                    break;
                case 3:
                    excluirVacina();
                    break;
                case 4:
                    alterarVacina();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private int mostrarMenuVacinas() {
        System.out.println("\nMenu Vacinas:");
        System.out.println("1. Cadastrar vacina do Pet");
        System.out.println("2. Listar vacinas do Pet");
        System.out.println("3. Excluir vacina");
        System.out.println("4. Alterar vacina");
        System.out.println("5. Voltar ao menu principal");
        System.out.print("Escolha uma opção: ");
        return lerInteiro();
    }

    private void cadastrarVacina() {
        System.out.print("Código do Animal: ");
        Integer codigoAnimal = lerInteiro();

        System.out.print("Nome da Vacina: ");
        String nome = scanner.nextLine();

        System.out.print("Descrição da Vacina: ");
        String descricao = scanner.nextLine();

        System.out.print("Data de Aplicação (yyyy-MM-dd): ");
        LocalDate dataAplicacao = LocalDate.parse(scanner.nextLine());

        vacinaService.salvar(null, codigoAnimal, nome, descricao, dataAplicacao);
        System.out.println("Vacina cadastrada com sucesso!");
    }

    private void listarVacinas() {
        List<Vacina> lista = vacinaService.listar();
        if (lista.isEmpty()) {
            System.out.println("Não há vacinas cadastradas.");
        } else {
            lista.forEach(vacina -> System.out.println("Id: " + vacina.getId() + " - Código do Animal: " + vacina.getCodigoAnimal() +
                    " - Nome: " + vacina.getNome() + " - Descrição: " + vacina.getDescricao() +
                    " - Data de Aplicação: " + vacina.getDataAplicacao()));
        }
    }

    private void excluirVacina() {
        System.out.print("Digite o ID da vacina a ser excluída: ");
        int idExcluir = lerInteiro();
        String resultadoExcluir = vacinaService.excluir(idExcluir);
        if ("ok".equals(resultadoExcluir)) {
            System.out.println("Vacina excluída com sucesso!");
        } else {
            System.out.println("Erro ao excluir a vacina. Verifique se o ID está correto.");
        }
    }

    private void alterarVacina() {
        System.out.print("Digite o ID da vacina a ser alterada: ");
        int idAlterar = lerInteiro();

        Vacina vacina = vacinaService.buscarPorId(idAlterar);
        if (vacina == null) {
            System.out.println("Vacina não encontrada. Verifique se o ID está correto.");
            return;
        }

        System.out.print("Nome da Vacina (atual: " + vacina.getNome() + "): ");
        String novoNome = scanner.nextLine();

        System.out.print("Descrição (atual: " + vacina.getDescricao() + "): ");
        String novaDescricao = scanner.nextLine();

        System.out.print("Data de Aplicação (atual: " + vacina.getDataAplicacao() + "): ");
        LocalDate novaDataAplicacao = LocalDate.parse(scanner.nextLine());

        vacinaService.salvar(idAlterar, vacina.getCodigoAnimal(), novoNome, novaDescricao, novaDataAplicacao);
        System.out.println("Vacina alterada com sucesso!");
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