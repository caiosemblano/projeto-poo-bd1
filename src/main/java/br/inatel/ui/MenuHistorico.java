package br.inatel.ui;

import br.inatel.dao.HistoricoPrecoDAO;
import br.inatel.model.Acao;
import br.inatel.model.HistoricoPreco;
import br.inatel.service.HistoricoPrecoService;

import java.math.BigDecimal;
import java.time.LocalDate;
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
        service.listarTodos();
    }

    private void buscarHistoricoPorData() {
        System.out.println("\n--- Buscar Histórico por Data ---");
        System.out.print("Digite a data (YYYY-MM-DD): ");
        String dataStr = scanner.nextLine();
        try {
            LocalDate data = LocalDate.parse(dataStr);
            service.buscarPorData(data);
        } catch (Exception e) {
            System.out.println("Formato de data inválido.");
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
        Acao ativo = new Acao();
        ativo.setIdAtivo(idAtivo);
        historico.setAtivo(ativo);
        historico.setData(data);
        historico.setPrecoAbertura(abertura);
        historico.setPrecoFechamento(fechamento);
        historico.setPrecoMaximo(maximo);
        historico.setPrecoMinimo(minimo);
        historico.setVolumeNegociado(volume);

        service.inserir(historico);
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
            return;
        }

        System.out.print("Novo Preço de Abertura (ou Enter para manter): ");
        String aberturaStr = scanner.nextLine();
        if (!aberturaStr.trim().isEmpty()) historico.setPrecoAbertura(new BigDecimal(aberturaStr));

        System.out.print("Novo Preço de Fechamento (ou Enter para manter): ");
        String fechamentoStr = scanner.nextLine();
        if (!fechamentoStr.trim().isEmpty()) historico.setPrecoFechamento(new BigDecimal(fechamentoStr));

        System.out.print("Novo Preço Máximo (ou Enter para manter): ");
        String maximoStr = scanner.nextLine();
        if (!maximoStr.trim().isEmpty()) historico.setPrecoMaximo(new BigDecimal(maximoStr));

        System.out.print("Novo Preço Mínimo (ou Enter para manter): ");
        String minimoStr = scanner.nextLine();
        if (!minimoStr.trim().isEmpty()) historico.setPrecoMinimo(new BigDecimal(minimoStr));

        System.out.print("Novo Volume Negociado (ou Enter para manter): ");
        String volumeStr = scanner.nextLine();
        if (!volumeStr.trim().isEmpty()) historico.setVolumeNegociado(new BigDecimal(volumeStr));

        service.atualizar(historico);
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
            return;
        }

        System.out.println("Tem certeza que deseja deletar este histórico? (S/N)");
        String confirmacao = scanner.nextLine().toUpperCase();

        if (confirmacao.equals("S")) {
            service.deletar(id);
        } else {
            System.out.println("Operação cancelada.");
        }
    }
}
