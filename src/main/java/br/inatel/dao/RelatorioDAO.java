package br.inatel.dao;

import java.sql.*;

public class RelatorioDAO {

    public void relatorioResumoPortfolio() {
        String sql = "SELECT * FROM vw_resumo_portfolio";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    public void relatorioHistoricoTransacoes(int idInvestidor) {
        String sql = "SELECT t.data_transacao, t.tipo_transacao, a.simbolo, c.nome_carteira, i.nome " +
                "FROM TRANSACAO t " +
                "JOIN CARTEIRA c ON t.id_carteira = c.id_carteira " +
                "JOIN INVESTIDOR i ON c.id_investidor = i.id_investidor " +
                "JOIN ATIVO a ON t.id_ativo = a.id_ativo " +
                "WHERE i.id_investidor = ? " +
                "ORDER BY t.data_transacao DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idInvestidor);
            try (ResultSet rs = stmt.executeQuery()) {
            }
        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }
}
