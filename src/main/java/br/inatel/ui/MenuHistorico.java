package br.inatel.ui;

import br.inatel.dao.HistoricoPrecoDAO;
import br.inatel.model.Ativo;
import br.inatel.model.HistoricoPreco;
import br.inatel.service.AtivoService;
import br.inatel.service.HistoricoPrecoService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuHistorico {

    private final Scanner scanner = new Scanner(System.in);
    private final HistoricoPrecoService service = new HistoricoPrecoService(new HistoricoPrecoDAO());

    public void exibirMenu() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- Gestão de Histórico de Preços ---");
            System.out.println("1. Listar todos os históricos");
            System.out.println("2. Buscar históricos por data");
            System.out.println("3. Cadastrar novo histórico");
            System.out.println("4. Atualizar histórico");
            System.out.println("5. Deletar histórico");
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
                    listarHistoricos();
                    break;
                case 2:
                    buscarHistoricoPorData();
                    break;
                case 3:
                    cadastrarHistorico();
                    break;
                case 4:
                    atualizarHistorico();
                    break;
                case 5:
                    deletarHistorico();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void listarHistoricos() {
        exibirTabelaHistoricos(service.listarTodos());
    }

    private void buscarHistoricoPorData() {
        System.out.println("\n--- Buscar Histórico por Data ---");
        System.out.print("Digite a data (YYYY-MM-DD): ");
        String dataStr = scanner.nextLine();
        try {
            LocalDate data = LocalDate.parse(dataStr);
            exibirTabelaHistoricos(service.buscarPorData(data));
        } catch (Exception e) {
            System.out.println("Formato de data inválido.");
        }
    }

    private void exibirTabelaHistoricos(List<HistoricoPreco> historicos) {
        if (historicos.isEmpty()) {
            System.out.println("Nenhum histórico encontrado.");
            return;
        }

        System.out.printf("\n%-5s | %-8s | %-12s | %-15s | %-15s | %-15s | %-15s | %-15s\n",
                "ID", "ID Ativo", "Data", "Abertura", "Fechamento", "Máximo", "Mínimo", "Volume");
        System.out.println(
                "-----------------------------------------------------------------------------------------------------------------------");
        for (HistoricoPreco h : historicos) {
            System.out.printf("%-5d | %-8d | %-12s | R$ %-12s | R$ %-12s | R$ %-12s | R$ %-12s | R$ %-12s\n",
                    h.getIdHistorico(),
                    h.getAtivo() != null ? h.getAtivo().getIdAtivo() : 0,
                    h.getData(),
                    h.getPrecoAbertura(),
                    h.getPrecoFechamento(),
                    h.getPrecoMaximo(),
                    h.getPrecoMinimo(),
                    h.getVolumeNegociado());
        }
    }

    private void cadastrarHistorico() {
        System.out.println("\n--- Cadastrar Novo Histórico ---");
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

        System.out.print("Digite a data (YYYY-MM-DD): ");
        String dataStr = scanner.nextLine();
        LocalDate data;
        try {
            data = LocalDate.parse(dataStr);
        } catch (Exception e) {
            System.out.println("Formato de data inválido.");
            return;
        }

        System.out.print("Preço de Abertura: ");
        BigDecimal abertura = new BigDecimal(scanner.nextLine());
        System.out.print("Preço de Fechamento: ");
        BigDecimal fechamento = new BigDecimal(scanner.nextLine());
        System.out.print("Preço Máximo: ");
        BigDecimal maximo = new BigDecimal(scanner.nextLine());
        System.out.print("Preço Mínimo: ");
        BigDecimal minimo = new BigDecimal(scanner.nextLine());
        System.out.print("Volume Negociado: ");
        BigDecimal volume = new BigDecimal(scanner.nextLine());

        HistoricoPreco historico = new HistoricoPreco();
        AtivoService ativoService = new AtivoService();
        Ativo ativo = ativoService.buscarPorId(idAtivo);
        if (ativo == null) {
            System.out.println("ID fornecido não existente.");
            return;
        }
        ativo.setIdAtivo(idAtivo);
        historico.setAtivo(ativo);
        historico.setData(data);
        historico.setPrecoAbertura(abertura);
        historico.setPrecoFechamento(fechamento);
        historico.setPrecoMaximo(maximo);
        historico.setPrecoMinimo(minimo);
        historico.setVolumeNegociado(volume);

        try {
            service.inserir(historico);
            System.out.println("Histórico cadastrado com sucesso! ID gerado: " + historico.getIdHistorico());
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar histórico: " + e.getMessage());
        }
    }

    private void atualizarHistorico() {
        System.out.println("\n--- Atualizar Histórico ---");
        System.out.print("Digite o ID do Histórico a atualizar: ");
        int id = -1;
        try {
            id = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("ID inválido.");
            scanner.nextLine();
            return;
        }

        HistoricoPreco historico = service.buscarPorId(id);
        if (historico == null) {
            System.out.println("Histórico não encontrado!");
            return;
        }

        System.out.print("Novo Preço de Abertura (ou Enter para manter): ");
        String aberturaStr = scanner.nextLine();
        if (!aberturaStr.trim().isEmpty())
            historico.setPrecoAbertura(new BigDecimal(aberturaStr));

        System.out.print("Novo Preço de Fechamento (ou Enter para manter): ");
        String fechamentoStr = scanner.nextLine();
        if (!fechamentoStr.trim().isEmpty())
            historico.setPrecoFechamento(new BigDecimal(fechamentoStr));

        System.out.print("Novo Preço Máximo (ou Enter para manter): ");
        String maximoStr = scanner.nextLine();
        if (!maximoStr.trim().isEmpty())
            historico.setPrecoMaximo(new BigDecimal(maximoStr));

        System.out.print("Novo Preço Mínimo (ou Enter para manter): ");
        String minimoStr = scanner.nextLine();
        if (!minimoStr.trim().isEmpty())
            historico.setPrecoMinimo(new BigDecimal(minimoStr));

        System.out.print("Novo Volume Negociado (ou Enter para manter): ");
        String volumeStr = scanner.nextLine();
        if (!volumeStr.trim().isEmpty())
            historico.setVolumeNegociado(new BigDecimal(volumeStr));

        try {
            service.atualizar(historico);
            System.out.println("Histórico atualizado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar histórico: " + e.getMessage());
        }
    }

    private void deletarHistorico() {
        System.out.println("\n--- Deletar Histórico ---");
        System.out.print("Digite o ID do Histórico a remover: ");
        int id = -1;
        try {
            id = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("ID inválido.");
            scanner.nextLine();
            return;
        }

        HistoricoPreco historico = service.buscarPorId(id);
        if (historico == null) {
            System.out.println("Histórico não encontrado!");
            return;
        }

        System.out.println("Tem certeza que deseja deletar este histórico? (S/N)");
        String confirmacao = scanner.nextLine().toUpperCase();

        if (confirmacao.equals("S")) {
            try {
                service.deletar(id);
                System.out.println("Histórico deletado com sucesso!");
            } catch (Exception e) {
                System.out.println("Erro ao deletar histórico: " + e.getMessage());
            }
        } else {
            System.out.println("Operação cancelada.");
        }
    }
}
