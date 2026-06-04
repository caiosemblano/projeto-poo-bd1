package br.inatel.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Carteira {
    private int idCarteira;
    private Investidor investidor;
    private String nomeCarteira;
    private LocalDate dataCriacao;
    private String descricao;
    private BigDecimal valorTotalInvestido;
    
    private List<CarteiraAtivo> posicoes = new ArrayList<>();
    private Objetivo objetivo;

    public Carteira() {}

    public int getIdCarteira() {
        return idCarteira;
    }

    public void setIdCarteira(int idCarteira) {
        this.idCarteira = idCarteira;
    }

    public Investidor getInvestidor() {
        return investidor;
    }

    public void setInvestidor(Investidor investidor) {
        this.investidor = investidor;
    }

    public String getNomeCarteira() {
        return nomeCarteira;
    }

    public void setNomeCarteira(String nomeCarteira) {
        this.nomeCarteira = nomeCarteira;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValorTotalInvestido() {
        return valorTotalInvestido;
    }

    public void setValorTotalInvestido(BigDecimal valorTotalInvestido) {
        this.valorTotalInvestido = valorTotalInvestido;
    }

    public List<CarteiraAtivo> getPosicoes() {
        return posicoes;
    }

    public void setPosicoes(List<CarteiraAtivo> posicoes) {
        this.posicoes = posicoes;
    }

    public Objetivo getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(Objetivo objetivo) {
        this.objetivo = objetivo;
    }
}