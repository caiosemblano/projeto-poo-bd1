CREATE DATABASE GestaoPortifolio;
USE GestaoPortifolio;

CREATE TABLE INVESTIDOR (
    id_investidor INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    telefone VARCHAR(20),
    senha VARCHAR(255) NOT NULL,
    data_cadastro DATE NOT NULL,
    status VARCHAR(20) CHECK (status IN ('Ativo', 'Inativo'))
);

CREATE TABLE CARTEIRA (
    id_carteira INT AUTO_INCREMENT PRIMARY KEY,
    id_investidor INT NOT NULL,
    nome_carteira VARCHAR(100) NOT NULL,
    data_criacao DATE NOT NULL,
    descricao TEXT,
    valor_total_investido DECIMAL(15, 2) DEFAULT 0.00,
    FOREIGN KEY (id_investidor) REFERENCES INVESTIDOR(id_investidor) ON DELETE CASCADE
);

CREATE TABLE ATIVO (
    id_ativo INT AUTO_INCREMENT PRIMARY KEY,
    tipo_ativo VARCHAR(50) NOT NULL,
    simbolo VARCHAR(20) UNIQUE NOT NULL,
    nome_ativo VARCHAR(150) NOT NULL,
    descricao TEXT,
    data_listagem DATE
);

CREATE TABLE TRANSACAO (
    id_transacao INT AUTO_INCREMENT PRIMARY KEY,
    id_carteira INT NOT NULL,
    id_ativo INT NOT NULL,
    tipo_transacao VARCHAR(20) CHECK (tipo_transacao IN ('Compra', 'Venda')),
    quantidade DECIMAL(15, 6) NOT NULL,
    preco_unitario DECIMAL(15, 2) NOT NULL,
    valor_total DECIMAL(15, 2) NOT NULL,
    data_transacao DATE NOT NULL,
    comissao DECIMAL(10, 2) DEFAULT 0.00,
    FOREIGN KEY (id_carteira) REFERENCES CARTEIRA(id_carteira) ON DELETE CASCADE,
    FOREIGN KEY (id_ativo) REFERENCES ATIVO(id_ativo) ON DELETE RESTRICT
);

CREATE TABLE HISTORICO_PRECO (
    id_historico INT AUTO_INCREMENT PRIMARY KEY,
    id_ativo INT NOT NULL,
    data DATE NOT NULL,
    preco_abertura DECIMAL(15, 2),
    preco_fechamento DECIMAL(15, 2),
    preco_maximo DECIMAL(15, 2),
    preco_minimo DECIMAL(15, 2),
    volume_negociado DECIMAL(20, 2),
    FOREIGN KEY (id_ativo) REFERENCES ATIVO(id_ativo) ON DELETE CASCADE
);

CREATE TABLE OBJETIVO (
    id_objetivo INT AUTO_INCREMENT PRIMARY KEY,
    id_carteira INT UNIQUE NOT NULL,
    meta_rentabilidade DECIMAL(5, 2) NOT NULL,
    prazo_meses INT NOT NULL,
    data_criacao DATE NOT NULL,
    status VARCHAR(20) CHECK (status IN ('Ativo', 'Concluido')),
    descricao TEXT,
    FOREIGN KEY (id_carteira) REFERENCES CARTEIRA(id_carteira) ON DELETE CASCADE
);

CREATE TABLE CARTEIRA_ATIVO (
    id_carteira INT NOT NULL,
    id_ativo INT NOT NULL,
    quantidade DECIMAL(15, 6) NOT NULL DEFAULT 0,
    valor_atual DECIMAL(15, 2) NOT NULL DEFAULT 0.00,
    PRIMARY KEY (id_carteira, id_ativo),
    FOREIGN KEY (id_carteira) REFERENCES CARTEIRA(id_carteira) ON DELETE CASCADE,
    FOREIGN KEY (id_ativo) REFERENCES ATIVO(id_ativo) ON DELETE RESTRICT
);

INSERT INTO INVESTIDOR (nome, email, telefone, senha, data_cadastro, status) VALUES 
('Vaporeon', 'eevee@email.com', '11999999999', 'hash123', '2023-01-10', 'Ativo'), 
('Mewtwo', 'mewone@email.com', '11988888888', 'hash456', '2023-02-15', 'Ativo'), 
('Vampeta', 'pepeta@email.com', '11977777777', 'hash789', '2023-03-20', 'Inativo'), 
('umbigo', 'doisbigo@email.com', '11966666666', 'hash321', '2023-04-25', 'Ativo'), 
('Weverson', 'zoio@email.com', '11955555555', 'Alekhash654', '2023-05-30', 'Ativo');

INSERT INTO CARTEIRA (id_investidor, nome_carteira, data_criacao, descricao, valor_total_investido) VALUES 
(1, 'Aposentadoria', '2023-01-12', 'Foco em dividendos', 15000.00), 
(1, 'Cripto Especulacao', '2023-06-05', 'Carteira de alto risco', 5000.00), 
(2, 'Longo Prazo', '2023-02-20', 'Acoes de tecnologia', 20000.00), 
(4, 'FIIs Renda Mensal', '2023-05-01', 'Foco em fundos', 10000.00), 
(5, 'Reserva de Oportunidade', '2023-06-15', 'Renda Fixa e Liquidez', 8000.00);

