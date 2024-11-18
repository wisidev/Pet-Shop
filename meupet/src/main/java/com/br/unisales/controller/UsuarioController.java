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
        String nome, sexo, email, senha, grupo;
    
        while (true) {
            System.out.print("Nome do Usuário (obrigatório): ");
            nome = scanner.nextLine();
            if (nome.trim().isEmpty()) {
                System.out.println("O nome é obrigatório. Tente novamente.");
                continue;
            }
    
            System.out.print("Sexo (M/F) (obrigatório): ");
            sexo = scanner.nextLine();
            if (sexo.trim().isEmpty() || (!sexo.equalsIgnoreCase("M") && !sexo.equalsIgnoreCase("F"))) {
                System.out.println("Sexo é obrigatório e deve ser M ou F. Tente novamente.");
                continue;
            }
    
            System.out.print("Email (obrigatório): ");
            email = scanner.nextLine();
            if (email.trim().isEmpty()) {
                System.out.println("O email é obrigatório. Tente novamente.");
                continue;
            }
    
            System.out.print("Senha (obrigatório): ");
            senha = scanner.nextLine();
            if (senha.trim().isEmpty()) {
                System.out.println("A senha é obrigatória. Tente novamente.");
                continue;
            }
    
            System.out.print("Grupo (Administrador, Proprietário, Secretário, Veterinário) (obrigatório): ");
            grupo = scanner.nextLine();
            if (grupo.trim().isEmpty() || !(grupo.equalsIgnoreCase("Administrador") || 
                                            grupo.equalsIgnoreCase("Proprietário") || 
                                            grupo.equalsIgnoreCase("Secretário") || 
                                            grupo.equalsIgnoreCase("Veterinário"))) {
                System.out.println("Grupo inválido. Escolha entre Administrador, Proprietário, Secretário ou Veterinário.");
                continue;
            }
    
            // Salva os dados do usuário no banco de dados
            usuarioService.salvar(null, nome, sexo, email, senha, grupo);
            System.out.println("Usuário cadastrado com sucesso!");
    
            // Se o grupo for "Proprietário", abrir tela de cadastro de proprietário
            if (grupo.equalsIgnoreCase("Proprietário")) {
                cadastrarProprietario();
            }
    
            break;
        }
    }
    
    private void cadastrarProprietario() {
        String nomeProprietario, cpf, endereco;
    
        // Coleta os dados do proprietário
        System.out.print("Nome do Proprietário: ");
        nomeProprietario = scanner.nextLine();
    
        System.out.print("CPF do Proprietário: ");
        cpf = scanner.nextLine();
    
        System.out.print("Endereço do Proprietário: ");
        endereco = scanner.nextLine();
    
        // Validação dos dados (poderiam ser mais rigorosos, como verificar CPF ou formato de endereço)
        if (nomeProprietario.trim().isEmpty() || cpf.trim().isEmpty() || endereco.trim().isEmpty()) {
            System.out.println("Todos os campos do proprietário são obrigatórios.");
            return; // Caso algum campo esteja vazio, encerra o cadastro do proprietário
        }
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