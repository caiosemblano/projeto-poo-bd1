package br.inatel.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDAO {

    public List<String[]> relatorioResumoPortfolio() {
        List<String[]> dados = new ArrayList<>();
        String sql = "SELECT * FROM vw_resumo_portfolio";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                dados.add(new String[]{
                        rs.getString("nome_carteira"),
                        rs.getString("investidor"),
                        rs.getString("ativo"),
                        String.valueOf(rs.getDouble("quantidade")),
                        String.valueOf(rs.getDouble("valor_atual")),
                        String.valueOf(rs.getDouble("montante_total_ativo"))
                });
            }
        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
        return dados;
    }

    public List<String[]> relatorioHistoricoTransacoes(int idInvestidor) {
        List<String[]> dados = new ArrayList<>();
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
                while (rs.next()) {
                    dados.add(new String[]{
                            String.valueOf(rs.getDate("data_transacao")),
                            rs.getString("tipo_transacao"),
                            rs.getString("simbolo"),
                            rs.getString("nome_carteira"),
                            rs.getString("nome")
                    });
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
        return dados;
    }

    public List<String[]> relatorioObjetivosCarteira() {
        List<String[]> dados = new ArrayList<>();
        String sql = "SELECT o.descricao AS objetivo, o.meta_rentabilidade, o.prazo_meses, o.status AS status_objetivo, " +
                "c.nome_carteira, c.valor_total_investido, i.nome AS investidor " +
                "FROM OBJETIVO o " +
                "JOIN CARTEIRA c ON o.id_carteira = c.id_carteira " +
                "JOIN INVESTIDOR i ON c.id_investidor = i.id_investidor";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                dados.add(new String[]{
                        rs.getString("objetivo"),
                        String.valueOf(rs.getDouble("meta_rentabilidade")),
                        String.valueOf(rs.getInt("prazo_meses")),
                        rs.getString("status_objetivo"),
                        rs.getString("nome_carteira"),
                        String.valueOf(rs.getDouble("valor_total_investido")),
                        rs.getString("investidor")
                });
            }
        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
        return dados;
    }

    public List<String[]> relatorioComparativoPreco(int idCarteira) {
        List<String[]> dados = new ArrayList<>();
        String sql = "SELECT a.simbolo, a.nome_ativo, ca.quantidade, ca.valor_atual AS preco_atual, " +
                "hp.data AS data_hist, hp.preco_fechamento AS preco_hist, (ca.valor_atual - hp.preco_fechamento) AS variacao " +
                "FROM CARTEIRA_ATIVO ca " +
                "JOIN ATIVO a ON ca.id_ativo = a.id_ativo " +
                "JOIN HISTORICO_PRECO hp ON a.id_ativo = hp.id_ativo " +
                "WHERE ca.id_carteira = ? " +
                "ORDER BY hp.data DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCarteira);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    dados.add(new String[]{
                            rs.getString("simbolo"),
                            rs.getString("nome_ativo"),
                            String.valueOf(rs.getDouble("quantidade")),
                            String.valueOf(rs.getDouble("preco_atual")),
                            String.valueOf(rs.getDate("data_hist")),
                            String.valueOf(rs.getDouble("preco_hist")),
                            String.valueOf(rs.getDouble("variacao"))
                    });
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
        return dados;
    }

    public List<String[]> relatorioComprasEVendas() {
        List<String[]> dados = new ArrayList<>();
        String sql = "SELECT i.nome AS investidor, a.simbolo, " +
                "SUM(CASE WHEN t.tipo_transacao = 'Compra' THEN t.valor_total ELSE 0 END) AS total_comprado, " +
                "SUM(CASE WHEN t.tipo_transacao = 'Venda' THEN t.valor_total ELSE 0 END) AS total_vendido " +
                "FROM TRANSACAO t " +
                "JOIN CARTEIRA c ON t.id_carteira = c.id_carteira " +
                "JOIN INVESTIDOR i ON c.id_investidor = i.id_investidor " +
                "JOIN ATIVO a ON t.id_ativo = a.id_ativo " +
                "GROUP BY i.id_investidor, a.id_ativo";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                dados.add(new String[]{
                        rs.getString("investidor"),
                        rs.getString("simbolo"),
                        String.valueOf(rs.getDouble("total_comprado")),
                        String.valueOf(rs.getDouble("total_vendido"))
                });
            }
        } catch (SQLException e) {
            System.err.println("Erro: " + e.getMessage());
        }
        return dados;
    }
}
