package br.inatel.ui;

import br.inatel.dao.AtivoDAO;
import br.inatel.service.AtivoService;

import java.util.Scanner;

public class MenuPrincipal {

    Scanner scanner = new Scanner(System.in);

    public void exibirMenu() {
        Scanner scanner = new Scanner(System.in);

        AtivoDAO ativoDAO = new AtivoDAO();
        AtivoService ativoService = new AtivoService(ativoDAO);
        MenuAtivo menuAtivo = new MenuAtivo(scanner, ativoService);

        int opcao = -1;

        do {
            System.out.println("\n=== GESTÃO DE PORTFÓLIO DE INVESTIMENTOS ===");
            System.out.println("1. Investidores");
            System.out.println("2. Carteiras");
            System.out.println("3. Ativos");
            System.out.println("4. Transações");
            System.out.println("5. Histórico de Preços");
            System.out.println("6. Objetivos");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
            }catch(Exception e) {
                System.out.println("Erro de entrada, tente novamente seguindo as opções válidas: ");
                scanner.nextLine();
                opcao = -1;
            }

                switch (opcao) {
                    case 1:
                        MenuInvestidor investidor = new MenuInvestidor();
                        //investidor.exibirMenu();
                        break;

                    case 2:
                        MenuCarteira carteira = new MenuCarteira();
                        //carteira.exibirMenu();
                        break;

                    case 3:
                        menuAtivo.exibirMenu();
                        break;

                    case 4:
                        MenuTransacao transacao = new MenuTransacao();
                        transacao.exibirMenu();
                        break;

                    case 5:
                        MenuHistorico historico = new MenuHistorico();
                        //historico.exibirMenu();
                        break;

                    case 6:
                        MenuObjetivo objetivo = new MenuObjetivo();
                        //objetivo.exibirMenu();
                        break;

                    case 0:
                        System.out.println("Saindo do sistema... Obrigado!");
                        break;

                    default:
                        System.out.println("Opção inválida!");
                }

            } while (opcao != 0) ;
    }
}