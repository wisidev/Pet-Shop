package com.br.unisales.controller;


import com.br.unisales.service.UsuarioService;
import com.br.unisales.table.Usuario;

import java.util.List;
import java.util.Scanner;

public class UsuarioController {
    private final UsuarioService usuarioService = new UsuarioService();
    private final Scanner scanner;

    public UsuarioController(Scanner scanner) {
        this.scanner = scanner;
    }

    public void gerenciarUsuarios() {
        while (true) {
            switch (mostrarMenuUsuarios()) {
                case 1:
                    cadastrarUsuario();
                    break;
                case 2:
                    listarUsuarios();
                    break;
                case 3:
                    excluirUsuario();
                    break;
                case 4:
                    alterarUsuario();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private int mostrarMenuUsuarios() {
        System.out.println("\nMenu Usuários:");
        System.out.println("1. Cadastrar Usuário");
        System.out.println("2. Listar Usuários");
        System.out.println("3. Excluir Usuário");
        System.out.println("4. Alterar Usuário");
        System.out.println("5. Voltar ao menu principal");
        System.out.print("Escolha uma opção: ");
        return lerInteiro();
    }

    private void cadastrarUsuario() {
        System.out.print("Nome do Usuário: ");
        String nome = scanner.nextLine();

        System.out.print("Sexo (M/F): ");
        String sexo = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        System.out.print("Grupo: ");
        String grupo = scanner.nextLine();

        usuarioService.salvar(null, nome, sexo, email, senha, grupo);
        System.out.println("Usuário cadastrado com sucesso!");
    }

    private void listarUsuarios() {
        List<Usuario> lista = usuarioService.listar();
        if (lista.isEmpty()) {
            System.out.println("Não há usuários cadastrados.");
        } else {
            lista.forEach(usuario -> System.out.println("Id: " + usuario.getId() + " - Nome: " + usuario.getNome() +
                    " - Sexo: " + usuario.getSexo() + " - Email: " + usuario.getEmail() + " - Grupo: " + usuario.getGrupo()));
        }
    }

    private void excluirUsuario() {
        System.out.print("Digite o ID do usuário a ser excluído: ");
        int idExcluir = lerInteiro();
        String resultadoExcluir = usuarioService.excluir(idExcluir);
        if ("ok".equals(resultadoExcluir)) {
            System.out.println("Usuário excluído com sucesso!");
        } else {
            System.out.println("Erro ao excluir o usuário. Verifique se o ID está correto.");
        }
    }

    private void alterarUsuario() {
        System.out.print("Digite o ID do usuário a ser alterado: ");
        int idAlterar = lerInteiro();

        Usuario usuario = usuarioService.buscarPorId(idAlterar);
        if (usuario == null) {
            System.out.println("Usuário não encontrado. Verifique se o ID está correto.");
            return;
        }

        System.out.print("Nome do Usuário (atual: " + usuario.getNome() + "): ");
        String novoNome = scanner.nextLine();

        System.out.print("Sexo (M/F) (atual: " + usuario.getSexo() + "): ");
        String novoSexo = scanner.nextLine();

        System.out.print("Email (atual: " + usuario.getEmail() + "): ");
        String novoEmail = scanner.nextLine();

        System.out.print("Senha (atual: " + usuario.getSenha() + "): ");
        String novaSenha = scanner.nextLine();

        System.out.print("Grupo (atual: " + usuario.getGrupo() + "): ");
        String novoGrupo = scanner.nextLine();

        usuarioService.salvar(idAlterar, novoNome, novoSexo, novoEmail, novaSenha, novoGrupo);
        System.out.println("Usuário alterado com sucesso!");
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

