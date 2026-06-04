package br.inatel.model;

public class FundoImobiliario extends Ativo {
    
    public FundoImobiliario() {
        this.setTipoAtivo("Fundo Imobiliario");
    }

    @Override
    public String descricaoResumida() {
        return "Fundo de Investimento Imobiliário: " + getSimbolo() + " - " + getNomeAtivo();
    }
}
