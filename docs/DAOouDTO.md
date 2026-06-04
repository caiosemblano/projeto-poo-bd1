1. O que é DAO (Data Access Object)?
O DAO é um padrão responsável por isolar a lógica de acesso ao banco de dados do resto da sua aplicação. Em vez de espalhar comandos SQL (SELECT, INSERT, etc.) pelas suas classes de menu ou regras de negócio, você cria uma classe específica (o DAO) para lidar com isso.

Responsabilidade: Conversar com o banco de dados (MySQL, via JDBC).
O que ele faz: Implementa o CRUD (Create, Read, Update, Delete).
Exemplo no seu projeto: InvestidorDAO, CarteiraDAO, etc. Se você precisa salvar um novo Investidor, a camada de serviço chama investidorDAO.inserir(investidor). O resto do sistema não faz ideia de como o MySQL guarda isso.
2. O que é DTO (Data Transfer Object)?
O DTO é um padrão responsável por transportar dados entre diferentes camadas ou sistemas, geralmente através de uma rede. Ele é um objeto "burro", contendo apenas atributos, getters e setters, sem nenhuma lógica de negócio ou de banco de dados.

Responsabilidade: Empacotar dados para transferência.
Quando é muito usado: Em APIs REST (desenvolvimento Web). Por exemplo, se você tem uma entidade Usuario com o campo senha, você não quer devolver a senha quando alguém consulta o usuário na API. Então, você cria um UsuarioDTO apenas com nome e email para mandar para o front-end.
Qual a principal diferença?
O DAO lida com o ACESSO (como eu gravo e busco do banco de dados).
O DTO lida com o TRANSPORTE (como eu empacoto a informação para enviá-la para outra camada/sistema).
Por que estamos utilizando DAO neste projeto?
Com base na especificação do seu projeto (docs/spec.md), a utilização do padrão DAO é uma escolha arquitetural exigida e perfeitamente adequada para o seu contexto. Aqui estão os motivos exatos:

Exigência das Disciplinas (POO e BD1): A especificação dita claramente na seção 2.2 que um dos requisitos de BD1 é ter "Models e DAOs em pastas separadas" e "CRUD específico por DAO". É a forma de demonstrar que vocês sabem integrar Java com SQL de forma limpa.
Separação de Responsabilidades (Arquitetura em Camadas): No item 4 da spec, vemos a arquitetura definida: Model → DAO → Service → UI. O DAO garante que a camada de Interface de Usuário (UI - os menus no console) e de regras de negócio (Service) fiquem totalmente livres de JDBC e SQL.
Padronização através de Interfaces: Vocês estão utilizando uma interface genérica Repositorio<T, ID>. O DAO implementa essa interface, garantindo que todas as entidades do banco tenham as mesmas operações básicas estruturadas (inserir, buscarPorId, etc.).
E por que não estamos usando DTOs? O seu projeto é uma aplicação desktop (console/menu interativo). Todas as camadas rodam no mesmo lugar (na mesma máquina e no mesmo processo da JVM). Não há comunicação via rede (como um backend Java conversando com um frontend React, por exemplo). Nesse cenário, é perfeitamente aceitável e muito mais produtivo que as camadas de Service e UI utilizem diretamente as entidades do pacote model/ (como Investidor ou Ativo) para exibir dados na tela. Criar DTOs para um sistema de console apenas geraria classes duplicadas desnecessariamente.