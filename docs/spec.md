# Spec — Sistema de Gestão de Portfólio de Investimentos

> **Disciplinas:** C06 POO com Java · BD1 (Banco de Dados I)  
> **Instituição:** Inatel — Instituto Nacional de Telecomunicações  
> **Prazo de entrega:** apresentação entre 15/06 e 24/06  
> **Entrega:** GitHub (código + script SQL)

---

## 1. Visão Geral

O sistema é uma **aplicação desktop em Java** (console/menu interativo) que permite a **gestores de investimentos e investidores** criar e acompanhar carteiras de ativos financeiros, registrar transações de compra e venda, consultar histórico de preços e acompanhar objetivos de rentabilidade.

O backend persiste todos os dados em um banco **MySQL** (`GestaoPortifolio`), integrado via JDBC, seguindo a arquitetura de camadas **Model → DAO → Service → UI**.

---

## 2. Contexto Acadêmico & Requisitos das Disciplinas

### 2.1 Requisitos de POO (C06)

| Conceito | Como será aplicado |
|---|---|
| **Classes e Objetos** | Cada entidade do domínio (`Investidor`, `Carteira`, `Ativo`, `Transacao`, `HistoricoPreco`, `Objetivo`) é uma classe com instâncias gerenciadas em tempo de execução. |
| **Encapsulamento** | Todos os atributos `private`, acesso via `getters/setters`. Senhas nunca expostas diretamente. |
| **Herança** | `Ativo` é abstrata. Subclasses concretas: `Acao`, `Criptomoeda`, `FundoImobiliario`. |
| **Polimorfismo** | Método `descricaoResumida()` sobrescrito em cada subclasse de `Ativo`. Menu genérico imprime qualquer `Ativo` polimorficamente. |
| **Abstração** | Classes abstratas `Ativo` e interfaces `Repositorio<T, ID>` (contrato de CRUD). |
| **Composição/Associação** | `Carteira` contém uma lista de `CarteiraAtivo`; `Investidor` possui lista de `Carteira`. |
| **Organização do código** | Pacotes separados: `model`, `dao`, `service`, `ui`, `util`. |

### 2.2 Requisitos de BD1

| Requisito | Atendimento |
|---|---|
| Integração SQL com Java | JDBC + MySQL Connector |
| Menu com INSERT/UPDATE/DELETE/SELECT por tabela | Menu interativo no console cobrindo todas as 7 entidades |
| Busca por atributo (≥1 por entidade) | Detalhado na §5 |
| ≥ 3 SELECTs com JOIN | 5 queries com JOIN definidas na §6 |
| Models e DAOs em pastas separadas | Pacotes `model/` e `dao/` |
| CRUD específico por DAO | Um DAO por entidade |
| Script SQL com `SELECT * FROM tabela` | Incluído no `config-inicial.sql` e em script de verificação |

---

## 3. Modelagem de Dados

### 3.1 Diagrama Entidade-Relacionamento (texto)

```
INVESTIDOR (1) ──< CARTEIRA (1) ──< TRANSACAO >── ATIVO
                       |                              |
                  CARTEIRA_ATIVO >─────────────── ATIVO
                       |
                   OBJETIVO (1:1)

ATIVO ──< HISTORICO_PRECO
```

### 3.2 Esquema Relacional

```
INVESTIDOR (id_investidor PK, nome, email UNIQUE, telefone, senha, data_cadastro, status)
CARTEIRA   (id_carteira PK, id_investidor FK, nome_carteira, data_criacao, descricao, valor_total_investido)
ATIVO      (id_ativo PK, tipo_ativo, simbolo UNIQUE, nome_ativo, descricao, data_listagem)
TRANSACAO  (id_transacao PK, id_carteira FK, id_ativo FK, tipo_transacao, quantidade, preco_unitario, valor_total, data_transacao, comissao)
HISTORICO_PRECO (id_historico PK, id_ativo FK, data, preco_abertura, preco_fechamento, preco_maximo, preco_minimo, volume_negociado)
OBJETIVO   (id_objetivo PK, id_carteira FK UNIQUE, meta_rentabilidade, prazo_meses, data_criacao, status, descricao)
CARTEIRA_ATIVO (id_carteira FK, id_ativo FK, quantidade, valor_atual) PK(id_carteira, id_ativo)
```

### 3.3 Restrições de Integridade

