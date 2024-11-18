package com.br.unisales.main;

import com.br.unisales.controller.PetController;
import com.br.unisales.controller.ProprietarioController;
import com.br.unisales.controller.UsuarioController;
import com.br.unisales.controller.VacinaController;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        PetController petController = new PetController(scanner);
        ProprietarioController proprietarioController = new ProprietarioController(scanner);
        UsuarioController usuarioController = new UsuarioController(scanner);
        VacinaController vacinaController = new VacinaController(scanner);

        while (true) {
            switch (mostrarMenuPrincipal()) {
                case 1:
                    petController.gerenciarPets();
                    break;
                case 2:
                    proprietarioController.gerenciarProprietarios();
                    break;
                case 3:
                    usuarioController.gerenciarUsuarios();
                    break;
                case 4:
                    vacinaController.gerenciarVacinas();
                    break;
                case 5:
                    System.out.println("Saindo...");
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static int mostrarMenuPrincipal() {
        System.out.println("\nMenu Principal:");
        System.out.println("1. Gerenciar Pets");
        System.out.println("2. Gerenciar Proprietários");
        System.out.println("3. Gerenciar Usuários");
        System.out.println("4. Gerenciar Vacinas");
        System.out.println("5. Sair");
        System.out.print("Escolha uma opção: ");
        return lerInteiro();
    }

    private static int lerInteiro() {
        while (!scanner.hasNextInt()) {
            System.out.print("Entrada inválida. Digite um número inteiro: ");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha
        return valor;
    }
}