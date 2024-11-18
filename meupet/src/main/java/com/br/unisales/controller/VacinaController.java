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
        System.out.println("3. Voltar ao menu principal");
        System.out.print("Escolha uma opção: ");
        return lerInteiro();
    }

    private void cadastrarVacina() {
        System.out.print("Código do Animal (not null): ");
        Integer codigoAnimal = lerInteiro();
        if (codigoAnimal == null) {
            System.out.println("Código do Animal é obrigatório.");
            return;
        }

        System.out.print("Nome da Vacina (not null): ");
        String nome = scanner.nextLine().trim();
        if (nome.isEmpty()) {
            System.out.println("Nome da Vacina é obrigatório.");
            return;
        }

        System.out.print("Descrição da Vacina: ");
        String descricao = scanner.nextLine();

        System.out.print("Data de Aplicação (yyyy-MM-dd): ");
        LocalDate dataAplicacao;
        try {
            dataAplicacao = LocalDate.parse(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Data de Aplicação inválida.");
            return;
        }

        vacinaService.salvar(null, codigoAnimal, nome, descricao, dataAplicacao);
        System.out.println("Vacina cadastrada com sucesso!");
    }

    private void listarVacinas() {
        System.out.print("Código do Animal (opcional): ");
        String codigoAnimalInput = scanner.nextLine();
        Integer codigoAnimal = codigoAnimalInput.isEmpty() ? null : Integer.parseInt(codigoAnimalInput);

        System.out.print("Nome da Vacina (opcional): ");
        String nomeVacina = scanner.nextLine();

        List<Vacina> vacinas = vacinaService.buscarPorFiltro(codigoAnimal, nomeVacina);

        if (vacinas.isEmpty()) {
            System.out.println("Nenhuma vacina encontrada.");
        } else {
            vacinas.forEach(vacina -> System.out.println("ID: " + vacina.getId() + " | Nome: " + vacina.getNome() + " | Data: " + vacina.getDataAplicacao()));
        }
    }

    private int lerInteiro() {
        while (!scanner.hasNextInt()) {
            System.out.print("Entrada inválida. Digite um número inteiro: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine();
        return valor;
    }
}