# 🎮 Game Library API

REST API for managing a personal video game library. It supports creating, listing, searching, updating, favoriting, and deleting games. Each game can include descriptive metadata and an `imageUrl` for its cover art.

> This repository contains the **backend**. The front-end (HTML, CSS, and JavaScript) is maintained separately: [game-library-frontend](https://github.com/arthurSchgg/game-library-frontend).

- **Production API:** https://game-library-backend-ebrh.onrender.com
- **Production Swagger UI:** https://game-library-backend-ebrh.onrender.com/swagger-ui.html

## 🛠 Tech Stack

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| Spring Boot 4.1.1 | Application framework |
| Spring Web MVC | REST endpoints and HTTP handling |
| Spring Data JPA / Hibernate | Persistence and ORM |
| Jakarta Bean Validation | Validation of request bodies |
| MySQL 8.4 | Relational database |
| Springdoc OpenAPI 2.8.13 | OpenAPI specification and Swagger UI |
| Lombok | Reduces boilerplate in the JPA entity |
| Maven | Build and dependency management |
| Docker / Docker Compose | Containerized application and local database |

## 🏗 Architecture

The application is organized in layers, with each layer responsible for a distinct part of the request flow:

```text
Client (front-end)
      │ HTTP / JSON
      ▼
Controller (GameController)
      │ validates request DTOs and builds HTTP responses
      ▼
Service (GameService)
      │ business rules and transaction boundaries
      ├──────────► Mapper (GameMapper)
      │             converts between DTOs and the Game entity
      ▼
Repository (GameRepository)
      │ Spring Data JPA
      ▼
MySQL
```

The `Game` entity is used internally for persistence. The API receives and returns DTOs, rather than exposing the entity directly. `GlobalExceptionHandler` centralizes error responses, and `WebConfig` defines the allowed browser origins.

### Project structure

```text
src/main/java/com/br/game_library
├── config/
│   ├── OpenApiConfig.java
│   ├── RestClientConfig.java
│   └── WebConfig.java
├── controller/
│   └── GameController.java
├── dto/
│   ├── GameRequestDTO.java
│   ├── GameRequestUpdateDTO.java
│   └── GameResponseDTO.java
├── entity/
│   └── Game.java
├── exception/
│   ├── GameNotFoundException.java
│   └── GlobalExceptionHandler.java
├── mapper/
│   └── GameMapper.java
├── repository/
│   └── GameRepository.java
└── service/
    └── GameService.java
```

## 🚀 Run locally

### Prerequisites

- Java 21
- Docker and Docker Compose
- Git (to clone the repository)
- Maven is optional because the repository includes the Maven Wrapper

### 1. Start MySQL

From the project root, run:

```bash
docker compose -f docker/docker-compose.yml up -d
```

The Compose file starts MySQL 8.4 in the `game-library-mysql` container. The database is named `game_library`; host port `3333` maps to the container's port `3306`. The `mysql_data` volume keeps the database data across container restarts.

### 2. Start the application

Run the command from the project root:

**Linux / macOS**

```bash
./mvnw spring-boot:run
```

**Windows (Command Prompt)**

```bat
mvnw.cmd spring-boot:run
```

**Windows (PowerShell)**

```powershell
.\mvnw.cmd spring-boot:run
```

You can also run `GameLibraryApplication` from your IDE.

### 3. Open the API

- **Base URL:** `http://localhost:8085`
- **Swagger UI:** `http://localhost:8085/swagger-ui.html`
- **OpenAPI JSON:** `http://localhost:8085/v3/api-docs`

To stop the database container, run `docker compose -f docker/docker-compose.yml down`. This preserves the named volume. To also delete the local database data, use `docker compose -f docker/docker-compose.yml down -v`.

## ⚙️ Configuration

The application reads its port and database connection from environment variables. Defaults are intended for local development.

| Environment variable | Default | Purpose |
|---|---|---|
| `PORT` | `8085` | HTTP port used by Spring Boot |
| `SPRING_DATASOURCE_URL` | `jdbc:mysql://localhost:3333/game_library` | JDBC connection URL |
| `SPRING_DATASOURCE_USERNAME` | `root` | Database username |
| `SPRING_DATASOURCE_PASSWORD` | `root` | Database password |

The current JPA configuration uses `spring.jpa.hibernate.ddl-auto=update`, so Hibernate updates the schema as needed. SQL statements and formatted SQL are also enabled in the application properties.

> **Security note:** `root` / `root` are development defaults only. Set secure environment variables for deployed environments, and never commit production credentials to the repository.

## 🌐 CORS

The backend currently allows browser requests from these origins:

- `http://localhost:5500` (local Live Server)
- `https://game-library-frontend-*.vercel.app` (matching Vercel deployments of the front-end)

The configured methods are `GET`, `POST`, `PUT`, `PATCH`, `DELETE`, and `OPTIONS`. If you use a different local port or host the front-end on another domain, update `allowedOriginPatterns` in `config/WebConfig.java`.

## 📖 API endpoints

All game endpoints use the `/v1/game-library` base path.

| Method | Endpoint | Description | Success response |
|---|---|---|---|
| `GET` | `/v1/game-library` | Lists every registered game. Returns an empty array if the library is empty. | `200 OK` |
| `POST` | `/v1/game-library` | Registers a game. New games start with `favorite: false`. The response includes a `Location` header for the created resource. | `201 Created` |
| `GET` | `/v1/game-library/{id}` | Finds one game by its ID. | `200 OK` |
| `GET` | `/v1/game-library/search?title=zelda` | Finds games whose titles contain the given text, ignoring case. | `200 OK` |
| `PUT` | `/v1/game-library/{id}` | Replaces the editable data of an existing game. The favorite status is preserved. | `200 OK` |
| `PATCH` | `/v1/game-library/{id}/favorite` | Toggles the game's favorite status between `true` and `false`. | `200 OK` |
| `DELETE` | `/v1/game-library/{id}` | Deletes a game by its ID. | `204 No Content` |

### Example request body

Use this JSON with `POST /v1/game-library` to register a game:

```json
{
  "title": "Cyberpunk 2077",
  "imageUrl": "https://cdn.akamai.steamstatic.com/steam/apps/1091500/header.jpg",
  "genre": "Role-playing",
  "platform": "PC",
  "releaseYear": 2020,
  "rating": 9.0,
  "description": "An open-world role-playing game."
}
```

`imageUrl` is optional. The API stores the string provided by the client; it does not download, resize, or verify the image itself.

### Example response

A successful response from the game endpoints has this shape:

```json
{
  "id": 1,
  "title": "Cyberpunk 2077",
  "imageUrl": "https://cdn.akamai.steamstatic.com/steam/apps/1091500/header.jpg",
  "genre": "Role-playing",
  "platform": "PC",
  "releaseYear": 2020,
  "rating": 9.0,
  "description": "An open-world role-playing game.",
  "favorite": false
}
```

### Update behavior (`PUT`)

`PUT /v1/game-library/{id}` overwrites the editable fields: `title`, `imageUrl`, `genre`, `platform`, `releaseYear`, `rating`, and `description`. Send the current values for any fields you want to keep. If an optional field is omitted or sent as `null`, it can be saved as `null`. The `favorite` field is not modified by `PUT`; use the favorite endpoint instead.

### Validation rules

Validation runs on the request DTOs before the service processes a request.

| Field | Rules |
|---|---|
| `title` | Required; cannot be blank; maximum 150 characters |
| `imageUrl` | Optional string; no URL-format validation is currently applied |
| `genre` | Optional; maximum 100 characters |
| `platform` | Optional; maximum 50 characters |
| `releaseYear` | Optional; must be between 1970 and 2027, inclusive |
| `rating` | Optional; must be between `0.0` and `10.0`, inclusive |
| `description` | Optional text |
| `favorite` | Set to `false` when a game is created; changed with the favorite endpoint |

The API returns `400 Bad Request` for invalid request-body fields, `404 Not Found` when a requested game ID does not exist, and `500 Internal Server Error` for unexpected internal errors. Error responses use Spring's `ProblemDetail` format.

## 🎮 Current scope and Steam data

The current backend revision provides the game-library CRUD endpoints listed above. **Steam search/detail endpoints (`/v1/steam/...`) are not implemented in this revision**, and there is no active Steam lookup flow in the backend.

The `Game` JPA entity still contains a `steamAppId` field, but the current request and response DTOs do not expose it. The `imageUrl` field can still store a cover-image URL supplied by the front-end or another source. The backend stores and returns that URL; displaying the image and preserving its aspect ratio are responsibilities of the front-end.

## 👤 Author

**Arthur Miguel Schlichting**
