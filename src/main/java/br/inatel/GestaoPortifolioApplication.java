package br.inatel;
import br.inatel.ui.MenuPrincipal;

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