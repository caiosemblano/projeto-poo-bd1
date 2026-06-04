package br.inatel.model;

public class Criptomoeda extends Ativo {
    
    public Criptomoeda() {
        this.setTipoAtivo("Criptomoeda");
    }

    @Override
    public String descricaoResumida() {
        return "Criptoativo (Blockchain): " + getSimbolo() + " - " + getNomeAtivo();
    }
}
