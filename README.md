# MovieDB

## Descrição
O projeto **Movie Database** é uma aplicação Java que se conecta a um banco de dados MySQL para gerenciar uma coleção de filmes. Ele permite inserir, editar, excluir e listar filmes em um banco de dados, proporcionando uma maneira prática de entender como interagir com bancos de dados em Java.

## Funcionalidades
- **Listar filmes**: Exibe todos os filmes armazenados no banco de dados.
- **Inserir filmes**: Permite adicionar novos filmes ao banco de dados.
- **Editar filmes**: Possibilita modificar informações de filmes existentes.
- **Excluir filmes**: Remove filmes do banco de dados.

## Tecnologias Utilizadas
- **Java**: Linguagem de programação utilizada para desenvolver a aplicação.
- **MySQL**: Sistema de gerenciamento de banco de dados para armazenar informações sobre os filmes.
- **JDBC**: API do Java para conectar-se e executar operações no banco de dados.

## Requisitos
- Java Development Kit (JDK) 17 ou superior.
- MySQL Server.
- MySQL Connector/J (JAR) para conexão com o banco de dados.

## Instalação
1. Clone o repositório:
   ```bash
   git clone https://github.com/gustavosouto02/MovieDB.git
2. Navegue até o diretório do projeto::
   ```bash
   cd MovieDB
3. Certifique-se de ter o MySQL Server em execução e que o banco de dados moviedb está criado.
4. Adicione o MySQL Connector/J ao seu projeto:

- No IntelliJ IDEA, clique com o botão direito no projeto e vá em Project Structure > Libraries.
- Clique em + para adicionar uma nova biblioteca e selecione o arquivo mysql-connector-j-9.0.0.jar.
5. Altere as credenciais no código (usuário e senha) conforme necessário.

## Uso
- Para executar a aplicação, abra o arquivo MovieDatabase.java no IntelliJ IDEA e execute-o.
- Siga as instruções no console para interagir com a aplicação.

## Contribuição
Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou enviar pull requests.
