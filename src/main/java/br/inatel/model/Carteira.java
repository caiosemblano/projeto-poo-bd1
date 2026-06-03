package br.inatel.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Carteira {
    private int idCarteira;
    private Investidor investidor;         // associação
    private String nomeCarteira;
    private LocalDate dataCriacao;
    private String descricao;
    private BigDecimal valorTotalInvestido;
    private List<CarteiraAtivo> posicoes; // composição
    private Objetivo objetivo;            // composição 1:1
}