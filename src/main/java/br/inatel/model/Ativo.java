package br.inatel.model;

import java.time.LocalDate;

public abstract class Ativo {
    private int idAtivo;
    private String tipoAtivo;
    private String simbolo;
    private String nomeAtivo;
    private String descricao;
    private LocalDate dataListagem;

    public int getIdAtivo() {
        return idAtivo;
    }

    public void setIdAtivo(int idAtivo) {
        this.idAtivo = idAtivo;
    }

    public String getTipoAtivo() {
        return tipoAtivo;
    }

    public void setTipoAtivo(String tipoAtivo) {
        this.tipoAtivo = tipoAtivo;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getNomeAtivo() {
        return nomeAtivo;
    }

    public void setNomeAtivo(String nomeAtivo) {
        this.nomeAtivo = nomeAtivo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataListagem() {
        return dataListagem;
    }

    public void setDataListagem(LocalDate dataListagem) {
        this.dataListagem = dataListagem;
    }

    public abstract String descricaoResumida();

}
