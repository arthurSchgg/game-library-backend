# 🎮 Game Library API

REST API to manage a personal game library. It allows you to create, query, update, favorite, and delete games, and it uses the **Steam Store** to find the *app ID* and the *cover image* of each game based on its name.

> This repository contains the **backend**. The front-end (HTML, CSS, and JavaScript) lives in a separate repository: `https://github.com/arthurSchgg/game-library-frontend`.

🌐 **API in production:** `https://game-library-backend-ebrh.onrender.com/`

---

## 🛠 Tech Stack

| Technology | Purpose |
|---|---|
| Java 21 | Language |
| Spring Boot 4.1.1 | Framework (Web MVC, Data JPA, Validation) |
| MySQL 8.4 | Database (via Docker) |
| Hibernate / JPA | Persistence |
| Springdoc OpenAPI | Swagger documentation |
| RestClient | Steam Store API consumption |
| Lombok | Boilerplate reduction on the entity |
| Maven | Build and dependencies |

---

## 🏗 Architecture

The project is split into layers, each with a single responsibility:

```
Client (front-end)
      │  JSON
      ▼
┌─────────────┐   validates (@Valid) and returns ResponseEntity
│ Controller  │   GameController · SteamController
└─────┬───────┘
      ▼
┌─────────────┐   business rules, converts Entity ⇄ DTO
│  Service    │   GameService · SteamService
└─────┬───────┘
      ├──► Mapper ───► converts Game ⇄ DTOs
      ▼
┌─────────────┐
│ Repository  │   GameRepository (Spring Data JPA) ──► MySQL
└─────────────┘
```

**Folder structure**

```
src/main/java/com/br/game_library
├── config/        # CORS, RestClient, and OpenAPI
├── controller/    # game/ and steam/
├── dto/           # game/ and steam/ (records)
├── entity/        # Game
├── mapper/        # GameMapper
├── repository/    # GameRepository
└── service/       # game/ and steam/
```

The `Game` entity never leaves the Service layer: the Controller only sees **DTOs**.

---

## 🚀 How to Run

### Prerequisites

- Java 21
- Docker and Docker Compose
- (Optional) Maven, since the project already includes the `mvnw` wrapper

### Step by step

**1. Start the database**

```bash
cd docker
docker compose up -d
```

This creates the MySQL container `game-library-mysql` on port **3333** (host machine) with the database `game_library`.

**2. Run the application**

```bash
./mvnw spring-boot:run
```

(on Windows: `mvnw.cmd spring-boot:run`) or run the `GameLibraryApplication` class from your IDE.

**3. Access**

- API: `http://localhost:8085`
- Swagger UI: `http://localhost:8085/swagger-ui.html`

### Configuration (`application.properties`)

| Property | Value |
|---|---|
| `server.port` | `8085` |
| `spring.datasource.url` | `jdbc:mysql://localhost:3333/game_library` |
| `spring.datasource.username` / `password` | `root` / `root` |
| `spring.jpa.hibernate.ddl-auto` | `update` (the `game` table is created/adjusted automatically) |

> ⚠️ The credentials above are **for local development only**. In any other environment, use environment variables and never commit real passwords.

### CORS

`WebConfig` allows browser calls only from `http://localhost:5500` and `http://127.0.0.1:5500` (the default Live Server port). If your front-end uses a different port, adjust `allowedOrigins` in `config/WebConfig.java`.

---

## 📖 Interactive Documentation (Swagger)

With the application running, open:

```
http://localhost:8085/swagger-ui.html
```

There you can see all endpoints, input and output formats, response codes, and **test requests directly from the browser** (*Try it out* button). The JSON specification is available at `/v3/api-docs`.

---

## 🗃 Data Model and Validations

`game` table:

| Field | Type | Rules |
|---|---|---|
| `id` | `Long` | Auto-generated |
| `title` | `String` | **Required**, up to 150 characters |
| `steamAppId` | `Long` | Optional |
| `imageUrl` | `String` | Optional |
| `genre` | `String` | Optional, up to 100 characters |
| `platform` | `String` | Optional, up to 50 characters |
| `releaseYear` | `Integer` | Optional, between 1970 and 2027 |
| `rating` | `Double` | Optional, between 0.0 and 10.0 |
| `description` | `String` | Optional (`TEXT`) |
| `favorite` | `Boolean` | Starts as `false` |

The rules are enforced with `@Valid` on the input DTOs: invalid data is blocked **before** reaching the business logic, and the request returns `400 Bad Request`.

> On `PUT`, all fields are overwritten (including `steamAppId` and `imageUrl`). Resend the current values of any fields that should not change. The favorite status is **not** changed by `PUT`: use `PATCH` for that.

---

## 🎯 Steam Integration

The browser **cannot** call Steam directly, because the store does not allow CORS. That is why the API acts as a middleman (proxy):

```
Front-end ──► Game Library API ──► Steam Store
 (JS)          :8085               store.steampowered.com
```

**Flow to register a game with a cover image:**

1. The user types the game name.
2. The front-end calls `GET /v1/steam/search?name=...` and displays the results.
3. The user picks one (the search often returns DLCs and soundtracks as well).
4. The front-end calls `GET /v1/steam/{appId}` to get `header_image` (a high-resolution cover).
5. The front-end fills in `title`, `steamAppId`, and `imageUrl` and sends `POST /v1/game-library`.

> Corporate networks with firewalls may block `store.steampowered.com`. In that case the API responds with `500` (SSL handshake error) on the Steam routes. The `game-library` routes keep working normally.

## 👤 Author

**Arthur Miguel Schlichting**
