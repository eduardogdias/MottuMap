# MottuMap
 
MottuMap é um projeto que utiliza visão computacional e tecnologia RFID para facilitar a localização e a gestão das motos nos pátios da Mottu.
 
Cada moto será equipada com uma etiqueta RFID, que ao ser lida por sensores instalados nas zonas do pátio, gera um novo registro no histórico de movimentação da moto. Isso permite rastrear em tempo real a zona em que a moto está localizada.
 
Além disso, para uma identificação ainda mais precisa, o sistema conta com câmeras de visão computacional que mapeiam as vagas dentro de cada zona, permitindo associar a moto a uma posição específica (vaga) dentro da zona.
 
Com isso, será possível acompanhar o histórico de movimentação de cada moto, sabendo por onde ela passou e onde está atualmente.

**Esta API desenvolvida com Spring Boot conta com**: um sistema de Login e Logout, usuários salvos no banco, gerenciamento de entidades pela Web, versionamento do banco de dados e mais tecnologias, acompanhe a documentação completa abaixo.
 
---

## Arquitetura da Solução

![](images/arquitetura.png)

---


 
## Interface Web

Veja abaixo como ficou o resultado das telas para gerenciar cada uma das entidades (Cadastro e Listagem).
- É possível editar ou excluir registros. 
- O formulário de cadastro/edição conta com validação nos campos.

![](images/listagem.png)
![](images/cadastro.png)


---

## Deploy da API

O Deploy e o banco de dados estão na Azure. Acesse os links abaixo e coloque o endpoint no final.

- Para retornar páginas ``HTML``:
https://webapp-mottumap.azurewebsites.net/


- Para retornar em ``JSON``:
https://webapp-mottumap.azurewebsites.net/api/


---

## Acesso / Usuários

Foram definidos dois usuários, assim que a aplicação inicia, através de Migrations com **Flyway**:
- **ADMIN:** tem permissão pra fazer o CRUD completo e acessar todas as telas do sistema.
- **USER:** só pode acessar páginas de listagem e fazer requisições do tipo GET. 

Para acessar as telas, basta logar com um desses usuários:
| Email             | Senha     | Role (permissão)  |
| ----------------- | --------- | ----------------- |
| admin@mottu.com   | 123456    | ADMIN             |
| user@mottu.com    | 123456    | USER              |

Ao cadastrar um novo usuário, o mesmo receberá a Role de `USER` automaticamente.

![](images/login.png)
![](images/cadastroUsuario.png)


Para acessar via Postman, coloque o Email e Senha na sessão de Basic Auth antes de cada requisição:

![](images/loginPostman.png)


---

## Configuração do Spring Initializr
![Imagem do projeto](images/springinitializr.png)
 
**Dependências:**
- Spring Web: Por se tratar de uma API Rest.
- Spring Boot DevTools: Recursos que ajudam na produtividade.
- Spring Data JPA: Acessar e manipular bancos de dados.
- Lombok: Gerar automaticamente códigos repetitivos (mais otimizado).
- Validation: Biblioteca de validação usada nos DTOs.
- MS SQL: SQL Server na Azure.
- Thymeleaf: Renderização de página HTML no navegador.
- Spring Security: Autenticação e acesso aos endpoints.
- Flyway: Versionamento do banco de dados.
 
**Estrutura do Banco de Dados:**
![](images/banco.png)
 
**Entidades:** Patio, Zona, Moto, Sensor, Historico
 
---
 
## Pacotes utilizados e suas funções
 
### Arquitetura: MVC
 
**1. Model:**
- Entity → Mapeia tabelas e relacionamentos no banco.
- Repository → Abstrai o acesso e consultas ao banco.
- Service → Implementa lógica e regras de negócio.
- Exception → Constrói exceptions personalizadas.
- DTO → Definição dos modelos de Request e Response de cada entidade.
- Security → Definição das regras de segurança e permissões.

