package br.inatel.dao;

import br.inatel.model.Investidor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvestidorDAO implements Repositorio<Investidor, Integer> {
    Connection connection = ConnectionFactory.getConnection();
    PreparedStatement pst;

    @Override
    public void inserir(Investidor investidor) {
        String sql = "INSERT INTO INVESTIDOR (nome, email, telefone, senha, data_cadastro, status) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, investidor.getNome());
            pst.setString(2, investidor.getEmail());
            pst.setString(3, investidor.getTelefone());
            pst.setString(4, investidor.getSenha());
            pst.setDate(5, Date.valueOf(investidor.getDataCadastro()));
            pst.setString(6, investidor.getStatus());
            pst.executeUpdate();

            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    investidor.setIdInvestidor(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Investidor buscarPorId(Integer id) {
        String sql = "SELECT * FROM INVESTIDOR WHERE id_investidor = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSetParaInvestidor(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Investidor> listarTodos() {
        List<Investidor> investidores = new ArrayList<>();
        String sql = "SELECT * FROM INVESTIDOR";
        try {
            pst = connection.prepareStatement(sql);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    investidores.add(mapearResultSetParaInvestidor(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return investidores;
    }

    @Override
    public void atualizar(Investidor investidor) {
        String sql = "UPDATE INVESTIDOR SET nome = ?, email = ?, telefone = ?, senha = ?, status = ? WHERE id_investidor = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, investidor.getNome());
            pst.setString(2, investidor.getEmail());
            pst.setString(3, investidor.getTelefone());
            pst.setString(4, investidor.getSenha());
            pst.setString(5, investidor.getStatus());
            pst.setInt(6, investidor.getIdInvestidor());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletar(Integer id) {
        String sql = "DELETE FROM INVESTIDOR WHERE id_investidor = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Investidor buscarPorEmail(String email) {
        String sql = "SELECT * FROM INVESTIDOR WHERE email = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, email);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSetParaInvestidor(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Investidor> buscarPorNome(String nome) {
        List<Investidor> investidores = new ArrayList<>();
        String sql = "SELECT * FROM INVESTIDOR WHERE nome LIKE ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, "%" + nome + "%");
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    investidores.add(mapearResultSetParaInvestidor(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return investidores;
    }

    public void inativar(Integer id) {
        String sql = "CALL sp_inativar_investidor(?)";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            pst.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Investidor mapearResultSetParaInvestidor(ResultSet rs) throws SQLException {
        Investidor investidor = new Investidor();
        investidor.setIdInvestidor(rs.getInt("id_investidor"));
        investidor.setNome(rs.getString("nome"));
        investidor.setEmail(rs.getString("email"));
        investidor.setTelefone(rs.getString("telefone"));
        investidor.setSenha(rs.getString("senha"));
        if (rs.getDate("data_cadastro") != null) {
            investidor.setDataCadastro(rs.getDate("data_cadastro").toLocalDate());
        }
        investidor.setStatus(rs.getString("status"));

        CarteiraDAO carteiraDAO = new CarteiraDAO();
        investidor.setCarteiras(carteiraDAO.buscarPorInvestidor(investidor.getIdInvestidor()));

        return investidor;
    }
}
