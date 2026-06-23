package br.inatel.dao;

import br.inatel.model.Carteira;
import br.inatel.model.Investidor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarteiraDAO implements Repositorio<Carteira, Integer>{
    Connection connection = ConnectionFactory.getConnection();
    PreparedStatement pst;

    @Override
    public void inserir(Carteira carteira) {
        String sql = "INSERT INTO CARTEIRA (id_investidor, nome_carteira, data_criacao, descricao, valor_total_investido) VALUES (?, ?, ?, ?, ?)";
        try {
            pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pst.setInt(1, carteira.getInvestidor().getIdInvestidor());
            pst.setString(2, carteira.getNomeCarteira());
            pst.setDate(3, Date.valueOf(carteira.getDataCriacao()));
            pst.setString(4, carteira.getDescricao());
            pst.setBigDecimal(5, carteira.getValorTotalInvestido());
            pst.executeUpdate();

            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    carteira.setIdCarteira(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Carteira buscarPorId(Integer id_carteira) {
        String sql = "SELECT * FROM CARTEIRA WHERE id_carteira = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, id_carteira);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSetParaCarteira(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Carteira buscarPorNome(String nome_carteira) {
        String sql = "SELECT * FROM CARTEIRA WHERE nome_carteira = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, nome_carteira);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSetParaCarteira(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Carteira> listarTodos() {
        List<Carteira> carteiras = new ArrayList<>();
        String sql = "SELECT * FROM CARTEIRA";
        try {
            pst = connection.prepareStatement(sql);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    carteiras.add(mapearResultSetParaCarteira(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return carteiras;
    }

    public List<Carteira> buscarPorInvestidor(int idInvestidor) {
        List<Carteira> carteiras = new ArrayList<>();
        String sql = "SELECT * FROM CARTEIRA WHERE id_investidor = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, idInvestidor);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    carteiras.add(mapearResultSetParaCarteira(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return carteiras;
    }

    private Carteira mapearResultSetParaCarteira(ResultSet rs) throws SQLException {
        Carteira carteira = new Carteira();
        carteira.setIdCarteira(rs.getInt("id_carteira"));
        carteira.setNomeCarteira(rs.getString("nome_carteira"));
        carteira.setDataCriacao(rs.getDate("data_criacao").toLocalDate());
        carteira.setDescricao(rs.getString("descricao"));
        carteira.setValorTotalInvestido(rs.getBigDecimal("valor_total_investido"));

        Investidor investidor = new Investidor();
        investidor.setIdInvestidor(rs.getInt("id_investidor"));
        carteira.setInvestidor(investidor);

        CarteiraAtivoDAO caDAO = new CarteiraAtivoDAO();
        carteira.setPosicoes(caDAO.listarPorCarteira(carteira.getIdCarteira()));

        ObjetivoDAO oDAO = new ObjetivoDAO();
        carteira.setObjetivo(oDAO.buscarPorCarteira(carteira.getIdCarteira()));

        return carteira;
    }

    @Override
    public void atualizar(Carteira carteira) {
        String sql = "UPDATE CARTEIRA SET id_investidor = ?, nome_carteira = ?, data_criacao = ?, descricao = ?, valor_total_investido = ? WHERE id_carteira = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, carteira.getInvestidor().getIdInvestidor());
            pst.setString(2, carteira.getNomeCarteira());
            pst.setDate(3, Date.valueOf(carteira.getDataCriacao()));
            pst.setString(4, carteira.getDescricao());
            pst.setBigDecimal(5, carteira.getValorTotalInvestido());
            pst.setInt(6, carteira.getIdCarteira());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletar(Integer id_carteira) {
        String sql = "DELETE FROM CARTEIRA WHERE id_carteira = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, id_carteira);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
