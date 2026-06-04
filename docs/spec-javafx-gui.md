# Spec — GUI JavaFX: Sistema de Gestão de Portfólio de Investimentos

## 1. Visão Geral

Este documento detalha a especificação para a substituição da interface de linha de comando atual por uma Interface Gráfica de Usuário (GUI) utilizando JavaFX. A arquitetura manterá as camadas de Model, DAO e Service intactas, alterando exclusivamente a camada UI.

O padrão adotado para a camada de visualização será o MVC adaptado para o ecossistema JavaFX, onde as Views são definidas em arquivos XML e a lógica de apresentação reside em classes Controller.

---

## 2. Dependências Necessárias

Como o projeto utiliza Java 17+, o JavaFX deve ser adicionado como dependência no gerenciador de pacotes.

```xml
<dependencies>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>17.0.6</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-fxml</artifactId>
        <version>17.0.6</version>
    </dependency>
</dependencies>
```

---

## 3. Estrutura de Pacotes Atualizada

A camada `ui` será refatorada para suportar a arquitetura FXML e Controllers.

```text
src/
├── main/
│   ├── java/
│   │   ├── br/inatel/
│   │   │   ├── model/
│   │   │   ├── dao/
│   │   │   ├── service/
│   │   │   └── ui/
│   │   │       ├── MainApplication.java
│   │   │       └── controller/
│   │   │           ├── DashboardController.java
│   │   │           ├── InvestidorController.java
│   │   │           ├── CarteiraController.java
│   │   │           ├── AtivoController.java
│   │   │           └── TransacaoController.java
│   └── resources/
│       └── views/
│           ├── css/
│           │   └── styles.css
│           ├── MainDashboard.fxml
│           ├── InvestidorView.fxml
│           ├── CarteiraView.fxml
│           ├── AtivoView.fxml
│           └── TransacaoView.fxml
```

---

## 4. Telas Principais (Views)

A navegação será um layout de painel lateral (Sidebar) com uma área de conteúdo dinâmico.

### 4.1. Dashboard Principal (MainDashboard.fxml)
- Layout Base: BorderPane
- Esquerda: VBox atuando como Sidebar de navegação com botões para as demais telas.
- Centro: StackPane onde as outras telas serão carregadas dinamicamente.

### 4.2. Gestão de Entidades (Ex: InvestidorView.fxml)
- Layout Base: VBox
- Topo: Barra de ferramentas com campo de busca e botão de cadastro.
- Centro: TableView listando os registros.
- Direita: Formulário de cadastro/edição contendo TextFields e PasswordField.

### 4.3. Resumo do Portfólio
- Visualização de Dados: Utilização de PieChart para mostrar a distribuição de ativos na carteira.
- Tabela de Posições: TableView mostrando o consolidado.

---

## 5. Arquitetura MVC com JavaFX

### 5.1. O Arquivo FXML (View)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<VBox xmlns="http://javafx.com/javafx/17" xmlns:fx="http://javafx.com/fxml/1" fx:controller="br.inatel.ui.controller.InvestidorController">
    <Button fx:id="btnCarregar" text="Carregar Investidores" onAction="#handleCarregarInvestidores"/>
    <TableView fx:id="tabelaInvestidores">
        <columns>
            <TableColumn fx:id="colNome" text="Nome" />
            <TableColumn fx:id="colEmail" text="Email" />
        </columns>
    </TableView>
</VBox>
```

### 5.2. A Classe Controller

```java
package br.inatel.ui.controller;

import br.inatel.model.Investidor;
import br.inatel.service.InvestidorService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class InvestidorController {

    @FXML
    private TableView<Investidor> tabelaInvestidores;
    
    @FXML
    private TableColumn<Investidor, String> colNome;
    
    @FXML
    private TableColumn<Investidor, String> colEmail;

    private InvestidorService investidorService;

    public InvestidorController() {
        this.investidorService = new InvestidorService();
    }

    @FXML
    public void initialize() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        carregarDados();
    }

    @FXML
    private void handleCarregarInvestidores() {
        carregarDados();
    }

    private void carregarDados() {
        List<Investidor> investidores = investidorService.listarTodos();
        ObservableList<Investidor> dados = FXCollections.observableArrayList(investidores);
        tabelaInvestidores.setItems(dados);
    }
}
```

---

## 6. Integração com Banco de Dados e Concorrência

Operações pesadas devem ser envolvidas em uma `javafx.concurrent.Task`.
A atualização dos componentes visuais após o retorno dos dados deve ser feita em `Platform.runLater`.

---

## 7. Fluxo de Execução da Aplicação

1. A classe `MainApplication` executa seu método `start(Stage primaryStage)`.
2. O arquivo `MainDashboard.fxml` é carregado através da classe `FXMLLoader`.
3. O Scene principal é configurado e o arquivo CSS é aplicado.
4. A janela é exibida ao usuário.
5. A interação ocorre via disparos de eventos que invocam os métodos correspondentes nas classes Controller.
6. Os Controllers invocam a camada Service, que acessa a camada DAO.
