O objetivo principal desse projeto é criar uma solução simples para encurtar URLs longas, armazená-las em um banco de dados e permitir o redirecionamento dos usuários para a URL original.

Funcionalidades:
- Encurtamento de URLs:
O sistema gera códigos curtos exclusivos para URLs fornecidas pelos usuários e as armazena no banco de dados.

- Redirecionamento por código curto:
Quando o usuário acessa o código curto, o sistema realiza o redirecionamento para a URL original.

- Comunicação entre serviços:
O projeto foi estruturado com dois serviços principais:
1. Shortener Service: Responsável por gerar os códigos curtos e armazenar as URLs.
2. Redirect Service: Responsável por buscar a URL original e redirecionar o usuário.

- Tecnologias:
Java 17: Linguagem principal para o desenvolvimento.
Spring Framework:
Spring Boot: Para facilitar a criação do projeto.
Spring Data JPA: Para interação com o banco de dados.
Spring Web: Para construção das APIs REST.

- Banco de dados relacional:
Utilizei o MySQL configurado com JPA para o mapeamento objeto-relacional.

- Estrutura do projeto:
O sistema é dividido em camadas bem definidas:
1. Controller: Gerencia as requisições HTTP.
2. Service: Contém a lógica de negócios, como a geração de códigos curtos.
3. Repository: Interage com o banco de dados.

- Funcionalidades implementadas:
Geração de códigos curtos:
Utilizei UUID para gerar códigos únicos.
Validação e persistência de dados:
As URLs são validadas antes de serem armazenadas.

Este projeto reflete o meu compromisso em melhorar o meu conhecimento em Java e estabelecer uma relação mais familiar com a linguagem.

Lembrando que este é um dos meus primeiros projetos, claro que obtive ajuda à várias fontes e enchi o meu código de comentários para fixar na memória. Mas me sinto satisfeito com o resultado.

Fique à vontade para explorar o código!