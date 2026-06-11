package br.inatel.ui;

import java.util.Scanner;

public class MenuObjetivo {

    Scanner s;

    public MenuObjetivo(Scanner scanner) {
        this.s = scanner;
    }

    public void exibirMenu() {
        int opcao = -1;
        do {
            System.out.println("\n--- Menu de Objetivos ---");
            System.out.println("1. Criar Objetivo");
            System.out.println("2. Listar Objetivos");
            System.out.println("3. Atualizar Objetivo");
            System.out.println("4. Excluir Objetivo");
            System.out.println("5. Voltar para o Menu Principal");
            System.out.print("Escolha uma opção: ");
            opcao = s.nextInt();

            switch (opcao) {
                case 1:
                    // criarObjetivo();
                    break;
                case 2:
                    // listarObjetivos();
                    break;
                case 3:
                    // atualizarObjetivo();
                    break;
                case 4:
                    // excluirObjetivo();
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
