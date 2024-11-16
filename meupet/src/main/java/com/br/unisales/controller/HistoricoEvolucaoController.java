package com.br.unisales.controller;

import com.br.unisales.service.HistoricoEvolucaoService;
import com.br.unisales.table.HistoricoEvolucao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class HistoricoEvolucaoController {
    private final HistoricoEvolucaoService historicoEvolucaoService = new HistoricoEvolucaoService();
    private final Scanner scanner;

    public HistoricoEvolucaoController(Scanner scanner) {
        this.scanner = scanner;
    }

    public void gerenciarHistorico() {
        while (true) {
            switch (mostrarMenuHistorico()) {
                case 1:
                    cadastrarHistorico();
                    break;
                case 2:
                    listarHistorico();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private int mostrarMenuHistorico() {
        System.out.println("\nMenu Histórico de Evolução:");
        System.out.println("1. Cadastrar Histórico de Evolução");
        System.out.println("2. Listar Histórico de Evolução");
        System.out.println("3. Voltar ao menu principal");
        System.out.print("Escolha uma opção: ");
        return lerInteiro();
    }

    private void cadastrarHistorico() {
        System.out.print("Código do Pet (ID): ");
        Integer idPet = lerInteiro();

        System.out.print("Peso: ");
        Double peso = lerDouble();

        System.out.print("Altura: ");
        Double altura = lerDouble();

        LocalDateTime dataHora = LocalDateTime.now();

        historicoEvolucaoService.salvar(idPet, peso, altura, dataHora);
        System.out.println("Histórico de evolução cadastrado com sucesso!");
    }

    private void listarHistorico() {
        List<HistoricoEvolucao> lista = historicoEvolucaoService.listar();
        if (lista.isEmpty()) {
            System.out.println("Nenhum histórico encontrado.");
        } else {
            lista.forEach(h -> System.out.println("ID: " + h.getId() + " - Pet ID: " + h.getIdPet() +
                    " - Peso: " + h.getPeso() + " - Altura: " + h.getAltura() + " - Data e Hora: " + h.getDataHora()));
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

    private Double lerDouble() {
        while (!scanner.hasNextDouble()) {
            System.out.print("Entrada inválida. Digite um número decimal: ");
            scanner.next();
        }
        double valor = scanner.nextDouble();
        scanner.nextLine();
        return valor;
    }
}