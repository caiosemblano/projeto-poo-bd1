package br.inatel.ui;

import br.inatel.model.*;
import br.inatel.service.AtivoService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuAtivo {

    Scanner scanner;
    AtivoService service;

    public MenuAtivo() {
        this.scanner = scanner;
        this.service = service;
    }

    public void exibirMenu() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- Gestão de Ativos ---");
            System.out.println("1. Listar todos os ativos");
            System.out.println("2. Buscar ativo por símbolo");
            System.out.println("3. Buscar ativos por tipo");
            System.out.println("4. Cadastrar novo ativo");
            System.out.println("5. Atualizar ativo");
            System.out.println("6. Deletar ativo");
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
                    listarAtivos();
                    break;
                case 2:
                    buscarAtivoPorSimbolo();
                    break;
                case 3:
                    buscarAtivosPorTipo();
                    break;
                case 4:
                    cadastrarAtivo();
                    break;
                case 5:
                    atualizarAtivo();
                    break;
                case 6:
                    deletarAtivo();
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void listarAtivos() {
        System.out.println("\n--- Lista de Ativos Cadastrados ---");
        List<Ativo> ativos = service.listarTodos();

        if (ativos.isEmpty()) {
            System.out.println("Nenhum ativo cadastrado.");
            return;
        }

        System.out.printf("%-5s | %-15s | %-8s | %-25s | %-30s\n",
                "ID", "Tipo", "Símbolo", "Nome", "Descrição Resumida (Polimorfismo)");
        System.out.println("-----------------------------------------------------------------------------------------");
        for (Ativo ativo : ativos) {
            System.out.printf("%-5d | %-15s | %-8s | %-25s | %-30s\n",
                    ativo.getIdAtivo(),
                    ativo.getTipoAtivo(),
                    ativo.getSimbolo(),
                    ativo.getNomeAtivo(),
                    ativo.descricaoResumida());
        }
    }

    private void buscarAtivoPorSimbolo() {
        System.out.println("\n--- Buscar Ativo por Símbolo ---");
        System.out.print("Digite o símbolo a ser pesquisado: ");
        String simbolo = scanner.nextLine().toUpperCase();

        Ativo ativo = service.buscarPorSimbolo(simbolo);
        if (ativo != null) {
            System.out.println("\nAtivo Encontrado:");
            System.out.println("ID: " + ativo.getIdAtivo());
            System.out.println("Tipo: " + ativo.getTipoAtivo());
            System.out.println("Símbolo: " + ativo.getSimbolo());
            System.out.println("Nome: " + ativo.getNomeAtivo());
            System.out.println("Descrição: " + ativo.getDescricao());
            System.out.println("Descrição Resumida (Polimorfismo): " + ativo.descricaoResumida());
        } else {
            System.out.println("Ativo não encontrado para o símbolo: " + simbolo);
        }
    }

    private void buscarAtivosPorTipo() {
        System.out.println("\n--- Buscar Ativos por Tipo ---");
        System.out.println("Selecione o tipo de ativo para pesquisa:");
        System.out.println("1. Ação");
        System.out.println("2. Criptomoeda");
        System.out.println("3. Fundo Imobiliário");
        System.out.print("Escolha: ");
        int tipoEscolha = -1;
        try {
            tipoEscolha = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Entrada inválida.");
            scanner.nextLine();
            return;
        }

        String tipoFiltro = "";
        switch (tipoEscolha) {
            case 1:
                tipoFiltro = "Acao";
                break;
            case 2:
                tipoFiltro = "Criptomoeda";
                break;
            case 3:
                tipoFiltro = "Fundo Imobiliario";
                break;
            default:
                System.out.println("Tipo inválido.");
                return;
        }

        List<Ativo> ativos = service.buscarPorTipo(tipoFiltro);
        if (ativos.isEmpty()) {
            System.out.println("Nenhum ativo encontrado para o tipo selecionado.");
            return;
        }

        System.out.printf("\n%-5s | %-15s | %-8s | %-25s | %-30s\n",
                "ID", "Tipo", "Símbolo", "Nome", "Descrição Resumida (Polimorfismo)");
        System.out.println("-----------------------------------------------------------------------------------------");
        for (Ativo ativo : ativos) {
            System.out.printf("%-5d | %-15s | %-8s | %-25s | %-30s\n",
                    ativo.getIdAtivo(),
                    ativo.getTipoAtivo(),
                    ativo.getSimbolo(),
                    ativo.getNomeAtivo(),
                    ativo.descricaoResumida());
        }
    }

    private void cadastrarAtivo() {
        System.out.println("\n--- Cadastrar Novo Ativo ---");
        System.out.println("Selecione o tipo de ativo:");
        System.out.println("1. Ação");
        System.out.println("2. Criptomoeda");
        System.out.println("3. Fundo Imobiliário");
        System.out.print("Escolha: ");
        int tipoEscolha = -1;
        try {
            tipoEscolha = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Entrada inválida. Cancelando cadastro.");
            scanner.nextLine();
            return;
        }

        Ativo ativo;
        switch (tipoEscolha) {
            case 1:
                ativo = new Acao();
                break;
            case 2:
                ativo = new Criptomoeda();
                break;
            case 3:
                ativo = new FundoImobiliario();
                break;
            default:
                System.out.println("Tipo inválido. Cancelando cadastro.");
                return;
        }

        System.out.print("Digite o Símbolo (ex: PETR4, BTC): ");
        ativo.setSimbolo(scanner.nextLine().toUpperCase());

        System.out.print("Digite o Nome do Ativo (ex: Petrobras PN): ");
        ativo.setNomeAtivo(scanner.nextLine());

        System.out.print("Digite a Descrição: ");
        ativo.setDescricao(scanner.nextLine());

        ativo.setDataListagem(LocalDate.now());

        service.inserir(ativo);
        System.out.println("Ativo cadastrado com sucesso! ID gerado: " + ativo.getIdAtivo());
    }

    private void atualizarAtivo() {
        System.out.println("\n--- Atualizar Ativo ---");
        System.out.print("Digite o ID do Ativo que deseja atualizar: ");
        int id = -1;
        try {
            id = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Erro: ID inválido.");
            scanner.nextLine();
            return;
        }

        Ativo ativo = service.buscarPorId(id);
        if (ativo == null) {
            System.out.println("Ativo não encontrado!");
            return;
        }

        System.out.println("Dados atuais: " + ativo.getNomeAtivo() + " (" + ativo.getDescricao() + ")");
        System.out.print("Digite o novo Nome (ou pressione Enter para manter): ");
        String novoNome = scanner.nextLine();
        if (!novoNome.trim().isEmpty()) {
            ativo.setNomeAtivo(novoNome);
        }

        System.out.print("Digite a nova Descrição (ou pressione Enter para manter): ");
        String novaDescricao = scanner.nextLine();
        if (!novaDescricao.trim().isEmpty()) {
            ativo.setDescricao(novaDescricao);
        }

        service.atualizar(ativo);
        System.out.println("Ativo atualizado com sucesso!");
    }

    private void deletarAtivo() {
        System.out.println("\n--- Deletar Ativo ---");
        System.out.print("Digite o ID do Ativo que deseja remover: ");
        int id = -1;
        try {
            id = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Erro: ID inválido.");
            scanner.nextLine();
            return;
        }

        Ativo ativo = service.buscarPorId(id);
        if (ativo == null) {
            System.out.println("Ativo não encontrado!");
            return;
        }

        System.out.println("Tem certeza que deseja deletar o ativo " + ativo.getSimbolo() + "? (S/N)");
        String confirmacao = scanner.nextLine().toUpperCase();

        if (confirmacao.equals("S")) {
            try {
                service.deletar(id);
                System.out.println("Ativo deletado com sucesso!");
            } catch (Exception e) {
                System.out.println("Erro: Não foi possível deletar o ativo. Pode haver transações ativas vinculadas a ele.");
            }
        } else {
            System.out.println("Operação cancelada.");
        }
    }
}
