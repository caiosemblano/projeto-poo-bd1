package br.inatel.ui;

import br.inatel.model.*;
import br.inatel.service.CarteiraService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuCarteira {

    private final Scanner scanner;
    private final CarteiraService service;

    public MenuCarteira(Scanner scanner, CarteiraService service) {
        this.scanner = scanner;
        this.service = service;
    }

    public void exibirMenu() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- Gestão de Carteiras ---");
            System.out.println("1. Listar todas as carteiras");
            System.out.println("2. Buscar carteira por nome");
            System.out.println("3. Cadastrar nova carteira");
            System.out.println("4. Atualizar carteira");
            System.out.println("5. Deletar carteira");
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
                    listarCarteiras();
                    break;
                case 2:
                    buscarCarteiraPorNome();
                    break;
                case 3:
                    cadastrarCarteira();
                    break;
                case 4:
                    atualizarCarteira();
                    break;
                case 5:
                    deletarCarteira();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void listarCarteiras() {
        System.out.println("\n--- Lista de Carteiras Cadastradas ---");
        List<Carteira> carteiras = service.listarTodos();

        if (carteiras.isEmpty()) {
            System.out.println("Nenhuma carteira cadastrada.");
            return;
        }

        System.out.printf("%-5s | %-25s | %-18s | %-15s | %-35s\n",
                "ID", "Nome", "Valor Investido", "Data Criação", "Descrição");
        System.out.println("---------------------------------------------------------------------------------------------------------");
        for (Carteira carteira : carteiras) {
            String valor = carteira.getValorTotalInvestido() != null ? "R$ " + carteira.getValorTotalInvestido().toString() : "R$ 0.00";
            System.out.printf("%-5d | %-25s | %-18s | %-15s | %-35s\n",
                    carteira.getIdCarteira(),
                    carteira.getNomeCarteira(),
                    valor,
                    carteira.getDataCriacao(),
                    carteira.getDescricao() != null ? carteira.getDescricao() : "");
        }
    }

    private void buscarCarteiraPorNome() {
        System.out.println("\n--- Buscar Carteira por Nome ---");
        System.out.print("Digite o nome da carteira a ser pesquisada: ");
        String nome = scanner.nextLine();

        Carteira carteira = service.buscarPorNome(nome);
        if (carteira != null) {
            System.out.println("\nCarteira Encontrada:");
            System.out.println("ID: " + carteira.getIdCarteira());
            System.out.println("Nome: " + carteira.getNomeCarteira());
            System.out.println("Descrição: " + (carteira.getDescricao() != null ? carteira.getDescricao() : ""));
            System.out.println("Valor Total Investido: " + (carteira.getValorTotalInvestido() != null ? "R$ " + carteira.getValorTotalInvestido() : "R$ 0.00"));
            System.out.println("Data de Criação: " + carteira.getDataCriacao());
        } else {
            System.out.println("Carteira não encontrada.");
        }
    }

    private void cadastrarCarteira() {
        System.out.println("\n--- Cadastrar Nova Carteira ---");
        System.out.print("Digite o ID do Investidor dono desta carteira: ");
        int idInvestidor = -1;
        try {
            idInvestidor = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("ID inválido. Cancelando cadastro.");
            scanner.nextLine();
            return;
        }

        System.out.print("Digite o Nome da Carteira: ");
        String nome = scanner.nextLine();

        System.out.print("Digite a Descrição: ");
        String descricao = scanner.nextLine();

        System.out.print("Digite o Valor Total Investido Inicial: ");
        double valorInicial = 0.0;
        try {
            valorInicial = scanner.nextDouble();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Valor inválido. Definindo como 0.0.");
            scanner.nextLine();
        }

        Carteira carteira = new Carteira();
        Investidor investidor = new Investidor();
        investidor.setIdInvestidor(idInvestidor);
        carteira.setInvestidor(investidor);
        carteira.setNomeCarteira(nome);
        carteira.setDescricao(descricao);
        carteira.setValorTotalInvestido(BigDecimal.valueOf(valorInicial));
        carteira.setDataCriacao(LocalDate.now());

        try {
            service.inserir(carteira);
            System.out.println("Carteira cadastrada com sucesso! ID gerado: " + carteira.getIdCarteira());
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar carteira. Verifique se o ID do Investidor realmente existe.");
        }
    }

    private void atualizarCarteira() {
        System.out.println("\n--- Atualizar Carteira ---");
        System.out.print("Digite o ID da Carteira que deseja atualizar: ");
        int id = -1;
        try {
            id = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Erro: ID inválido.");
            scanner.nextLine();
            return;
        }

        Carteira carteira = service.buscarPorId(id);
        if (carteira == null) {
            System.out.println("Carteira não encontrada!");
            return;
        }

        System.out.println("Dados atuais: " + carteira.getNomeCarteira() + " (" + (carteira.getDescricao() != null ? carteira.getDescricao() : "") + ")");
        System.out.print("Digite o novo Nome (ou pressione Enter para manter): ");
        String novoNome = scanner.nextLine();
        if (!novoNome.trim().isEmpty()) {
            carteira.setNomeCarteira(novoNome);
        }

        System.out.print("Digite a nova Descrição (ou pressione Enter para manter): ");
        String novaDescricao = scanner.nextLine();
        if (!novaDescricao.trim().isEmpty()) {
            carteira.setDescricao(novaDescricao);
        }

        System.out.print("Digite o novo Valor Total (ou pressione Enter para manter): ");
        String novoValorStr = scanner.nextLine();
        if (!novoValorStr.trim().isEmpty()) {
            try {
                double novoValor = Double.parseDouble(novoValorStr);
                carteira.setValorTotalInvestido(BigDecimal.valueOf(novoValor));
            } catch (Exception e) {
                System.out.println("Valor inválido. Mantendo o valor anterior.");
            }
        }

        if (carteira.getInvestidor() == null) {
            System.out.print("Digite o ID do Investidor dono desta carteira: ");
            int idInvestidor = -1;
            try {
                idInvestidor = scanner.nextInt();
                scanner.nextLine();
                Investidor investidor = new Investidor();
                investidor.setIdInvestidor(idInvestidor);
                carteira.setInvestidor(investidor);
            } catch (Exception e) {
                System.out.println("ID inválido. Cancelando atualização.");
                scanner.nextLine();
                return;
            }
        }

        try {
            service.atualizar(carteira);
            System.out.println("Carteira atualizada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar carteira: " + e.getMessage());
        }
    }

    private void deletarCarteira() {
        System.out.println("\n--- Deletar Carteira ---");
        System.out.print("Digite o ID da Carteira que deseja remover: ");
        int id = -1;
        try {
            id = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Erro: ID inválido.");
            scanner.nextLine();
            return;
        }

        Carteira carteira = service.buscarPorId(id);
        if (carteira == null) {
            System.out.println("Carteira não encontrada!");
            return;
        }

        System.out.println("Tem certeza que deseja deletar a carteira " + carteira.getNomeCarteira() + "? (S/N)");
        String confirmacao = scanner.nextLine().toUpperCase();

        if (confirmacao.equals("S")) {
            try {
                service.deletar(id);
                System.out.println("Carteira deletada com sucesso!");
            } catch (Exception e) {
                System.out.println("Erro ao deletar carteira: " + e.getMessage());
            }
        } else {
            System.out.println("Operação cancelada.");
        }
    }
}
