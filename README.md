# starwars

<p>Para executar é necessário : <br>
  1º entrar na pasta raíz do projeto e execuar o comando "docker-compose up" <br>
  2º fazer clean package no projeto. <br>
  3º rodar o projeto.
</p>
  
<p>Documentação swagger não está completo, mas é possível utiliza-lo no http://localhost:8080/swagger-ui/index.html#/ </p>
<p>O campo "amountOfMovie" será com informação da quantidade de exibição o planeta teve em filmes através do consumo da API pública https://swapi.dev/ </p>

### EndPoints disponiveis
<p> GET ​/v1​/planets <br>
  retorna uma lista paginada com o padrão da API pública https://swapi.dev/
</p>

<p> POST ​/v1​/planets <br>
Persiste um Planeta e consume a API pública https://swapi.dev/ para verificar a quantidade de exibição em filmes. <br>
atributos necessário: name, climate, terrain
</p>

<p> GET ​/v1​/planets​/{id} <br>
Consulta um planeta por id e retorna 404 caso não encontre.
</p>

<p> DELETE ​/v1​/planets​/{id} <br>
deleta um planeta do banco de dados.
</p>

<p> GET ​/v1​/planets​/name​/{name} <br>
Consulta um planeta por nome no banco de dados.
</p>

<p> GET ​/v1​/planets​/starwarsapi <br>
Consume a API pública https://swapi.dev/ e retorna uma lista paginada de planetas.
</p>

#### Stack-utilizada
Java 11, MongoDb, Docker-compose, Swagger, Springboot, SpringData, Gson, Junit.
