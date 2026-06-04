# Projeto POO BD1

## Pré-requisitos

Para rodar este projeto localmente, certifique-se de ter os seguintes componentes instalados:
- **Docker** e **Docker Compose** (para rodar o banco de dados de forma isolada)
- **Java JDK** (versão 21 ou compatível)
- **Maven** (para gerenciamento de dependências e build)

---

## Como rodar o projeto (Passo a Passo)

### Passo 1: Subir o Banco de Dados (MySQL)

O projeto utiliza o Docker para provisionar o banco de dados. Ele foi configurado para expor a porta **3307** localmente, a fim de evitar conflitos caso você já tenha o MySQL instalado na porta padrão (3306).

Abra o terminal na pasta raiz do projeto e execute:

```bash
docker compose up -d
```

> **Atenção:** Na primeira execução, o Docker baixará a imagem do MySQL 8.0 e rodará automaticamente o script `config-inicial.sql`. Este script será responsável por criar o banco `GestaoPortifolio`, as tabelas necessárias e inserir alguns dados fictícios para testes.

Você pode conferir se o banco de dados já terminou de inicializar executando:
```bash
docker compose ps
```
*(Aguarde até que a coluna de status mostre `healthy`)*.

### Passo 2: Compilar e Executar a Aplicação Java

Com o banco de dados rodando, agora você pode executar a aplicação.

Para compilar o código fonte e baixar eventuais dependências, execute:
```bash
mvn clean compile
```

Para rodar o programa pelo terminal usando o Maven, digite:
```bash
mvn exec:java -Dexec.mainClass="br.inatel.GestaoPortifolioApplication"
```

> **Dica de IDE:** Se preferir, você pode simplesmente abrir o projeto na sua IDE favorita (VS Code, IntelliJ, Eclipse, etc.) e rodar a classe `GestaoPortifolioApplication.java` diretamente. O projeto já está configurado na classe `ConnectionFactory` para acessar o banco na porta `3307`.

---

## Comandos Úteis do Docker

- **Acompanhar os logs do banco:** 
  ```bash
  docker compose logs -f mysql
  ```
- **Desligar o banco de dados (mantendo os dados salvos):** 
  ```bash
  docker compose down
  ```
- **Desligar o banco de dados e APAGAR todos os dados (reset total):** 
  ```bash
  docker compose down -v
  ```