- `TRANSACAO.tipo_transacao` ∈ `{'Compra', 'Venda'}`
- `INVESTIDOR.status` ∈ `{'Ativo', 'Inativo'}`
- `OBJETIVO.status` ∈ `{'Ativo', 'Concluido'}`
- `OBJETIVO` tem relacionamento 1:1 com `CARTEIRA` (UNIQUE em `id_carteira`)
- `TRANSACAO` impede exclusão de `ATIVO` referenciado (`ON DELETE RESTRICT`)

---

## 4. Arquitetura do Sistema

```
src/
├── main/
│   └── Main.java                  ← entry point (menu principal)
├── model/
│   ├── Investidor.java
│   ├── Carteira.java
│   ├── Ativo.java                 ← abstract
│   ├── Acao.java                  ← extends Ativo
│   ├── Criptomoeda.java           ← extends Ativo
│   ├── FundoImobiliario.java      ← extends Ativo
│   ├── Transacao.java
│   ├── HistoricoPreco.java
│   ├── Objetivo.java
│   └── CarteiraAtivo.java
├── dao/
│   ├── Repositorio.java           ← interface genérica CRUD
│   ├── ConnectionFactory.java     ← gerencia conexão JDBC
│   ├── InvestidorDAO.java
│   ├── CarteiraDAO.java
│   ├── AtivoDAO.java
│   ├── TransacaoDAO.java
│   ├── HistoricoPrecoDAO.java
│   ├── ObjetivoDAO.java
│   └── CarteiraAtivoDAO.java
├── service/
│   ├── InvestidorService.java
│   ├── CarteiraService.java
│   ├── AtivoService.java
│   └── TransacaoService.java
└── ui/
    ├── MenuPrincipal.java
    ├── MenuInvestidor.java
    ├── MenuCarteira.java
    ├── MenuAtivo.java
    ├── MenuTransacao.java
    ├── MenuHistorico.java
    └── MenuObjetivo.java
```

---

## 5. Hierarquia de Classes Java

### 5.1 Interface `Repositorio<T, ID>`

```java
public interface Repositorio<T, ID> {
    void inserir(T entidade);
    T buscarPorId(ID id);
    List<T> listarTodos();
    void atualizar(T entidade);
    void deletar(ID id);
}
```

### 5.2 Classe Abstrata `Ativo`

```java
public abstract class Ativo {
    private int idAtivo;
    private String tipoAtivo;    // discriminador: "Acao" | "Criptomoeda" | "FundoImobiliario"
    private String simbolo;
    private String nomeAtivo;
    private String descricao;
    private LocalDate dataListagem;

    // getters/setters...
    public abstract String descricaoResumida();  // polimorfismo
}
```

### 5.3 Subclasses de `Ativo`

| Classe | `tipoAtivo` | Comportamento extra |
|---|---|---|
| `Acao` | `"Acao"` | `descricaoResumida()` exibe mercado (B3 / BDR) |
| `Criptomoeda` | `"Criptomoeda"` | `descricaoResumida()` exibe rede/blockchain |
| `FundoImobiliario` | `"FundoImobiliario"` | `descricaoResumida()` exibe tipo de fundo (papel/tijolo) |

### 5.4 Composição em `Carteira`

```java
public class Carteira {
    private int idCarteira;
    private Investidor investidor;         // associação
    private String nomeCarteira;
    private LocalDate dataCriacao;
    private String descricao;
    private BigDecimal valorTotalInvestido;
    private List<CarteiraAtivo> posicoes; // composição
    private Objetivo objetivo;            // composição 1:1
}
```

---

## 6. Requisitos Funcionais por Entidade

### 6.1 INVESTIDOR

| Operação | Descrição |
|---|---|
| **Criar** | Cadastrar novo investidor com nome, email, telefone, senha (hash), data_cadastro |
| **Listar** | Todos os investidores |
| **Buscar** | Por **email** (atributo obrigatório) ou por **nome** (LIKE) |
| **Atualizar** | Telefone, email, senha |
| **Inativar** | Executa a stored procedure `sp_inativar_investidor(id)` |
| **Deletar** | Remove o investidor (cascata derruba carteiras) |

### 6.2 CARTEIRA

| Operação | Descrição |
|---|---|
| **Criar** | Carteira vinculada a um investidor |
| **Listar** | Todas as carteiras de um investidor |
| **Buscar** | Por **nome_carteira** |
| **Atualizar** | Nome, descrição, valor_total_investido |
| **Deletar** | Cascata derruba transações, objetivos e posições |

