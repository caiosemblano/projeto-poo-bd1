package br.inatel.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Objetivo {
    private int idObjetivo;
    private Carteira carteira;
    private BigDecimal metaRentabilidade;
    private int prazoMeses;
    private LocalDate dataCriacao;
    private String status;
    private String descricao;

    public Objetivo() {}

    public int getIdObjetivo() {
        return idObjetivo;
    }

    public void setIdObjetivo(int idObjetivo) {
        this.idObjetivo = idObjetivo;
    }

    public Carteira getCarteira() {
        return carteira;
    }

    public void setCarteira(Carteira carteira) {
        this.carteira = carteira;
    }

    public BigDecimal getMetaRentabilidade() {
        return metaRentabilidade;
    }

    public void setMetaRentabilidade(BigDecimal metaRentabilidade) {
        this.metaRentabilidade = metaRentabilidade;
    }

    public int getPrazoMeses() {
        return prazoMeses;
    }

    public void setPrazoMeses(int prazoMeses) {
        this.prazoMeses = prazoMeses;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
