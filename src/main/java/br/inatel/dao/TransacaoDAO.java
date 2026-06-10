package br.inatel.dao;

import br.inatel.model.Acao;
import br.inatel.model.Ativo;
import br.inatel.model.Carteira;
import br.inatel.model.Transacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransacaoDAO implements Repositorio<Transacao, Integer> {
    Connection connection = ConnectionFactory.getConnection();
    PreparedStatement pst;

    @Override
    public void inserir(Transacao transacao) {
        String sql = "INSERT INTO transacao (id_carteira, id_ativo, tipo_transacao, quantidade, preco_unitario, valor_total, data_transacao, comissao) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, transacao.getCarteira().getIdCarteira());
            pst.setInt(2, transacao.getAtivo().getIdAtivo());
            pst.setString(3, transacao.getTipoTransacao());
            pst.setBigDecimal(4, transacao.getQuantidade());
            pst.setBigDecimal(5, transacao.getPrecoUnitario());
            pst.setBigDecimal(6, transacao.getValorTotal());
            pst.setDate(7, Date.valueOf(transacao.getDataTransacao()));
            pst.setBigDecimal(8, transacao.getComissao());
            pst.executeUpdate();

            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    transacao.setIdTransacao(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Transacao buscarPorId(Integer id) {
        String sql = "SELECT * FROM transacao WHERE id_transacao = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSetParaTransacao(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Transacao> listarTodos() {
        List<Transacao> transacoes = new ArrayList<>();
        String sql = "SELECT * FROM transacao";
        try {
            pst = connection.prepareStatement(sql);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    transacoes.add(mapearResultSetParaTransacao(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transacoes;
    }

    @Override
    public void atualizar(Transacao transacao) {
        String sql = "UPDATE transacao SET id_carteira = ?, id_ativo = ?, tipo_transacao = ?, quantidade = ?, preco_unitario = ?, valor_total = ?, data_transacao = ?, comissao = ? WHERE id_transacao = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, transacao.getCarteira().getIdCarteira());
            pst.setInt(2, transacao.getAtivo().getIdAtivo());
            pst.setString(3, transacao.getTipoTransacao());
            pst.setBigDecimal(4, transacao.getQuantidade());
            pst.setBigDecimal(5, transacao.getPrecoUnitario());
            pst.setBigDecimal(6, transacao.getValorTotal());
            pst.setDate(7, Date.valueOf(transacao.getDataTransacao()));
            pst.setBigDecimal(8, transacao.getComissao());
            pst.setInt(9, transacao.getIdTransacao());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletar(Integer id) {
        String sql = "DELETE FROM transacao WHERE id_transacao = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Transacao> buscarPorTipo(String tipoTransacao) {
        List<Transacao> transacoes = new ArrayList<>();
        String sql = "SELECT * FROM transacao WHERE tipo_transacao = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, tipoTransacao);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    transacoes.add(mapearResultSetParaTransacao(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transacoes;
    }

    public List<Transacao> buscarPorIntervaloData(java.time.LocalDate inicio, java.time.LocalDate fim) {
        List<Transacao> transacoes = new ArrayList<>();
        String sql = "SELECT * FROM transacao WHERE data_transacao BETWEEN ? AND ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setDate(1, Date.valueOf(inicio));
            pst.setDate(2, Date.valueOf(fim));
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    transacoes.add(mapearResultSetParaTransacao(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transacoes;
    }

    private Transacao mapearResultSetParaTransacao(ResultSet rs) throws SQLException {
        Transacao transacao = new Transacao();
        transacao.setIdTransacao(rs.getInt("id_transacao"));

        Carteira carteira = new Carteira();
        carteira.setIdCarteira(rs.getInt("id_carteira"));
        transacao.setCarteira(carteira);

        Ativo ativo = new Acao();
        ativo.setIdAtivo(rs.getInt("id_ativo"));
        transacao.setAtivo(ativo);

        transacao.setTipoTransacao(rs.getString("tipo_transacao"));
        transacao.setQuantidade(rs.getBigDecimal("quantidade"));
        transacao.setPrecoUnitario(rs.getBigDecimal("preco_unitario"));
        transacao.setValorTotal(rs.getBigDecimal("valor_total"));
        if (rs.getDate("data_transacao") != null) {
            transacao.setDataTransacao(rs.getDate("data_transacao").toLocalDate());
        }
        transacao.setComissao(rs.getBigDecimal("comissao"));

        return transacao;
    }
}
