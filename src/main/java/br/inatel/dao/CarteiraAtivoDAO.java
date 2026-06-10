package br.inatel.dao;

import br.inatel.model.CarteiraAtivo;
import br.inatel.model.Carteira;
import br.inatel.model.Ativo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarteiraAtivoDAO implements Repositorio<CarteiraAtivo, Integer>{


    @Override
    public void inserir(CarteiraAtivo carteiraAtivo) {
        String sql = "INSERT INTO CARTEIRA_ATIVO (id_carteira, id_ativo, quantidade, valor_atual) VALUES (?, ?, ?, ?)";
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement pst = connection.prepareStatement(sql)){
            pst.setInt(1, carteiraAtivo.getCarteira().getIdCarteira());
            pst.setInt(2, carteiraAtivo.getAtivo().getIdAtivo());
            pst.setBigDecimal(3, carteiraAtivo.getQuantidade());
            pst.setBigDecimal(4, carteiraAtivo.getValorAtual());
            pst.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public CarteiraAtivo buscarPorId(Integer id) {
        throw new UnsupportedOperationException("Chave composta. Use buscarPorCarteiraEAtivo.");
    }


    public CarteiraAtivo buscarPorCarteiraEAtivo(int idCarteira, int idAtivo) {
        String sql = "SELECT * FROM CARTEIRA_ATIVO WHERE id_carteira = ? AND id_ativo = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, idCarteira);
            pst.setInt(2, idAtivo);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return mapearCarteiraAtivo(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

        public List<CarteiraAtivo> listarPorCarteira(int idCarteira) {
            List<CarteiraAtivo> posicaocarteira = new ArrayList<>();
            String sql = "SELECT * FROM CARTEIRA_ATIVO WHERE id_carteira = ?";

            try (Connection connection = ConnectionFactory.getConnection();
                 PreparedStatement pst = connection.prepareStatement(sql)) {

                pst.setInt(1, idCarteira);
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        posicaocarteira.add(mapearCarteiraAtivo(rs));
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return posicaocarteira;
        }

        public void deletarPorCarteiraEAtivo(int idCarteira, int idAtivo) {
            String sql = "DELETE FROM CARTEIRA_ATIVO WHERE id_ativo=? AND id_carteira=? ";
            try(Connection connection = ConnectionFactory.getConnection();
                PreparedStatement pst = connection.prepareStatement(sql)){
                pst.setInt(1, idAtivo);
                pst.setInt(2,idCarteira);
                pst.executeUpdate();
            }catch(SQLException e){
                throw new RuntimeException(e);
            }

        }

    @Override
    public List<CarteiraAtivo> listarTodos() {
        List<CarteiraAtivo> carteirasativo = new ArrayList<>();
        String sql = "SELECT * FROM CARTEIRA_ATIVO";
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery()){
            while (rs.next()) {
                carteirasativo.add(mapearCarteiraAtivo(rs));
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return carteirasativo;
    }

    @Override
    public void atualizar(CarteiraAtivo carteiraAtivo) {
        String sql = "UPDATE CARTEIRA_ATIVO SET quantidade = ?, valor_atual = ? WHERE id_carteira = ? AND id_ativo = ?";
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement pst = connection.prepareStatement(sql)){
            pst.setBigDecimal(1, carteiraAtivo.getQuantidade());
            pst.setBigDecimal(2, carteiraAtivo.getValorAtual());
            pst.setInt(3, carteiraAtivo.getCarteira().getIdCarteira());
            pst.setInt(4, carteiraAtivo.getAtivo().getIdAtivo());
            pst.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deletar(Integer id) {
        String sql = "DELETE FROM CARTEIRA_ATIVO WHERE id_ativo = ?";
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement pst = connection.prepareStatement(sql)){
            pst.setInt(1, id);
            pst.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }
    private CarteiraAtivo mapearCarteiraAtivo(ResultSet rs) throws SQLException {
        CarteiraAtivo carteiraativo = new CarteiraAtivo();
        carteiraativo.setQuantidade(rs.getBigDecimal("quantidade"));
        carteiraativo.setValorAtual(rs.getBigDecimal("valor_atual"));

        Carteira carteira = new Carteira();
        carteira.setIdCarteira(rs.getInt("id_carteira"));
        carteiraativo.setCarteira(carteira);

        int idAtivo = rs.getInt("id_ativo");

        AtivoDAO ativoDAO = new AtivoDAO();
        Ativo ativo = ativoDAO.buscarPorId(idAtivo);

        carteiraativo.setAtivo(ativo);

        return carteiraativo;
    }
}
