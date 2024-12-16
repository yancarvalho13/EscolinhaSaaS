# Escolinha SaaS

Projeto de software de gerenciamento escolar desenvolvido em Java para a Escola Cristal Luz. Este sistema tem como objetivo oferecer funcionalidades para administrar alunos e professores, além de exibir informações sobre notas dos alunos.

## Funcionalidades Disponíveis

- **CRUD de Alunos**: Cadastro, leitura, atualização e exclusão de registros de alunos.
- **CRUD de Professores**: Cadastro, leitura, atualização e exclusão de registros de professores.
- **Tela de Alunos com Notas**: Visualização detalhada das notas associadas a cada aluno.

## Tecnologias Utilizadas

- **Java**: Linguagem de programação principal.
- **Spring Boot**: Framework para criação de APIs RESTful e gerenciamento da aplicação.
- **JPA (Java Persistence API)**: Para o gerenciamento de persistência e integração com bancos de dados relacionais.
- **Swing**: Interface gráfica para interação com o usuário.

## Estrutura do Projeto

O projeto segue uma estrutura monolítica organizada em camadas, visando facilitar a manutenção e o desenvolvimento de novas funcionalidades. As camadas principais incluem:

- **Model**: Contém as classes que representam as entidades do sistema, como `Aluno` e `Professor`.
- **Repository**: Interfaces JPA responsáveis pela comunicação com o banco de dados.
- **Service**: Camada intermediária que implementa as regras de negócio e integra o repositório às controllers.
- **Controller**: Gerencia as requisições vindas da interface gráfica ou das APIs REST.
- **View**: Implementada com Swing, responsável pela exibição de telas e interação com o usuário.

## Como Executar o Projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/yancarvalho13/EscolinhaSaaS.git
   ```
2. Importe o projeto em sua IDE de preferência (como IntelliJ IDEA ou Eclipse).
3. Configure o banco de dados conforme as instruções no arquivo `application.properties`.
4. Execute a aplicação pela classe principal do Spring Boot.

## Próximos Passos

- Implementação de relatórios gerenciais.
- Adição de funcionalidades para gestão de turmas e disciplinas.
- Integração com APIs externas para exportação de dados.

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou enviar pull requests com melhorias e novas ideias.

## Licença

Este projeto está licenciado sob a [MIT License](LICENSE).
