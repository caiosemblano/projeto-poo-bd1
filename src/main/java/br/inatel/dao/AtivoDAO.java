    package br.inatel.dao;

    import br.inatel.model.Ativo;
    import br.inatel.model.Acao;
    import br.inatel.model.Criptomoeda;
    import br.inatel.model.FundoImobiliario;
    import java.sql.*;
    import java.util.ArrayList;
    import java.util.List;

    public class AtivoDAO implements Repositorio<Ativo, Integer> {

        @Override
        public void inserir(Ativo ativo) {
            String sql = "INSERT INTO ativo (tipo_ativo, simbolo, nome_ativo, descricao, data_listagem) VALUES (?, ?, ?, ?, ?)";
            try (Connection connection = ConnectionFactory.getConnection();
                 PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                pst.setString(1, ativo.getTipoAtivo());
                pst.setString(2, ativo.getSimbolo());
                pst.setString(3, ativo.getNomeAtivo());
                pst.setString(4, ativo.getDescricao());
                pst.setDate(5, Date.valueOf(ativo.getDataListagem()));
                pst.executeUpdate();

                try (ResultSet rs = pst.getGeneratedKeys()) {
                    if (rs.next()) {
                        ativo.setIdAtivo(rs.getInt(1));
                    }
                }
            } catch (SQLException e) {
                System.out.println("Erro ao inserir ativo: " + e.getMessage());
            }
        }
        @Override
        public Ativo buscarPorId(Integer id) {
            String sql = "SELECT * FROM ativo WHERE id_ativo = ?";
            try (Connection connection = ConnectionFactory.getConnection();
                 PreparedStatement pst = connection.prepareStatement(sql)){
                pst.setInt(1, id);

                try (ResultSet rs = pst.executeQuery()) {
                    if(rs.next()) {
                        return mapearativo(rs);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return null;
        }

        @Override
        public List<Ativo> listarTodos() {
        }

        @Override
        public void atualizar(Ativo ativo) {
            String sql = "UPDATE ativo SET nome_ativo=?, descricao=? WHERE id=?";
            try(Connection connection = ConnectionFactory.getConnection();
                PreparedStatement pst = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)){
                pst.setString(1, ativo.getTipoAtivo());
                pst.setString(2, ativo.getDescricao());
                pst.setInt(3, ativo.getIdAtivo());
                pst.executeUpdate();
            }catch (SQLException e){
                System.out.println("Erro ao atualizar ativo: " + e.getMessage());
            }
        }

        @Override
        public void deletar(Integer integer) {

        }
        private Ativo mapearativo(ResultSet rs) throws SQLException {
            String tipo = rs.getString("tipo_ativo");
            Ativo ativo = switch (tipo) {
                case "Acao" -> new Acao();
                case "Criptomoeda" -> new Criptomoeda();
                case "Fundo Imobiliario" -> new FundoImobiliario();
                default -> throw new IllegalArgumentException("Tipo desconhecido: " + tipo);
            };
            ativo.setIdAtivo(rs.getInt("id_ativo"));
            ativo.setSimbolo(rs.getString("simbolo"));
            ativo.setNomeAtivo(rs.getString("nome_ativo"));
            ativo.setDescricao(rs.getString("descricao"));
            ativo.setDataListagem(rs.getDate("data_listagem").toLocalDate());

            return ativo;
        }

    }


