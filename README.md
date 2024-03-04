# GESTAO-FRETES-API

Projeto desenvolvido com SpringBoot, utilizando banco de dados em memória e com integração com Data JPA.
- Java 17
- SpringBoot 3.2.3
- Banco de Dados H2

#

Swagger: http://localhost:8080/swagger-ui/index.html#/
- Não há fretes cadastrados, para testar o fluxo, iniciar os testes cadastrando novos fretes.
- Método GET para listar todos os fretes, estão paginados.
- <pre>{ "page": 0, "size": 10, "orderBy": "id", "orderDirection": "DESC", "pageNumber": 0, "pageSize": 10 }</pre>
