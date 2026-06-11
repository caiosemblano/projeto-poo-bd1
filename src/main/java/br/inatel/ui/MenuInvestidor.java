package br.inatel.ui;

import br.inatel.dao.InvestidorDAO;
import br.inatel.model.Investidor;
import br.inatel.service.InvestidorService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuInvestidor {

    private final Scanner scanner = new Scanner(System.in);
    private final InvestidorService service = new InvestidorService(new InvestidorDAO());

    public void exibirMenu() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- Gestão de Investidores ---");
            System.out.println("1. Listar todos os investidores");
            System.out.println("2. Buscar investidor por e-mail");
            System.out.println("3. Buscar investidores por nome");
            System.out.println("4. Cadastrar novo investidor");
            System.out.println("5. Atualizar investidor");
            System.out.println("6. Inativar investidor");
            System.out.println("7. Deletar investidor");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Erro: Entrada inválida. Digite um número.");
                scanner.nextLine();
                opcao = -1;
                continue;
            }

            switch (opcao) {
                case 1:
                    listarInvestidores();
                    break;
                case 2:
                    buscarPorEmail();
                    break;
                case 3:
                    buscarPorNome();
                    break;
                case 4:
                    cadastrarInvestidor();
                    break;
                case 5:
                    atualizarInvestidor();
                    break;
                case 6:
                    inativarInvestidor();
                    break;
                case 7:
                    deletarInvestidor();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void listarInvestidores() {
        System.out.println("\n--- Lista de Investidores ---");
        List<Investidor> investidores = service.listarTodos();

        if (investidores.isEmpty()) {
            System.out.println("Nenhum investidor cadastrado.");
            return;
        }

        System.out.printf("%-5s | %-30s | %-30s | %-15s | %-15s | %-10s\n",
                "ID", "Nome", "E-mail", "Telefone", "Data Cadastro", "Status");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------");
        for (Investidor inv : investidores) {
            System.out.printf("%-5d | %-30s | %-30s | %-15s | %-15s | %-10s\n",
                    inv.getIdInvestidor(),
                    inv.getNome(),
                    inv.getEmail(),
                    inv.getTelefone() != null ? inv.getTelefone() : "",
                    inv.getDataCadastro(),
                    inv.getStatus());
        }
    }

    private void buscarPorEmail() {
        System.out.println("\n--- Buscar Investidor por E-mail ---");
        System.out.print("Digite o e-mail a ser pesquisado: ");
        String email = scanner.nextLine();

        Investidor inv = service.buscarPorEmail(email);
        if (inv != null) {
            System.out.println("\nInvestidor Encontrado:");
            System.out.println("ID: " + inv.getIdInvestidor());
            System.out.println("Nome: " + inv.getNome());
            System.out.println("E-mail: " + inv.getEmail());
            System.out.println("Telefone: " + (inv.getTelefone() != null ? inv.getTelefone() : ""));
            System.out.println("Data Cadastro: " + inv.getDataCadastro());
            System.out.println("Status: " + inv.getStatus());
        } else {
            System.out.println("Investidor não encontrado.");
        }
    }

    private void buscarPorNome() {
        System.out.println("\n--- Buscar Investidores por Nome ---");
        System.out.print("Digite o nome a ser pesquisado: ");
        String nome = scanner.nextLine();

        List<Investidor> lista = service.buscarPorNome(nome);
        if (lista.isEmpty()) {
            System.out.println("Nenhum investidor encontrado.");
            return;
        }

        System.out.printf("\n%-5s | %-30s | %-30s | %-15s | %-10s\n",
                "ID", "Nome", "E-mail", "Telefone", "Status");
        System.out.println("-----------------------------------------------------------------------------------------------------");
        for (Investidor inv : lista) {
            System.out.printf("%-5d | %-30s | %-30s | %-15s | %-10s\n",
                    inv.getIdInvestidor(),
                    inv.getNome(),
                    inv.getEmail(),
                    inv.getTelefone() != null ? inv.getTelefone() : "",
                    inv.getStatus());
        }
    }

    private void cadastrarInvestidor() {
        System.out.println("\n--- Cadastrar Novo Investidor ---");
        System.out.print("Digite o Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o E-mail: ");
        String email = scanner.nextLine();

        System.out.print("Digite o Telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Digite a Senha: ");
        String senha = scanner.nextLine();

        Investidor inv = new Investidor();
        inv.setNome(nome);
        inv.setEmail(email);
        inv.setTelefone(telefone);
        inv.setSenha(senha);
        inv.setDataCadastro(LocalDate.now());
        inv.setStatus("Ativo");

        try {
            service.inserir(inv);
            System.out.println("Investidor cadastrado com sucesso! ID gerado: " + inv.getIdInvestidor());
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar investidor: " + e.getMessage());
        }
    }

    private void atualizarInvestidor() {
        System.out.println("\n--- Atualizar Investidor ---");
        System.out.print("Digite o ID do Investidor que deseja atualizar: ");
        int id = -1;
        try {
            id = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Erro: ID inválido.");
            scanner.nextLine();
            return;
        }

        Investidor inv = service.buscarPorId(id);
        if (inv == null) {
            System.out.println("Investidor não encontrado!");
            return;
        }

        System.out.println("Dados atuais: " + inv.getNome() + " (" + inv.getEmail() + ")");
        System.out.print("Digite o novo E-mail (ou pressione Enter para manter): ");
        String novoEmail = scanner.nextLine();
        if (!novoEmail.trim().isEmpty()) {
            inv.setEmail(novoEmail);
        }

        System.out.print("Digite o novo Telefone (ou pressione Enter para manter): ");
        String novoTelefone = scanner.nextLine();
        if (!novoTelefone.trim().isEmpty()) {
            inv.setTelefone(novoTelefone);
        }

        System.out.print("Digite a nova Senha (ou pressione Enter para manter): ");
        String novaSenha = scanner.nextLine();
        if (!novaSenha.trim().isEmpty()) {
            inv.setSenha(novaSenha);
        }

        try {
            service.atualizar(inv);
            System.out.println("Investidor atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar investidor: " + e.getMessage());
        }
    }

    private void inativarInvestidor() {
        System.out.println("\n--- Inativar Investidor ---");
        System.out.print("Digite o ID do Investidor que deseja inativar (Procedure): ");
        int id = -1;
        try {
            id = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Erro: ID inválido.");
            scanner.nextLine();
            return;
        }

        Investidor inv = service.buscarPorId(id);
        if (inv == null) {
            System.out.println("Investidor não encontrado!");
            return;
        }

        System.out.println("Tem certeza que deseja inativar o investidor " + inv.getNome() + "? (S/N)");
        String confirmacao = scanner.nextLine().toUpperCase();

        if (confirmacao.equals("S")) {
            try {
                service.inativar(id);
                System.out.println("Investidor inativado com sucesso (via Procedure)!");
            } catch (Exception e) {
                System.out.println("Erro ao inativar investidor: " + e.getMessage());
            }
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    private void deletarInvestidor() {
        System.out.println("\n--- Deletar Investidor ---");
        System.out.print("Digite o ID do Investidor que deseja remover: ");
        int id = -1;
        try {
            id = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Erro: ID inválido.");
            scanner.nextLine();
            return;
        }

        Investidor inv = service.buscarPorId(id);
        if (inv == null) {
            System.out.println("Investidor não encontrado!");
            return;
        }

        System.out.println("Tem certeza que deseja deletar o investidor " + inv.getNome() + " e todas as suas carteiras associadas? (S/N)");
        String confirmacao = scanner.nextLine().toUpperCase();

        if (confirmacao.equals("S")) {
            try {
                service.deletar(id);
                System.out.println("Investidor deletado com sucesso!");
            } catch (Exception e) {
                System.out.println("Erro ao deletar investidor: " + e.getMessage());
            }
        } else {
            System.out.println("Operação cancelada.");
        }
    }
}