**2. View:**
- Static → CSS, JS e imagens
- Templates → Fragmentos e Páginas HTML
 
**3. Controller:**
- Controller HTTP → Expõe endpoints REST e manipula requisições/respostas.
- Controller WEB → Expõe endpoints e mapeia páginas WEB.
 
---
 
 
## Endpoints
 
### JSON:
 
**1. Patio:**
 
| Método | URI            | Descrição                   |
| ------ | -------------- | --------------------------- |
| GET    | `/api/patios`      | Lista todos os pátios       |
| GET    | `/api/patios/{id}` | Busca um pátio pelo ID      |
| POST   | `/api/patios`      | Cria um novo pátio          |
| PUT    | `/api/patios/{id}` | Atualiza um pátio existente |
| DELETE | `/api/patios/{id}` | Deleta um pátio pelo ID     |
 
**2. Zona:**
 
| Método | URI           | Descrição                   |
| ------ | ------------- | --------------------------- |
| GET    | `/api/zonas`      | Lista todas as zonas        |
| GET    | `/api/zonas/{id}` | Busca uma zona pelo ID      |
| POST   | `/api/zonas`      | Cria uma nova zona          |
| PUT    | `/api/zonas/{id}` | Atualiza uma zona existente |
| DELETE | `/api/zonas/{id}` | Deleta uma zona pelo ID     |
 
**3. Moto:**
 
| Método | URI                            | Descrição                                        |
| ------ | ------------------------------ | ------------------------------------------------ |
| GET    | `/api/motos`                       | Lista todas as motos                             |
| GET    | `/api/motos/{id}`                  | Busca uma moto pelo ID                           |
| GET    | `/api/motos/buscar?marca=&modelo=` | Busca motos por marca e/ou modelo (query params) |
| POST   | `/api/motos`                       | Cria uma nova moto                               |
| PUT    | `/api/motos/{id}`                  | Atualiza uma moto existente                      |
| DELETE | `/api/motos/{id}`                  | Deleta uma moto pelo ID                          |
 
**4. Sensor:**
| Método | URI              | Descrição                    |
| ------ | ---------------- | ---------------------------- |
| GET    | `/api/sensores`      | Lista todos os sensores      |
| GET    | `/api/sensores/{id}` | Busca um sensor pelo ID      |
| POST   | `/api/sensores`      | Cria um novo sensor          |
| PUT    | `/api/sensores/{id}` | Atualiza um sensor existente |
| DELETE | `/api/sensores/{id}` | Deleta um sensor pelo ID     |
 
**5. Historico:**
| Método | URI                         | Descrição                               |
| ------ | --------------------------- | --------------------------------------- |
| GET    | `/api/historicos`               | Lista todos os históricos               |
| GET    | `/api/historicos/{id}`          | Busca um histórico pelo ID              |
| GET    | `/api/historicos/moto/{motoId}` | Lista históricos de uma moto específica |
| POST   | `/api/historicos`               | Cria um novo registro de histórico      |
| PUT    | `/api/historicos/{id}`          | Atualiza um histórico existente         |
| DELETE | `/api/historicos/{id}`          | Deleta um histórico pelo ID             |
 
---
 
### HTML:
 
**1. Patio:**
 
| Método | URI                       | Descrição                                       | View retornada            |
|--------|---------------------------|-------------------------------------------------|---------------------------|
| GET    | `/web/patios/listar`      | Lista todos os patios                           | `patio/patio-lista`      |
| GET    | `/web/patios/cadastrar`  | Exibe o formulário de cadastro/edição           | `patio/patio-form`        |
| GET    | `/web/patios/editar/{id}` | Exibe o formulário preenchido para edição       | `patio/patio-form`        |
| GET    | `/web/patios/excluir/{id}`| Exclui pátio e redireciona para listagem        | Redirect → `/listar`      |
 
**2. Zona:**
 
