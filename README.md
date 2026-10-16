# 🎮 Game Library API

API REST para gerenciar uma biblioteca pessoal de jogos. Permite cadastrar, consultar, atualizar, favoritar e remover jogos, e usa a **Steam Store** para encontrar o *app ID* e a *imagem de capa* de cada jogo a partir do nome.

> Este repositório contém o **backend**. O front-end (HTML, CSS e JavaScript) fica em um repositório separado: ```https://github.com/arthurSchgg/game-library-frontend```.

🌐 **API em produção:** `https://game-library-backend-ebrh.onrender.com/`

---

## 🛠 Tecnologias

| Tecnologia | Uso |
|---|---|
| Java 21 | Linguagem |
| Spring Boot 4.1.1 | Framework (Web MVC, Data JPA, Validation) |
| MySQL 8.4 | Banco de dados (via Docker) |
| Hibernate / JPA | Persistência |
| Springdoc OpenAPI | Documentação Swagger |
| RestClient | Consumo da API da Steam Store |
| Lombok | Redução de código repetitivo na entidade |
| Maven | Build e dependências |

---

## 🏗 Arquitetura

O projeto é dividido em camadas, e cada uma tem uma única responsabilidade:

```
Cliente (front-end)
      │  JSON
      ▼
┌─────────────┐   valida (@Valid) e devolve ResponseEntity
│ Controller  │   GameController · SteamController
└─────┬───────┘
      ▼
┌─────────────┐   regras de negócio, converte Entidade ⇄ DTO
│  Service    │   GameService · SteamService
└─────┬───────┘
      ├──► Mapper ───► converte Game ⇄ DTOs
      ▼
┌─────────────┐
│ Repository  │   GameRepository (Spring Data JPA) ──► MySQL
└─────────────┘
```

**Estrutura de pastas**

```
src/main/java/com/br/game_library
├── config/        # CORS, RestClient e OpenAPI
├── controller/    # game/ e steam/
├── dto/           # game/ e steam/ (records)
├── entity/        # Game
├── mapper/        # GameMapper
├── repository/    # GameRepository
└── service/       # game/ e steam/
```

A entidade `Game` nunca sai do Service: o Controller só enxerga **DTOs**.

---

## 🚀 Como executar

### Pré-requisitos

- Java 21
- Docker e Docker Compose
- (Opcional) Maven, pois o projeto já inclui o `mvnw`

### Passo a passo

**1. Subir o banco de dados**

```bash
cd docker
docker compose up -d
```

Isso cria o MySQL `game-library-mysql` na porta **3333** (do computador) com o banco `game_library`.

**2. Executar a aplicação**

```bash
./mvnw spring-boot:run
```

(no Windows: `mvnw.cmd spring-boot:run`) ou execute a classe `GameLibraryApplication` pela IDE.

**3. Acessar**

- API: `http://localhost:8085`
- Swagger UI: `http://localhost:8085/swagger-ui.html`

### Configuração (`application.properties`)

| Propriedade | Valor |
|---|---|
| `server.port` | `8085` |
| `spring.datasource.url` | `jdbc:mysql://localhost:3333/game_library` |
| `spring.datasource.username` / `password` | `root` / `root` |
| `spring.jpa.hibernate.ddl-auto` | `update` (a tabela `game` é criada/ajustada automaticamente) |

> ⚠️ As credenciais acima são **somente para desenvolvimento local**. Em outro ambiente, use variáveis de ambiente e nunca versione senhas reais.

### CORS

O `WebConfig` libera chamadas do navegador apenas para `http://localhost:5500` e `http://127.0.0.1:5500` (porta padrão do Live Server). Se o seu front usar outra porta, ajuste `allowedOrigins` em `config/WebConfig.java`.

---

## 📖 Documentação interativa (Swagger)

Com a aplicação rodando, abra:

```
http://localhost:8085/swagger-ui.html
```

Lá é possível ver todos os endpoints, os formatos de entrada e saída, os códigos de resposta e **testar as requisições direto pelo navegador** (botão *Try it out*). A especificação em JSON fica em `/v3/api-docs`.

---

## 🗃 Modelo de dados e validações

Tabela `game`:

| Campo | Tipo | Regras |
|---|---|---|
| `id` | `Long` | Gerado automaticamente |
| `title` | `String` | **Obrigatório**, até 150 caracteres |
| `steamAppId` | `Long` | Opcional |
| `imageUrl` | `String` | Opcional |
| `genre` | `String` | Opcional, até 100 caracteres |
| `platform` | `String` | Opcional, até 50 caracteres |
| `releaseYear` | `Integer` | Opcional, entre 1970 e 2027 |
| `rating` | `Double` | Opcional, entre 0.0 e 10.0 |
| `description` | `String` | Opcional (`TEXT`) |
| `favorite` | `Boolean` | Começa como `false` |

As regras são aplicadas com `@Valid` nos DTOs de entrada: dados inválidos são barrados **antes** de chegar à regra de negócio, e a requisição retorna `400 Bad Request`.

> No `PUT`, todos os campos são sobrescritos (inclusive `steamAppId` e `imageUrl`). Reenvie os valores atuais dos campos que não devem mudar. O status de favorito **não** é alterado pelo `PUT`: use o `PATCH`.

---

## 🎯 Integração com a Steam

O navegador **não pode** chamar a Steam diretamente, porque a loja não libera CORS. Por isso a API funciona como intermediária (proxy):

```
Front-end ──► Game Library API ──► Steam Store
 (JS)          :8085               store.steampowered.com
```

**Fluxo para cadastrar um jogo com capa:**

1. O usuário digita o nome do jogo.
2. O front chama `GET /v1/steam/search?name=...` e mostra os resultados.
3. O usuário escolhe um (a busca costuma trazer DLCs e trilhas sonoras também).
4. O front chama `GET /v1/steam/{appId}` para obter `header_image` (capa em boa resolução).
5. O front preenche `title`, `steamAppId` e `imageUrl` e envia `POST /v1/game-library`.

> Redes corporativas com firewall podem bloquear `store.steampowered.com`. Nesse caso a API responde `500` (erro de handshake SSL) nas rotas da Steam. As rotas de `game-library` continuam funcionando normalmente.

## 👤 Autor

**Arthur Miguel Schlichting**  
