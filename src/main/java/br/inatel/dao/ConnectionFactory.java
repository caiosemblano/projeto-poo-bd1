package br.inatel.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String URL = "jdbc:mysql://localhost:3307/GestaoPortifolio";
    private static final String USER = "root";
    private static final String PASSWORD = "root"; 

    /**
     * Estabelece e retorna a conexão com o banco de dados.
     * @return Connection objeto de conexão JDBC
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Erro ao conectar com o banco de dados: " + e.getMessage());
            throw new RuntimeException("Erro ao conectar com o banco de dados. Verifique se o MySQL está rodando e se as credenciais estão corretas.", e);
        }
    }
}
