package br.inatel.ui;

import br.inatel.dao.ObjetivoDAO;
import br.inatel.model.Carteira;
import br.inatel.model.Objetivo;
import br.inatel.service.ObjetivoService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuObjetivo {

    private final Scanner scanner = new Scanner(System.in);
    private final ObjetivoService service = new ObjetivoService(new ObjetivoDAO());

    public void exibirMenu() {
        int opcao = -1;
        do {
            System.out.println("\n--- Menu de Objetivos ---");
            System.out.println("1. Criar Objetivo");
            System.out.println("2. Listar Objetivos");
            System.out.println("3. Buscar Objetivos por Status");
            System.out.println("4. Atualizar Objetivo");
            System.out.println("5. Excluir Objetivo");
            System.out.println("0. Voltar para o Menu Principal");
            System.out.print("Escolha uma opção: ");
            
            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Erro de entrada, tente novamente.");
                scanner.nextLine();
                opcao = -1;
                continue;
            }

            switch (opcao) {
                case 1:
                    criarObjetivo();
                    break;
                case 2:
                    listarObjetivos();
                    break;
                case 3:
                    buscarObjetivosPorStatus();
                    break;
                case 4:
                    atualizarObjetivo();
                    break;
                case 5:
                    excluirObjetivo();
                    break;
                case 0:
                    System.out.println("Voltando ao Menu Principal...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void criarObjetivo() {
        System.out.println("\n--- Cadastrar Novo Objetivo ---");
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

        System.out.print("Meta de Rentabilidade em % (ex: 15.5 para 15,5%): ");
        BigDecimal meta;
        try {
            meta = new BigDecimal(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Valor inválido.");
            return;
        }

        System.out.print("Prazo (em meses): ");
        int prazo = -1;
        try {
            prazo = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Prazo inválido.");
            scanner.nextLine();
            return;
        }

        System.out.print("Status (ex: Em andamento, Concluído): ");
        String status = scanner.nextLine();

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        Objetivo objetivo = new Objetivo();
        Carteira carteira = new Carteira();
        carteira.setIdCarteira(idCarteira);
        objetivo.setCarteira(carteira);
        objetivo.setMetaRentabilidade(meta);
        objetivo.setPrazoMeses(prazo);
        objetivo.setDataCriacao(LocalDate.now());
        objetivo.setStatus(status);
        objetivo.setDescricao(descricao);

        service.inserir(objetivo);
    }

    private void listarObjetivos() {
        exibirTabelaObjetivos(service.listarTodos());
    }

    private void buscarObjetivosPorStatus() {
        System.out.print("\nDigite o status para busca: ");
        String status = scanner.nextLine();
        exibirTabelaObjetivos(service.buscarPorStatus(status));
    }

    private void exibirTabelaObjetivos(List<Objetivo> objetivos) {
        if (objetivos.isEmpty()) {
            System.out.println("Nenhum objetivo encontrado.");
            return;
        }

        System.out.printf("%-5s | %-12s | %-15s | %-10s | %-15s | %-15s | %-25s\n",
                "ID", "ID Carteira", "Meta Rent.", "Prazo", "Data Criação", "Status", "Descrição");
        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        for (Objetivo o : objetivos) {
            System.out.printf("%-5d | %-12d | %-15s | %-10d | %-15s | %-15s | %-25s\n",
                    o.getIdObjetivo(),
                    o.getCarteira() != null ? o.getCarteira().getIdCarteira() : 0,
                    o.getMetaRentabilidade() != null ? o.getMetaRentabilidade() + "%" : "N/A",
                    o.getPrazoMeses(),
                    o.getDataCriacao(),
                    o.getStatus(),
                    o.getDescricao() != null ? o.getDescricao() : "");
        }
    }

    private void atualizarObjetivo() {
        System.out.println("\n--- Atualizar Objetivo ---");
        System.out.print("Digite o ID do Objetivo a atualizar: ");
        int id = -1;
        try {
            id = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("ID inválido.");
            scanner.nextLine();
            return;
        }

        Objetivo objetivo = service.buscarPorId(id);
        if (objetivo == null) {
            return;
        }

        System.out.print("Nova Meta de Rentabilidade (ou Enter para manter): ");
        String metaStr = scanner.nextLine();
        if (!metaStr.trim().isEmpty()) {
            try {
                objetivo.setMetaRentabilidade(new BigDecimal(metaStr));
            } catch (Exception e) {
                System.out.println("Valor inválido, mantendo anterior.");
            }
        }

        System.out.print("Novo Prazo em meses (ou Enter para manter): ");
        String prazoStr = scanner.nextLine();
        if (!prazoStr.trim().isEmpty()) {
            try {
                objetivo.setPrazoMeses(Integer.parseInt(prazoStr));
            } catch (Exception e) {
                System.out.println("Valor inválido, mantendo anterior.");
            }
        }

        System.out.print("Novo Status (ou Enter para manter): ");
        String status = scanner.nextLine();
        if (!status.trim().isEmpty()) objetivo.setStatus(status);

        System.out.print("Nova Descrição (ou Enter para manter): ");
        String descricao = scanner.nextLine();
        if (!descricao.trim().isEmpty()) objetivo.setDescricao(descricao);

        service.atualizar(objetivo);
    }

    private void excluirObjetivo() {
        System.out.println("\n--- Excluir Objetivo ---");
        System.out.print("Digite o ID do Objetivo a remover: ");
        int id = -1;
        try {
            id = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("ID inválido.");
            scanner.nextLine();
            return;
        }

        Objetivo objetivo = service.buscarPorId(id);
        if (objetivo == null) {
            return;
        }

        System.out.println("Tem certeza que deseja deletar este objetivo? (S/N)");
        String confirmacao = scanner.nextLine().toUpperCase();

        if (confirmacao.equals("S")) {
            service.deletar(id);
        } else {
            System.out.println("Operação cancelada.");
        }
    }
}
