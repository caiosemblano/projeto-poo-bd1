package br.inatel.ui;

import java.util.Scanner;

public class MenuInvestidor {
    Scanner s = new Scanner(System.in);

    public void exibirMenu() {
        int opcao = -1;
        do {
            System.out.println("\n--- Menu de Investidores ---");
            System.out.println("1. Criar Investidor");
            System.out.println("2. Listar Investidores");
            System.out.println("3. Atualizar Investidor");
            System.out.println("4. Excluir Investidor");
            System.out.println("5. Voltar para o Menu Principal");
            System.out.print("Escolha uma opção: ");
            opcao = s.nextInt();

            switch (opcao) {
                case 1:
                    // criarInvestidor();
                    break;
                case 2:
                    // listarInvestidores();
                    break;
                case 3:
                    // atualizarInvestidor();
                    break;
                case 4:
                    // excluirInvestidor();
                    break;
                case 5:
                    System.out.println("Voltando ao Menu Principal...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 5);
    }
}