### 6.3 ATIVO

| Operação | Descrição |
|---|---|
| **Criar** | Cadastrar ativo com tipo, símbolo, nome, descrição |
| **Listar** | Todos os ativos |
| **Buscar** | Por **símbolo** ou por **tipo_ativo** |
| **Atualizar** | Descrição, nome |
| **Deletar** | Bloqueado se houver transações vinculadas |

### 6.4 TRANSACAO

| Operação | Descrição |
|---|---|
| **Criar** | Registrar compra ou venda (valida que carteira e ativo existem) |
| **Listar** | Todas as transações de uma carteira |
| **Buscar** | Por **tipo_transacao** (`Compra`/`Venda`) ou por intervalo de **data** |
| **Atualizar** | Corrigir comissão |
| **Deletar** | Remover transação específica |

### 6.5 HISTORICO_PRECO

| Operação | Descrição |
|---|---|
| **Criar** | Inserir registro de preço para data/ativo |
| **Listar** | Histórico de um ativo específico |
| **Buscar** | Por **data** |
| **Atualizar** | Preços OHLCV de uma data |
| **Deletar** | Remover registro de data |

### 6.6 OBJETIVO

| Operação | Descrição |
|---|---|
| **Criar** | Associar objetivo a carteira (1:1, valida duplicidade) |
| **Listar** | Todos os objetivos |
| **Buscar** | Por **status** (`Ativo`/`Concluido`) |
| **Atualizar** | Meta, prazo, status |
| **Deletar** | Remove o objetivo da carteira |

### 6.7 CARTEIRA_ATIVO (posição consolidada)

| Operação | Descrição |
|---|---|
| **Criar/Atualizar** | Inserir ou atualizar posição ao registrar transação |
| **Listar** | Posições de uma carteira |
| **Buscar** | Por **id_ativo** dentro de uma carteira |
| **Deletar** | Zerar posição |

---

## 7. Queries SQL com JOIN (≥ 3 obrigatórias)

### JOIN 1 — Resumo do portfólio (já existe como VIEW)
```sql
-- vw_resumo_portfolio
SELECT c.nome_carteira, i.nome AS investidor, a.simbolo,
       ca.quantidade, ca.valor_atual,
       fn_calcular_montante_total(ca.quantidade, ca.valor_atual) AS montante
FROM CARTEIRA_ATIVO ca
JOIN CARTEIRA   c ON ca.id_carteira = c.id_carteira
JOIN INVESTIDOR i ON c.id_investidor = i.id_investidor
JOIN ATIVO      a ON ca.id_ativo = a.id_ativo;
```

### JOIN 2 — Histórico de transações com detalhes de ativo e carteira
```sql
SELECT t.data_transacao, t.tipo_transacao,
       a.simbolo, a.nome_ativo, a.tipo_ativo,
       t.quantidade, t.preco_unitario, t.valor_total, t.comissao,
       c.nome_carteira, i.nome AS investidor
FROM TRANSACAO t
JOIN CARTEIRA   c ON t.id_carteira = c.id_carteira
JOIN INVESTIDOR i ON c.id_investidor = i.id_investidor
JOIN ATIVO      a ON t.id_ativo = a.id_ativo
WHERE i.id_investidor = ?
ORDER BY t.data_transacao DESC;
```

### JOIN 3 — Objetivos com situação da carteira
```sql
SELECT o.descricao AS objetivo, o.meta_rentabilidade, o.prazo_meses,
       o.status AS status_objetivo,
       c.nome_carteira, c.valor_total_investido,
       i.nome AS investidor
FROM OBJETIVO o
JOIN CARTEIRA   c ON o.id_carteira = c.id_carteira
JOIN INVESTIDOR i ON c.id_investidor = i.id_investidor;
```

### JOIN 4 — Preço atual vs. preço histórico de cada ativo em carteira
```sql
SELECT a.simbolo, a.nome_ativo,
       ca.quantidade, ca.valor_atual AS preco_atual,
       hp.data AS data_hist, hp.preco_fechamento AS preco_hist,
       (ca.valor_atual - hp.preco_fechamento) AS variacao
FROM CARTEIRA_ATIVO ca
JOIN ATIVO          a  ON ca.id_ativo = a.id_ativo
JOIN HISTORICO_PRECO hp ON a.id_ativo = hp.id_ativo
WHERE ca.id_carteira = ?
ORDER BY hp.data DESC;
```

