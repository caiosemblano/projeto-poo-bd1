package br.inatel;


public class GestaoPortifolioApplication {
    public static void main(String[] args) {
        
        GestaoPortifolioApplication app = new GestaoPortifolioApplication();
        app.run();

    }

    public void run() {
        MenuPrincipal menu = new MenuPrincipal();
        menu.exibirMenu();
    }
}