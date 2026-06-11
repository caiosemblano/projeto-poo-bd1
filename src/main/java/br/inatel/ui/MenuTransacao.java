package br.inatel.ui;

import br.inatel.dao.TransacaoDAO;
import br.inatel.model.*;
import br.inatel.service.TransacaoService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuTransacao {

    private final Scanner scanner = new Scanner(System.in);
    private final TransacaoService service = new TransacaoService(new TransacaoDAO());

    public void exibirMenu() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- Gestão de Transações ---");
            System.out.println("1. Registrar nova transação");
            System.out.println("2. Listar transações por carteira");
            System.out.println("3. Buscar transações por tipo");
            System.out.println("4. Buscar transações por período");
            System.out.println("5. Atualizar comissão de transação");
            System.out.println("6. Deletar transação");
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
                    registrarTransacao();
                    break;
                case 2:
                    listarTransacoesPorCarteira();
                    break;
                case 3:
                    buscarTransacoesPorTipo();
                    break;
                case 4:
                    buscarTransacoesPorPeriodo();
                    break;
                case 5:
                    atualizarComissao();
                    break;
                case 6:
                    deletarTransacao();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void registrarTransacao() {
        System.out.println("\n--- Registrar Nova Transação ---");
        System.out.print("Digite o ID da Carteira: ");
        int idCarteira = -1;
        try {
            idCarteira = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("ID inválido.");
            scanner.nextLine();
            return;
        }

        System.out.print("Digite o ID do Ativo: ");
        int idAtivo = -1;
        try {
            idAtivo = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("ID inválido.");
            scanner.nextLine();
            return;
        }

        System.out.print("Tipo de transação (Compra / Venda): ");
        String tipo = scanner.nextLine();
        if (!tipo.equalsIgnoreCase("Compra") && !tipo.equalsIgnoreCase("Venda")) {
            System.out.println("Tipo inválido. Deve ser 'Compra' ou 'Venda'.");
            return;
        }

        System.out.print("Quantidade: ");
        BigDecimal quantidade;
        try {
            quantidade = new BigDecimal(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Quantidade inválida.");
            return;
        }

        System.out.print("Preço Unitário: ");
        BigDecimal precoUnitario;
        try {
            precoUnitario = new BigDecimal(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Preço inválido.");
            return;
        }

        System.out.print("Comissão: ");
        BigDecimal comissao = BigDecimal.ZERO;
        try {
            String comissaoStr = scanner.nextLine();
            if (!comissaoStr.trim().isEmpty()) {
                comissao = new BigDecimal(comissaoStr);
            }
        } catch (Exception e) {
            System.out.println("Comissão inválida. Definindo como 0.0.");
        }

        BigDecimal valorTotal = quantidade.multiply(precoUnitario);

        Transacao transacao = new Transacao();

        Carteira carteira = new Carteira();
        carteira.setIdCarteira(idCarteira);
        transacao.setCarteira(carteira);

        Acao ativo = new Acao();
        ativo.setIdAtivo(idAtivo);
        transacao.setAtivo(ativo);

        transacao.setTipoTransacao(tipo);
        transacao.setQuantidade(quantidade);
        transacao.setPrecoUnitario(precoUnitario);
        transacao.setValorTotal(valorTotal);
        transacao.setDataTransacao(LocalDate.now());
        transacao.setComissao(comissao);

        try {
            service.inserir(transacao);
            System.out.println("Transação registrada com sucesso! ID gerado: " + transacao.getIdTransacao());
        } catch (Exception e) {
            System.out.println("Erro ao registrar transação: " + e.getMessage());
        }
    }

    private void listarTransacoesPorCarteira() {
        System.out.println("\n--- Listar Transações por Carteira ---");
        System.out.print("Digite o ID da Carteira: ");
        int idCarteira = -1;
        try {
            idCarteira = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("ID inválido.");
            scanner.nextLine();
            return;
        }

        List<Transacao> transacoes = service.listarTransacoesPorCarteira(idCarteira);
        if (transacoes.isEmpty()) {
            System.out.println("Nenhuma transação encontrada para esta carteira.");
            return;
        }

        System.out.printf("%-5s | %-10s | %-8s | %-12s | %-15s | %-15s | %-12s | %-12s\n",
                "ID", "Tipo", "Ativo ID", "Qtd", "Preço Unit.", "Valor Total", "Comissão", "Data");
        System.out.println("------------------------------------------------------------------------------------------------------------------");
        for (Transacao t : transacoes) {
            System.out.printf("%-5d | %-10s | %-8d | %-12s | R$ %-11s | R$ %-11s | R$ %-9s | %-12s\n",
                    t.getIdTransacao(),
                    t.getTipoTransacao(),
                    t.getAtivo().getIdAtivo(),
                    t.getQuantidade(),
                    t.getPrecoUnitario(),
                    t.getValorTotal(),
                    t.getComissao(),
                    t.getDataTransacao());
        }
    }

    private void buscarTransacoesPorTipo() {
        System.out.println("\n--- Buscar Transações por Tipo ---");
        System.out.print("Digite o tipo de transação (Compra / Venda): ");
        String tipo = scanner.nextLine();
        if (!tipo.equalsIgnoreCase("Compra") && !tipo.equalsIgnoreCase("Venda")) {
            System.out.println("Tipo inválido. Deve ser 'Compra' ou 'Venda'.");
            return;
        }

        List<Transacao> transacoes = service.buscarPorTipo(tipo);
        if (transacoes.isEmpty()) {
            System.out.println("Nenhuma transação encontrada.");
            return;
        }

        System.out.printf("%-5s | %-8s | %-10s | %-12s | %-15s | %-15s | %-12s\n",
                "ID", "Cart. ID", "Ativo ID", "Qtd", "Preço Unit.", "Valor Total", "Data");
        System.out.println("--------------------------------------------------------------------------------------------");
        for (Transacao t : transacoes) {
            System.out.printf("%-5d | %-8d | %-10d | %-12s | R$ %-11s | R$ %-11s | %-12s\n",
                    t.getIdTransacao(),
                    t.getCarteira().getIdCarteira(),
                    t.getAtivo().getIdAtivo(),
                    t.getQuantidade(),
                    t.getPrecoUnitario(),
                    t.getValorTotal(),
                    t.getDataTransacao());
        }
    }

    private void buscarTransacoesPorPeriodo() {
        System.out.println("\n--- Buscar Transações por Período ---");
        System.out.print("Digite a data de início (YYYY-MM-DD): ");
        LocalDate inicio;
        try {
            inicio = LocalDate.parse(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Data de início inválida.");
            return;
        }

        System.out.print("Digite a data de fim (YYYY-MM-DD): ");
        LocalDate fim;
        try {
            fim = LocalDate.parse(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Data de fim inválida.");
            return;
        }

        List<Transacao> transacoes = service.buscarPorIntervaloData(inicio, fim);
        if (transacoes.isEmpty()) {
            System.out.println("Nenhuma transação encontrada para este período.");
            return;
        }

        System.out.printf("%-5s | %-10s | %-8s | %-10s | %-12s | %-15s | %-15s\n",
                "ID", "Data", "Tipo", "Cart. ID", "Ativo ID", "Qtd", "Valor Total");
        System.out.println("---------------------------------------------------------------------------------------------");
        for (Transacao t : transacoes) {
            System.out.printf("%-5d | %-10s | %-8s | %-10d | %-12d | %-15s | R$ %-12s\n",
                    t.getIdTransacao(),
                    t.getDataTransacao(),
                    t.getTipoTransacao(),
                    t.getCarteira().getIdCarteira(),
                    t.getAtivo().getIdAtivo(),
                    t.getQuantidade(),
                    t.getValorTotal());
        }
    }

    private void atualizarComissao() {
        System.out.println("\n--- Atualizar Comissão ---");
        System.out.print("Digite o ID da transação que deseja atualizar: ");
        int id = -1;
        try {
            id = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Erro: ID inválido.");
            scanner.nextLine();
            return;
        }

        Transacao transacao = service.buscarPorId(id);
        if (transacao == null) {
            System.out.println("Transação não encontrada!");
            return;
        }

        System.out.println("Comissão atual: R$ " + transacao.getComissao());
        System.out.print("Digite o novo valor de Comissão: ");
        BigDecimal novaComissao;
        try {
            novaComissao = new BigDecimal(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Comissão inválida.");
            return;
        }

        transacao.setComissao(novaComissao);
        try {
            service.atualizar(transacao);
            System.out.println("Comissão atualizada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar comissão: " + e.getMessage());
        }
    }

    private void deletarTransacao() {
        System.out.println("\n--- Deletar Transação ---");
        System.out.print("Digite o ID da transação que deseja remover: ");
        int id = -1;
        try {
            id = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Erro: ID inválido.");
            scanner.nextLine();
            return;
        }

        Transacao transacao = service.buscarPorId(id);
        if (transacao == null) {
            System.out.println("Transação não encontrada!");
            return;
        }

        System.out.println("Tem certeza que deseja deletar a transação ID " + transacao.getIdTransacao() + "? (S/N)");
        String confirmacao = scanner.nextLine().toUpperCase();

        if (confirmacao.equals("S")) {
            try {
                service.deletar(id);
                System.out.println("Transação deletada com sucesso!");
            } catch (Exception e) {
                System.out.println("Erro ao deletar transação: " + e.getMessage());
            }
        } else {
            System.out.println("Operação cancelada.");
        }
    }
}