INSERT INTO ATIVO (tipo_ativo, simbolo, nome_ativo, descricao, data_listagem) VALUES 
('Acao', 'PETR4', 'Petrobras PN', 'Acoes preferenciais', '1990-01-01'), 
('Acao', 'VALE3', 'Vale ON', 'Acoes ordinarias', '1990-01-01'), 
('Criptomoeda', 'BTC', 'Bitcoin', 'Cripto descentralizada', '2009-01-03'), 
('Fundo Imobiliario', 'MXRF11', 'Maxi Renda FII', 'Fundo de papel', '2012-04-13'), 
('Acao', 'AAPL34', 'Apple BDR', 'BDR da Apple listado na B3', '2010-05-10');

INSERT INTO TRANSACAO (id_carteira, id_ativo, tipo_transacao, quantidade, preco_unitario, valor_total, data_transacao, comissao) VALUES 
(1, 1, 'Compra', 100, 35.00, 3500.00, '2023-01-15', 5.00), 
(3, 5, 'Compra', 50, 80.00, 4000.00, '2023-02-25', 10.00), 
(2, 3, 'Compra', 0.5, 100000.00, 50000.00, '2023-06-10', 25.00), 
(4, 4, 'Compra', 500, 10.50, 5250.00, '2023-05-05', 2.50), 
(1, 2, 'Compra', 100, 70.00, 7000.00, '2023-01-20', 8.00);

INSERT INTO HISTORICO_PRECO (id_ativo, data, preco_abertura, preco_fechamento, preco_maximo, preco_minimo, volume_negociado) VALUES 
(1, '2023-10-01', 34.50, 35.20, 35.50, 34.00, 15000000.00), 
(2, '2023-10-01', 69.00, 70.50, 71.00, 68.50, 25000000.00), 
(3, '2023-10-01', 99000.00, 102000.00, 103000.00, 98000.00, 50000000.00), 
(4, '2023-10-01', 10.40, 10.55, 10.60, 10.35, 1200000.00), 
(5, '2023-10-01', 79.00, 81.00, 81.50, 78.50, 3000000.00);

INSERT INTO OBJETIVO (id_carteira, meta_rentabilidade, prazo_meses, data_criacao, status, descricao) VALUES 
(1, 15.00, 120, '2023-01-13', 'Ativo', 'Bater Ibovespa'), 
(2, 50.00, 24, '2023-06-06', 'Ativo', 'Multiplicar capital'), 
(3, 20.00, 60, '2023-02-21', 'Ativo', 'Exposicao mercado americano'), 
(4, 10.00, 36, '2023-05-02', 'Ativo', 'Renda passiva'), 
(5, 5.00, 12, '2023-06-16', 'Ativo', 'Manter capital');

INSERT INTO CARTEIRA_ATIVO (id_carteira, id_ativo, quantidade, valor_atual) VALUES 
(1, 1, 100, 3520.00), 
(1, 2, 100, 7050.00), 
(2, 3, 0.5, 51000.00), 
(3, 5, 50, 4050.00), 
(4, 4, 500, 5275.00);

CREATE ROLE role_gestor_investimentos;
GRANT SELECT, INSERT, UPDATE ON *.* TO role_gestor_investimentos;

CREATE USER 'admin_alpha'@'localhost' IDENTIFIED BY 'AlphaSigmaRedPill';
CREATE USER 'admin_beta'@'localhost' IDENTIFIED BY 'Betinha67';

GRANT role_gestor_investimentos TO 'admin_alpha'@'localhost';
GRANT role_gestor_investimentos TO 'admin_beta'@'localhost';

CREATE OR REPLACE VIEW vw_resumo_portfolio AS 
SELECT 
    c.nome_carteira, 
    i.nome AS investidor, 
    a.simbolo AS ativo, 
    ca.quantidade, 
    ca.valor_atual, 
    (ca.quantidade * ca.valor_atual) AS montante_total_ativo 
FROM CARTEIRA_ATIVO ca 
JOIN CARTEIRA c ON ca.id_carteira = c.id_carteira 
JOIN INVESTIDOR i ON c.id_investidor = i.id_investidor 
JOIN ATIVO a ON ca.id_ativo = a.id_ativo;

DELIMITER $$ 

DROP PROCEDURE IF EXISTS sp_inativar_investidor$$ 

CREATE PROCEDURE sp_inativar_investidor (IN p_id_investidor INT)
BEGIN 
    UPDATE INVESTIDOR 
    SET status = 'Inativo' 
    WHERE id_investidor = p_id_investidor; 
END $$ 

DELIMITER ;

DELIMITER $$ 

DROP FUNCTION IF EXISTS fn_calcular_montante_total$$ 

CREATE FUNCTION fn_calcular_montante_total (qtd DECIMAL(15, 6), valor DECIMAL(15, 2)) 
RETURNS DECIMAL(15, 2) DETERMINISTIC 
BEGIN 
    RETURN qtd * valor; 
END $$ 

DELIMITER ;
