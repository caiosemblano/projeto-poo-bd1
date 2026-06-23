package br.inatel.dao;

import br.inatel.model.Carteira;
import br.inatel.model.Objetivo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ObjetivoDAO implements Repositorio<Objetivo, Integer> {
    Connection connection = ConnectionFactory.getConnection();
    PreparedStatement pst;

    @Override
    public void inserir(Objetivo objetivo) {
        String sql = "INSERT INTO OBJETIVO (id_carteira, meta_rentabilidade, prazo_meses, data_criacao, status, descricao) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, objetivo.getCarteira().getIdCarteira());
            pst.setBigDecimal(2, objetivo.getMetaRentabilidade());
            pst.setInt(3, objetivo.getPrazoMeses());
            pst.setDate(4, Date.valueOf(objetivo.getDataCriacao()));
            pst.setString(5, objetivo.getStatus());
            pst.setString(6, objetivo.getDescricao());
            pst.executeUpdate();

            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    objetivo.setIdObjetivo(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Objetivo buscarPorId(Integer id) {
        String sql = "SELECT * FROM OBJETIVO WHERE id_objetivo = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSetParaObjetivo(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Objetivo> listarTodos() {
        List<Objetivo> objetivos = new ArrayList<>();
        String sql = "SELECT * FROM OBJETIVO";
        try {
            pst = connection.prepareStatement(sql);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    objetivos.add(mapearResultSetParaObjetivo(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return objetivos;
    }

    @Override
    public void atualizar(Objetivo objetivo) {
        String sql = "UPDATE OBJETIVO SET id_carteira = ?, meta_rentabilidade = ?, prazo_meses = ?, status = ?, descricao = ? WHERE id_objetivo = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, objetivo.getCarteira().getIdCarteira());
            pst.setBigDecimal(2, objetivo.getMetaRentabilidade());
            pst.setInt(3, objetivo.getPrazoMeses());
            pst.setString(4, objetivo.getStatus());
            pst.setString(5, objetivo.getDescricao());
            pst.setInt(6, objetivo.getIdObjetivo());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletar(Integer id) {
        String sql = "DELETE FROM OBJETIVO WHERE id_objetivo = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Objetivo> buscarPorStatus(String status) {
        List<Objetivo> objetivos = new ArrayList<>();
        String sql = "SELECT * FROM OBJETIVO WHERE status = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, status);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    objetivos.add(mapearResultSetParaObjetivo(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return objetivos;
    }

    private Objetivo mapearResultSetParaObjetivo(ResultSet rs) throws SQLException {
        Objetivo objetivo = new Objetivo();
        objetivo.setIdObjetivo(rs.getInt("id_objetivo"));
        
        Carteira carteira = new Carteira();
        carteira.setIdCarteira(rs.getInt("id_carteira"));
        objetivo.setCarteira(carteira);
        
        objetivo.setMetaRentabilidade(rs.getBigDecimal("meta_rentabilidade"));
        objetivo.setPrazoMeses(rs.getInt("prazo_meses"));
        if (rs.getDate("data_criacao") != null) {
            objetivo.setDataCriacao(rs.getDate("data_criacao").toLocalDate());
        }
        objetivo.setStatus(rs.getString("status"));
        objetivo.setDescricao(rs.getString("descricao"));
        return objetivo;
    }

    public Objetivo buscarPorCarteira(int idCarteira) {
        String sql = "SELECT * FROM OBJETIVO WHERE id_carteira = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, idCarteira);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSetParaObjetivo(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
