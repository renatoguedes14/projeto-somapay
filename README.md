# 1 - Como executar a aplicação 
<b>Para compilar e executar: </b> na raíz do projeto, execute o comando: mvn spring-boot:run

<b>Requisitos para executar a aplicação: </b> É necessário ter PostgreSQL instalado e um banco de dados configurado. Em 'application.properties', altere os parâmetros '<b>spring.datasource.url',  'spring.datasource.username'</b> e <b>'spring.datasource.password'</b> para conter os parâmetros do banco de dados local da máquina onde a aplicação será executada. A criação das tabelas e colunas é feita automaticamente
# 2 - Como executar os testes unitários
Na raiz do projeto, execute o comando: mvn test

# 3 - Informações adicionais:

<b>Tecnologias e bibliotecas utilizadas: </b> Java 11, Spring Boot e Spring Data, PostgreSQL, Project Lombok e Swagger UI

## Swagger
http://localhost:8091/swagger-ui/index.html


<b>Este projeto foi desenvolvido por Renato Guedes para o processo seletivo da vaga de Desenvolvedor de back-end Júnior da Somapay.</b>
