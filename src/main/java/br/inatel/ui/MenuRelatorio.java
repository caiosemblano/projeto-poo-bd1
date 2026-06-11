package br.inatel.ui;

import br.inatel.dao.RelatorioDAO;
import br.inatel.service.RelatorioService;

import java.util.List;
import java.util.Scanner;

public class MenuRelatorio {

    private final Scanner scanner = new Scanner(System.in);
    private final RelatorioService service = new RelatorioService(new RelatorioDAO());

    public void exibirMenu() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- Relatórios e Consultas (JOINs) ---");
            System.out.println("1. Resumo do Portfólio (VIEW)");
            System.out.println("2. Histórico de Transações por Investidor");
            System.out.println("3. Objetivos com Situação da Carteira");
            System.out.println("4. Preço Atual vs Preço Histórico");
            System.out.println("5. Resumo de Compras/Vendas por Investidor");
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
                    gerarResumoPortfolio();
                    break;
                case 2:
                    historicoTransacoes();
                    break;
                case 3:
                    gerarObjetivosCarteira();
                    break;
                case 4:
                    comparativoPreco();
                    break;
                case 5:
                    gerarComprasEVendas();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void gerarResumoPortfolio() {
        List<String[]> dados = service.relatorioResumoPortfolio();
        if (dados.isEmpty()) {
            System.out.println("Nenhum dado encontrado.");
            return;
        }

        System.out.printf("\n%-20s | %-15s | %-10s | %-12s | %-12s | %-15s\n",
                "Carteira", "Investidor", "Ativo", "Quantidade", "Preço Atual", "Montante Total");
        System.out.println("--------------------------------------------------------------------------------------------------");
        for (String[] linha : dados) {
            System.out.printf("%-20s | %-15s | %-10s | %-12s | R$ %-9s | R$ %-12s\n",
                    linha[0], linha[1], linha[2], linha[3], linha[4], linha[5]);
        }
    }

    private void historicoTransacoes() {
        System.out.print("Digite o ID do Investidor: ");
        int id = -1;
        try {
            id = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("ID inválido.");
            scanner.nextLine();
            return;
        }

        List<String[]> dados = service.relatorioHistoricoTransacoes(id);
        if (dados.isEmpty()) {
            System.out.println("Nenhuma transação encontrada.");
            return;
        }

        System.out.printf("\n%-12s | %-8s | %-8s | %-15s | %-20s\n",
                "Data", "Tipo", "Ativo", "Carteira", "Investidor");
        System.out.println("------------------------------------------------------------------");
        for (String[] linha : dados) {
            System.out.printf("%-12s | %-8s | %-8s | %-15s | %-20s\n",
                    linha[0], linha[1], linha[2], linha[3], linha[4]);
        }
    }

    private void gerarObjetivosCarteira() {
        List<String[]> dados = service.relatorioObjetivosCarteira();
        if (dados.isEmpty()) {
            System.out.println("Nenhum dado encontrado.");
            return;
        }

        System.out.printf("\n%-25s | %-10s | %-8s | %-12s | %-20s | %-15s | %-15s\n",
                "Objetivo", "Meta (%)", "Prazo", "Status Obj.", "Carteira", "Total Invest.", "Investidor");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------");
        for (String[] linha : dados) {
            System.out.printf("%-25s | %-10s | %-5s m | %-12s | %-20s | R$ %-12s | %-15s\n",
                    linha[0], linha[1], linha[2], linha[3], linha[4], linha[5], linha[6]);
        }
    }

    private void comparativoPreco() {
        System.out.print("Digite o ID da Carteira: ");
        int id = -1;
        try {
            id = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("ID inválido.");
            scanner.nextLine();
            return;
        }

        List<String[]> dados = service.relatorioComparativoPreco(id);
        if (dados.isEmpty()) {
            System.out.println("Nenhum dado encontrado.");
            return;
        }

        System.out.printf("\n%-8s | %-20s | %-10s | %-12s | %-12s | %-12s | %-12s\n",
                "Símbolo", "Nome Ativo", "Quantidade", "Preço Atual", "Data Hist.", "Preço Hist.", "Variação");
        System.out.println("-----------------------------------------------------------------------------------------------------");
        for (String[] linha : dados) {
            System.out.printf("%-8s | %-20s | %-10s | R$ %-9s | %-12s | R$ %-9s | R$ %-9s\n",
                    linha[0], linha[1], linha[2], linha[3], linha[4], linha[5], linha[6]);
        }
    }

    private void gerarComprasEVendas() {
        List<String[]> dados = service.relatorioComprasEVendas();
        if (dados.isEmpty()) {
            System.out.println("Nenhum dado encontrado.");
            return;
        }

        System.out.printf("\n%-20s | %-8s | %-20s | %-20s\n",
                "Investidor", "Ativo", "Total Comprado", "Total Vendido");
        System.out.println("-------------------------------------------------------------------------------+");
        for (String[] linha : dados) {
            System.out.printf("%-20s | %-8s | R$ %-17s | R$ %-17s\n",
                    linha[0], linha[1], linha[2], linha[3]);
        }
    }
}
