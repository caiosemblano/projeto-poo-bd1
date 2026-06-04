package br.inatel.model;

public class Acao extends Ativo {
    
    public Acao() {
        this.setTipoAtivo("Acao");
    }

    @Override
    public String descricaoResumida() {
        return "Ação (B3/BDR): " + getSimbolo() + " - " + getNomeAtivo();
    }
}
