package br.inatel.dao;

import java.sql.Connection;
import java.sql.Statement;

public class DatabasePopulatorDAO {

    public static void popularBanco() {
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("INSERT IGNORE INTO INVESTIDOR (id_investidor, nome, email, telefone, senha, data_cadastro, status) VALUES " +
                    "(1, 'Vaporeon', 'eevee@email.com', '11999999999', 'hash123', '2023-01-10', 'Ativo'), " +
                    "(2, 'Mewtwo', 'mewone@email.com', '11988888888', 'hash456', '2023-02-15', 'Ativo'), " +
                    "(3, 'Vampeta', 'pepeta@email.com', '11977777777', 'hash789', '2023-03-20', 'Inativo'), " +
                    "(4, 'umbigo', 'doisbigo@email.com', '11966666666', 'hash321', '2023-04-25', 'Ativo'), " +
                    "(5, 'Weverson', 'zoio@email.com', '11955555555', 'Alekhash654', '2023-05-30', 'Ativo'), " +
                    "(6, 'Ana Silva', 'ana.silva@email.com', '11988887777', 'pwd123', '2023-01-10', 'Ativo'), " +
                    "(7, 'Bruno Souza', 'bruno.souza@email.com', '21977776666', 'pwd456', '2023-02-15', 'Ativo'), " +
                    "(8, 'Daniela Oliveira', 'daniela.o@email.com', '41955554444', 'pwd321', '2023-04-25', 'Ativo'), " +
                    "(9, 'Eduardo Santos', 'edu.santos@email.com', '51944443333', 'pwd654', '2023-05-30', 'Ativo'), " +
                    "(10, 'Julia Carvalho', 'julia.c@email.com', '11987654321', 'pwd963', '2023-10-18', 'Ativo');");

            stmt.execute("INSERT IGNORE INTO CARTEIRA (id_carteira, id_investidor, nome_carteira, data_criacao, descricao, valor_total_investido) VALUES " +
                    "(1, 1, 'Aposentadoria', '2023-01-12', 'Foco em dividendos', 15000.00), " +
                    "(2, 1, 'Cripto Especulacao', '2023-06-05', 'Carteira de alto risco', 5000.00), " +
                    "(3, 2, 'Longo Prazo', '2023-02-20', 'Acoes de tecnologia', 20000.00), " +
                    "(4, 4, 'FIIs Renda Mensal', '2023-05-01', 'Foco em fundos', 10000.00), " +
                    "(5, 5, 'Reserva de Oportunidade', '2023-06-15', 'Renda Fixa e Liquidez', 8000.00), " +
                    "(6, 6, 'Acoes Crescimento', '2023-06-20', 'Empresas de alto crescimento', 45000.00), " +
                    "(7, 7, 'Bolsa e Dividendos', '2023-07-25', 'Foco no indice Ibovespa', 18000.00), " +
                    "(8, 9, 'Carteira Geral Igor', '2023-09-15', 'Diversificacao de ativos', 22000.00), " +
                    "(9, 10, 'Foco em FIIs Julia', '2023-10-20', 'Fundos imobiliarios selecionados', 27000.00);");

            stmt.execute("INSERT IGNORE INTO ATIVO (id_ativo, tipo_ativo, simbolo, nome_ativo, descricao, data_listagem) VALUES " +
                    "(1, 'Acao', 'PETR4', 'Petrobras PN', 'Acoes preferenciais', '1990-01-01'), " +
                    "(2, 'Acao', 'VALE3', 'Vale ON', 'Acoes ordinarias', '1990-01-01'), " +
                    "(3, 'Criptomoeda', 'BTC', 'Bitcoin', 'Cripto descentralizada', '2009-01-03'), " +
                    "(4, 'Fundo Imobiliario', 'MXRF11', 'Maxi Renda FII', 'Fundo de papel', '2012-04-13'), " +
                    "(5, 'Acao', 'AAPL34', 'Apple BDR', 'BDR da Apple listado na B3', '2010-05-10'), " +
                    "(6, 'Acao', 'ITUB4', 'Itaú Unibanco PN', 'Acoes do maior banco privado brasileiro', '1995-03-10'), " +
                    "(7, 'Acao', 'WEGE3', 'Weg ON', 'Acoes da multinacional de motores eletricos', '1998-05-20'), " +
                    "(8, 'Acao', 'MSFT34', 'Microsoft BDR', 'BDR da Microsoft listado na B3', '2010-06-15'), " +
                    "(9, 'Criptomoeda', 'ETH', 'Ethereum', 'Rede blockchain inteligente e descentralizada', '2015-07-30'), " +
                    "(10, 'Criptomoeda', 'SOL', 'Solana', 'Blockchain de alta performance e rapidez', '2020-03-16'), " +
                    "(11, 'Fundo Imobiliario', 'HGLG11', 'CSHG Logística FII', 'Fundo de galpoes logisticos industriais', '2010-09-25'), " +
                    "(12, 'Fundo Imobiliario', 'XPML11', 'XP Malls FII', 'Fundo focado em shopping centers', '2018-01-15');");

            stmt.execute("INSERT IGNORE INTO TRANSACAO (id_transacao, id_carteira, id_ativo, tipo_transacao, quantidade, preco_unitario, valor_total, data_transacao, comissao) VALUES " +
                    "(1, 1, 1, 'Compra', 100, 35.00, 3500.00, '2023-01-15', 5.00), " +
                    "(2, 3, 5, 'Compra', 50, 80.00, 4000.00, '2023-02-25', 10.00), " +
                    "(3, 2, 3, 'Compra', 0.5, 100000.00, 50000.00, '2023-06-10', 25.00), " +
                    "(4, 4, 4, 'Compra', 500, 10.50, 5250.00, '2023-05-05', 2.50), " +
                    "(5, 1, 2, 'Compra', 100, 70.00, 7000.00, '2023-01-20', 8.00), " +
                    "(6, 6, 7, 'Compra', 200, 38.00, 7600.00, '2023-06-25', 10.00), " +
                    "(7, 7, 1, 'Compra', 150, 36.50, 5475.00, '2023-07-28', 5.00), " +
                    "(8, 7, 2, 'Compra', 40, 72.00, 2880.00, '2023-08-02', 6.00), " +
                    "(9, 8, 10, 'Compra', 50, 95.00, 4750.00, '2023-09-18', 4.00), " +
                    "(10, 9, 4, 'Compra', 500, 10.40, 5200.00, '2023-10-25', 3.00), " +
                    "(11, 9, 12, 'Compra', 80, 112.00, 8960.00, '2023-10-28', 8.00);");

            stmt.execute("INSERT IGNORE INTO HISTORICO_PRECO (id_historico, id_ativo, data, preco_abertura, preco_fechamento, preco_maximo, preco_minimo, volume_negociado) VALUES " +
                    "(1, 1, '2023-10-01', 34.50, 35.20, 35.50, 34.00, 15000000.00), " +
                    "(2, 2, '2023-10-01', 69.00, 70.50, 71.00, 68.50, 25000000.00), " +
                    "(3, 3, '2023-10-01', 99000.00, 102000.00, 103000.00, 98000.00, 50000000.00), " +
                    "(4, 4, '2023-10-01', 10.40, 10.55, 10.60, 10.35, 1200000.00), " +
                    "(5, 5, '2023-10-01', 79.00, 81.00, 81.50, 78.50, 3000000.00), " +
                    "(6, 7, '2023-12-01', 39.00, 40.10, 40.50, 38.80, 8000000.00), " +
                    "(7, 11, '2023-12-01', 161.00, 163.20, 164.00, 160.50, 1500000.00), " +
                    "(8, 12, '2023-12-01', 113.00, 114.80, 115.50, 112.50, 1800000.00);");

            stmt.execute("INSERT IGNORE INTO OBJETIVO (id_objetivo, id_carteira, meta_rentabilidade, prazo_meses, data_criacao, status, descricao) VALUES " +
                    "(1, 1, 15.00, 120, '2023-01-13', 'Ativo', 'Bater Ibovespa'), " +
                    "(2, 2, 50.00, 24, '2023-06-06', 'Ativo', 'Multiplicar capital'), " +
                    "(3, 3, 20.00, 60, '2023-02-21', 'Ativo', 'Exposicao mercado americano'), " +
                    "(4, 4, 10.00, 36, '2023-05-02', 'Ativo', 'Renda passiva'), " +
                    "(5, 5, 5.00, 12, '2023-06-16', 'Ativo', 'Manter capital'), " +
                    "(6, 6, 20.00, 48, '2023-06-21', 'Ativo', 'Bater o Ibovespa e Small Caps'), " +
                    "(7, 7, 10.00, 36, '2023-07-26', 'Ativo', 'Foco em dividendos estaveis'), " +
                    "(8, 9, 8.50, 72, '2023-10-21', 'Ativo', 'Renda passiva recorrente Julia');");

            stmt.execute("INSERT IGNORE INTO CARTEIRA_ATIVO (id_carteira, id_ativo, quantidade, valor_atual) VALUES " +
                    "(1, 1, 100.000000, 35.20), " +
                    "(1, 2, 100.000000, 70.50), " +
                    "(2, 3, 0.500000, 51000.00), " +
                    "(3, 5, 50.000000, 40.50), " +
                    "(4, 4, 500.000000, 52.75), " +
                    "(6, 7, 200.000000, 40.10), " +
                    "(7, 1, 150.000000, 35.20), " +
                    "(7, 2, 40.000000, 70.50), " +
                    "(8, 10, 50.000000, 98.20), " +
                    "(9, 4, 500.000000, 52.75), " +
                    "(9, 12, 80.000000, 114.80);");

            System.out.println("\n[Sucesso] Novos dados inseridos em perfeita sincronia com os originais!");

        } catch (Exception e) {
            System.err.println("Erro ao popular o banco de dados: " + e.getMessage());
        }
    }
}
