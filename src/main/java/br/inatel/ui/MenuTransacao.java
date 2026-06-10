package br.inatel.ui;

public class MenuTransacao {

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n--- Menu de Transações ---");
            System.out.println("1. Comprar Ativo");
            System.out.println("2. Vender Ativo");
            System.out.println("3. Listar Transações");
            System.out.println("4. Voltar para o Menu Principal");
            System.out.print("Escolha uma opção: ");
           
            switch (opcao) {
                case 1:
                    comprarAtivo();
                    break;
                case 2:
                    venderAtivo();
                    break;
                case 3:
                    listarTransacoes();
                    break;
                case 4:
                    System.out.println("Voltando ao Menu Principal...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 4);
    }

}