| Método | URI                       | Descrição                                       | View retornada            |
|--------|---------------------------|-------------------------------------------------|---------------------------|
| GET    | `/web/zonas/listar`      | Lista todas as zonas                           | `zona/zona-lista`      |
| GET    | `/web/zonas/cadastrar`  | Exibe o formulário de cadastro/edição           | `zona/zona-form`        |
| GET    | `/web/zonas/editar/{id}` | Exibe o formulário preenchido para edição       | `zona/zona-form`        |
| GET    | `/web/zonas/excluir/{id}`| Exclui zona e redireciona para listagem        | Redirect → `/listar`      |
 
**3. Moto:**
 
| Método | URI                       | Descrição                                       | View retornada            |
|--------|---------------------------|-------------------------------------------------|---------------------------|
| GET    | `/web/motos/listar`      | Lista todas as motos                           | `moto/moto-lista`      |
| GET    | `/web/motos/cadastrar`  | Exibe o formulário de cadastro/edição           | `moto/moto-form`        |
| GET    | `/web/motos/editar/{id}` | Exibe o formulário preenchido para edição       | `moto/moto-form`        |
| GET    | `/web/motos/excluir/{id}`| Exclui motos e redireciona para listagem        | Redirect → `/listar`      |
 
 
**4. Sensor:**
 
| Método | URI                       | Descrição                                       | View retornada            |
|--------|---------------------------|-------------------------------------------------|---------------------------|
| GET    | `/web/sensores/listar`      | Listar todos os sensores                           | `sensor/sensor-lista`      |
| GET    | `/web/sensores/cadastrar`  | Exibe o formulário de cadastro/edição           | `sensor/sensor-form`        |
| GET    | `/web/sensores/editar/{id}` | Exibe o formulário preenchido para edição       | `sensor/sensor-form`        |
| GET    | `/web/sensores/excluir/{id}`| Exclui sensor e redireciona para listagem        | Redirect → `/listar`      |
 
 
**5. Historico:**
 
| Método | URI                       | Descrição                                       | View retornada            |
|--------|---------------------------|-------------------------------------------------|---------------------------|
| GET    | `/web/historicos/listar`      | Lista todos os historicos                           | `historico/historico-lista`      |
| GET    | `/web/historicos/cadastrar`  | Exibe o formulário de cadastro/edição           | `historico/historico-form`        |
| GET    | `/web/historicos/editar/{id}` | Exibe o formulário preenchido para edição       | `historico/historico-form`        |
| GET    | `/web/historicos/excluir/{id}`| Exclui histórico e redireciona para listagem        | Redirect → `/listar`      |
 
 
 
---
 
 
## Exemplos de Requisições
 
- Exemplo de Request **Patio**:
````json
{
    "nome": "Mottu Space 3",
    "endereco": "Av. Butantan, 552"
}
````
 
 
- Exemplo de Request **Zona**:
````json
{
    "tipo": "Reparo",
    "qtdVaga": 10,
    "patioId": 1
}
````
 
- Exemplo de Request **Moto**:
````json
{
    "placa": "1234567",
    "chassi": "12345678901234567",
    "modelo": "Pop"
}
````
 
- Exemplo de Request **Sensor**:
````json
{
  "localizacao": "Entrada principal",
  "data": "2025-05-23",
  "hora": "22:30"
}
````
 
- Exemplo de Request **Historico**:
````json
{
  "posicao": 2,
  "motoId": 2,
  "zonaId": 1,
  "sensorId": 2
}
````
 
---
 
## Instruções para Executar o Projeto
 
1. Acesse o link de Deploy;
2. Adicione o endpoint desejado;
3. A aplicação já está configurada para usar um banco em nuvem; 
4. Teste as requisições via Postman, Aplicativo Mobile ou Interface Web;

---
 
## Contato

Para dúvidas, melhorias ou sugestões relacionadas ao projeto, entre em contato :)

