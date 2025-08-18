# To-Do List API

Um sistema de gerenciamento de tarefas (To-Do List) simples, construído com Spring Boot.

## Como Rodar o Projeto

### Pré-requisitos

* Java 21 ou superior
* Maven
* Um banco de dados MySQL rodando na sua máquina.

### Passos

1.  **Clone o repositório:**
    ```bash
    git clone <URL_DO_REPOSITORIO>
    cd todolistapi
    ```

2.  **Configure o Banco de Dados:**
    Este projeto não inclui um arquivo `application.properties` ou `application.yml` com a configuração do banco de dados. Você precisará criar um em `src/main/resources/application.yml` e adicionar as configurações para a sua instância do MySQL.
    Exemplo:
    ```yml
    spring:
      datasource:
        url: jdbc:mysql://localhost:3306/todolistdb?createDatabaseIfNotExist=true
        username: SEU_USUARIO
        password: SUA_SENHA
      jpa:
        hibernate:
          ddl-auto: update
        show-sql: true
    ```

3.  **Compile e rode a aplicação com Maven:**
    Abra um terminal na raiz do projeto e execute o seguinte comando:

    ```bash
    ./mvnw spring-boot:run
    ```
    Ou, se você tiver o Maven instalado globalmente:
    ```bash
    mvn spring-boot:run
    ```
    A aplicação estará disponível em `http://localhost:8080`.

## Endpoints da API

Aqui estão os endpoints disponíveis na API:

| Método HTTP | Caminho         | Descrição                                         |
| :---------- | :-------------- | :-------------------------------------------------- |
| `GET`       | `/todo`         | Lista todas as tarefas de forma paginada.           |
| `GET`       | `/todo/pending` | Lista todas as tarefas com o estado "TODO" (pendentes). |
| `GET`       | `/todo/done`    | Lista todas as tarefas com o estado "DONE" (concluídas). |
| `GET`       | `/todo/{id}`    | Busca uma tarefa específica pelo seu ID.            |
| `POST`      | `/todo`         | Cria uma nova tarefa.                               |
| `PUT`       | `/todo`         | Atualiza uma tarefa existente.                      |
| `DELETE`    | `/todo/{id}`    | Deleta uma tarefa pelo seu ID.                      |