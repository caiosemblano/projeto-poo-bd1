package br.inatel.dao;

import br.inatel.model.Ativo;
import br.inatel.model.HistoricoPreco;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoricoPrecoDAO implements Repositorio<HistoricoPreco, Integer> {
    Connection connection = ConnectionFactory.getConnection();
    PreparedStatement pst;

    @Override
    public void inserir(HistoricoPreco historico) {
        String sql = "INSERT INTO HISTORICO_PRECO (id_ativo, data, preco_abertura, preco_fechamento, preco_maximo, preco_minimo, volume_negociado) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, historico.getAtivo().getIdAtivo());
            pst.setDate(2, Date.valueOf(historico.getData()));
            pst.setBigDecimal(3, historico.getPrecoAbertura());
            pst.setBigDecimal(4, historico.getPrecoFechamento());
            pst.setBigDecimal(5, historico.getPrecoMaximo());
            pst.setBigDecimal(6, historico.getPrecoMinimo());
            pst.setBigDecimal(7, historico.getVolumeNegociado());
            pst.executeUpdate();

            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    historico.setIdHistorico(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public HistoricoPreco buscarPorId(Integer id) {
        String sql = "SELECT * FROM HISTORICO_PRECO WHERE id_historico = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSetParaHistorico(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<HistoricoPreco> listarTodos() {
        List<HistoricoPreco> historicos = new ArrayList<>();
        String sql = "SELECT * FROM HISTORICO_PRECO";
        try {
            pst = connection.prepareStatement(sql);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    historicos.add(mapearResultSetParaHistorico(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return historicos;
    }

    @Override
    public void atualizar(HistoricoPreco historico) {
        String sql = "UPDATE HISTORICO_PRECO SET id_ativo = ?, data = ?, preco_abertura = ?, preco_fechamento = ?, preco_maximo = ?, preco_minimo = ?, volume_negociado = ? WHERE id_historico = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, historico.getAtivo().getIdAtivo());
            pst.setDate(2, Date.valueOf(historico.getData()));
            pst.setBigDecimal(3, historico.getPrecoAbertura());
            pst.setBigDecimal(4, historico.getPrecoFechamento());
            pst.setBigDecimal(5, historico.getPrecoMaximo());
            pst.setBigDecimal(6, historico.getPrecoMinimo());
            pst.setBigDecimal(7, historico.getVolumeNegociado());
            pst.setInt(8, historico.getIdHistorico());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletar(Integer id) {
        String sql = "DELETE FROM HISTORICO_PRECO WHERE id_historico = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<HistoricoPreco> buscarPorData(java.time.LocalDate data) {
        List<HistoricoPreco> historicos = new ArrayList<>();
        String sql = "SELECT * FROM HISTORICO_PRECO WHERE data = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setDate(1, Date.valueOf(data));
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    historicos.add(mapearResultSetParaHistorico(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return historicos;
    }

    private HistoricoPreco mapearResultSetParaHistorico(ResultSet rs) throws SQLException {
        HistoricoPreco historico = new HistoricoPreco();
        historico.setIdHistorico(rs.getInt("id_historico"));

        AtivoDAO ativoDAO = new AtivoDAO();
        Ativo ativo = ativoDAO.buscarPorId(rs.getInt("id_ativo"));
        ativo.setIdAtivo(rs.getInt("id_ativo"));
        historico.setAtivo(ativo);
        
        if (rs.getDate("data") != null) {
            historico.setData(rs.getDate("data").toLocalDate());
        }
        historico.setPrecoAbertura(rs.getBigDecimal("preco_abertura"));
        historico.setPrecoFechamento(rs.getBigDecimal("preco_fechamento"));
        historico.setPrecoMaximo(rs.getBigDecimal("preco_maximo"));
        historico.setPrecoMinimo(rs.getBigDecimal("preco_minimo"));
        historico.setVolumeNegociado(rs.getBigDecimal("volume_negociado"));
        return historico;
    }
}
