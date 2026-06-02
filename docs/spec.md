Vou começar lendo os arquivos do projeto para entender o contexto antes de criar a spec.

Viewed requisitosPOO.md:1-96
Viewed requisitosBD1.md:1-25
Listed directory projeto-poo-bd1

Há um arquivo SQL e um documento Word. Vou ler o SQL e tentar acessar o .docx para entender o modelo de dados e o tema do projeto.

Viewed config-inicial.sql:1-175
Viewed README.md:1-2

Tenho tudo que preciso. Agora vou criar uma spec detalhada completa como artefato.

Created spec.md

A spec está pronta. Aqui está um resumo do que foi mapeado:

---

## O que foi levantado

**Tema:** Sistema de **Gestão de Portfólio de Investimentos** para a disciplina de POO (C06) e BD1, integrados.

### Principais decisões de design

**POO — hierarquia de classes:**
- `Ativo` é **classe abstrata** com método `descricaoResumida()` → herdado por `Acao`, `Criptomoeda` e `FundoImobiliario` (polimorfismo)
- Interface genérica `Repositorio<T, ID>` como contrato de CRUD (abstração)
- `Carteira` **compõe** `CarteiraAtivo` e `Objetivo` (composição/associação)

**Banco de dados — já modelado no `config-inicial.sql`:**
- 7 tabelas, 1 VIEW, 1 procedure (`sp_inativar_investidor`), 1 function (`fn_calcular_montante_total`)
- A spec define **5 queries com JOIN** (o requisito é mínimo de 3)

**Arquitetura:** 4 camadas — `model / dao / service / ui` — com um DAO e um sub-menu por entidade.

---

### Pontos para confirmar com o grupo

> [!IMPORTANT]
> A spec tem uma **divisão sugerida por integrante** na seção 11. Ajuste conforme as habilidades reais do grupo.

> [!NOTE]
> O requisito de hash de senha está marcado, mas o `config-inicial.sql` usa strings simples (`hash123`). Decide se usará **BCrypt** ou apenas simular como sufixo.