package br.inatel.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class HistoricoPreco {
    private int idHistorico;
    private Ativo ativo;
    private LocalDate data;
    private BigDecimal precoAbertura;
    private BigDecimal precoFechamento;
    private BigDecimal precoMaximo;
    private BigDecimal precoMinimo;
    private BigDecimal volumeNegociado;

    public HistoricoPreco() {}

    public int getIdHistorico() {
        return idHistorico;
    }

    public void setIdHistorico(int idHistorico) {
        this.idHistorico = idHistorico;
    }

    public Ativo getAtivo() {
        return ativo;
    }

    public void setAtivo(Ativo ativo) {
        this.ativo = ativo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public BigDecimal getPrecoAbertura() {
        return precoAbertura;
    }

    public void setPrecoAbertura(BigDecimal precoAbertura) {
        this.precoAbertura = precoAbertura;
    }

    public BigDecimal getPrecoFechamento() {
        return precoFechamento;
    }

    public void setPrecoFechamento(BigDecimal precoFechamento) {
        this.precoFechamento = precoFechamento;
    }

    public BigDecimal getPrecoMaximo() {
        return precoMaximo;
    }

    public void setPrecoMaximo(BigDecimal precoMaximo) {
        this.precoMaximo = precoMaximo;
    }

    public BigDecimal getPrecoMinimo() {
        return precoMinimo;
    }

    public void setPrecoMinimo(BigDecimal precoMinimo) {
        this.precoMinimo = precoMinimo;
    }

    public BigDecimal getVolumeNegociado() {
        return volumeNegociado;
    }

    public void setVolumeNegociado(BigDecimal volumeNegociado) {
        this.volumeNegociado = volumeNegociado;
    }
}
