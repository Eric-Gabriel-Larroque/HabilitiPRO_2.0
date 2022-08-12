# Projeto HabilitPRO

![](gif/amostra_projeto.gif)

> Segundo projeto do Módulo 2 do programa de capacitação TECH-DIVE

### Requisitos para executar o projeto:

- Docker e docker-compose
- Java 18
- Wildfly 26.1.0: https://www.wildfly.org/downloads/

### Executando o projeto:

1. Clone o projeto na pasta de sua preferência
2. Abra o arquivo **standalone.xml** na pasta do seu Wildfly no
   caminho ``pastaWildfly/standalone/configuration/standalone.xml``;
3. Adicione em **<datasources>** o seguinte, para que seja configurado o banco de dados:

```
 <datasource jndi-name="java:jboss/datasources/ProjetoDS" pool-name="ProjetoDS" enabled="true" use-java-context="true" statistics-enabled="true">
    <connection-url>jdbc:postgresql://127.0.0.1:5432/techdive</connection-url>
    <driver>postgresql</driver>
    <security>
        <user-name>postgres</user-name>
        <password>postgres</password>
    </security>
</datasource>
```
4. Execute o comando no terminal, na raiz do projeto: `` docker-compose up -d ``
5. Acesse o endereço ``http://localhost:8080/``

### Tecnologias utilizadas:

- **JSF e Primefaces -** Para criação de componentes do projeto;
- **Bootstrap -** Para estilização e alinhamento de componentes;
- **JPA e Hibernate -** Responsável pela organização e as transações realizadas no banco de dados;
- **Apache Shiro -** Utilizado para gerenciamento de segurança;
- **PostgresSQL -** Banco de dados utilizado na aplicação;
- **Log4j -** Usado para criação e disparo de logs em cada metodo relevante para sua utilização;
- **Java -** Linguagem de programação utilizada na elaboração do projeto;
- **Trello -** Para organização de tarefas: https://trello.com/b/0RaCrCS4/projeto-4;
- **Kanban -** Utilizado para criação de features;
- **Gitflow -** Utilizado para organização de branches, features, versionamento de código;
- **JBoss/Wildfly -** Servidor para rodar aplicação;
- **Docker -** Container para rodar banco de dados;

### Logins para teste de funcionalidades:

#### 1. **Administrador**

- **E-mail:** admin@admin.com
- **Senha:** admin

##### 2. **Supervisor**

- **E-mail:** supervisor@supervisor.com
- **Senha:** supervisor

## API REST

> Extensão do projeto com foco na criação de CRUD para as entidades Trabalhador, Usuário e Empresa, fora autenticação de usuário.

As três entidades supracitadas possuem endpoints para criação, deleção, atualização, listagem geral e por identificador, porém, com certa filtragem de acesso pelo tipo de usuário.

### Autenticação de acesso

- Ao autenticar como supervisor, é liberado acesso somente aos endpoints de listagem geral e por identificador de **Empresa** e **Trabalhador**, posto o fato de que no projeto passado a aba _Usuário_ mantém-se indisponível para usuários comuns

- Autenticação de acesso validada ao inserir o _Bearer Token_ no _Header_ de autorização da requisição.

- Credenciais de acesso dispostas na seção **Logins para teste de funcionalidade**