### JOIN 5 — Resumo de compras e vendas por investidor
```sql
SELECT i.nome AS investidor,
       a.simbolo,
       SUM(CASE WHEN t.tipo_transacao = 'Compra' THEN t.valor_total ELSE 0 END) AS total_comprado,
       SUM(CASE WHEN t.tipo_transacao = 'Venda'  THEN t.valor_total ELSE 0 END) AS total_vendido
FROM TRANSACAO  t
JOIN CARTEIRA   c ON t.id_carteira = c.id_carteira
JOIN INVESTIDOR i ON c.id_investidor = i.id_investidor
JOIN ATIVO      a ON t.id_ativo = a.id_ativo
GROUP BY i.id_investidor, a.id_ativo;
```

---

## 8. Estrutura do Menu (UI)

```
=== GESTÃO DE PORTFÓLIO ===
1. Investidores
2. Carteiras
3. Ativos
4. Transações
5. Histórico de Preços
6. Objetivos
7. Relatórios (JOINs)
0. Sair

--- Sub-menu (ex: Investidores) ---
1. Listar todos
2. Buscar por email
3. Buscar por nome
4. Cadastrar novo
5. Atualizar
6. Inativar (procedure)
7. Deletar
0. Voltar
```

---

## 9. Objetos SQL Existentes

| Objeto | Tipo | Descrição |
|---|---|---|
| `vw_resumo_portfolio` | VIEW | Resumo consolidado de posições |
| `sp_inativar_investidor` | STORED PROCEDURE | Marca investidor como 'Inativo' |
| `fn_calcular_montante_total` | FUNCTION | `qtd × valor` deterministic |
| `role_gestor_investimentos` | ROLE | SELECT + INSERT + UPDATE em todas as tabelas |
| `admin_alpha`, `admin_beta` | USERs MySQL | Atribuídos ao role acima |

---

## 10. Requisitos Não-Funcionais

| Categoria | Requisito |
|---|---|
| **Linguagem** | Java 17+ |
| **Banco** | MySQL 8.x |
| **Conectividade** | JDBC via `mysql-connector-j` |
| **Arquitetura** | Camadas: `model / dao / service / ui` |
| **Segurança** | Senhas armazenadas como hash (não em plaintext) |
| **Tratamento de erros** | `try-catch` em todos os acessos ao BD; mensagem amigável ao usuário |
| **Organização** | Pacotes Java refletem as camadas; sem lógica SQL na `main` |
| **Versionamento** | GitHub, commits de todos os integrantes |

---

## 11. Plano de Entrega

```
config-inicial.sql          ← schema + dados de exemplo + views + procedures
src/
├── model/                  ← 10 classes (7 entidades + Repositorio + subclasses Ativo)
├── dao/                    ← 7 DAOs + ConnectionFactory
├── service/                ← 4 services de negócio
└── ui/                     ← MenuPrincipal + 6 sub-menus
```

### Divisão sugerida (4 integrantes)

| Integrante | Responsabilidade |
|---|---|
| **A** | Modelos (`model/`), classe abstrata `Ativo` + subclasses, herança/polimorfismo |
| **B** | `ConnectionFactory`, DAOs de `Investidor`, `Carteira`, `CarteiraAtivo` |
| **C** | DAOs de `Ativo`, `Transacao`, `HistoricoPreco`, `Objetivo` |
| **D** | Camada `service/`, toda a `ui/` (menus), queries JOIN, integração final |

---

## 12. Checklist de Apresentação

### POO (20 min)
- [ ] Demonstrar o sistema funcionando end-to-end
- [ ] Mostrar diagrama de classes (herança de `Ativo`)
- [ ] Explicar onde cada pilar de POO foi aplicado
- [ ] Exibir trechos de código relevantes (interface, classe abstrata, polimorfismo)
- [ ] Todos os integrantes falam

### BD1 (10 min)
- [ ] Executar INSERT, SELECT, UPDATE, DELETE pelo menu Java
- [ ] Rodar ao menos 2 JOINs demonstrando resultado no terminal
- [ ] Mostrar alterações refletindo no banco em tempo real (`SELECT *`)
- [ ] Não é necessário mostrar código — só o funcionamento
