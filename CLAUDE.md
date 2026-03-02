# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Start the database
docker-compose up -d

# Run the application
./mvnw spring-boot:run

# Build
./mvnw clean package

# Run all tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=ClassName

# Run a single test method
./mvnw test -Dtest=ClassName#methodName
```

## Configuration

Copy `src/main/resources/application.properties.example` to `application.properties` and fill in:
- `spring.datasource.url/username/password` — PostgreSQL connection (Docker defaults: `admin`/`admin123`, DB `student_app_db`)
- `jwt.secret` — JWT signing secret

The app uses `spring.jpa.hibernate.ddl-auto=update`, so the schema is auto-managed.

API docs available at `/swagger-ui.html` and `/v3/api-docs` (no auth required).

## Architecture

This project follows **Hexagonal Architecture (Ports & Adapters)**. All business logic is isolated from infrastructure concerns.

```
domain/
  model/          -- Pure domain entities (no JPA annotations)
  port/in/        -- Use case interfaces (input ports)
  port/out/       -- Repository/service interfaces (output ports)
  service/        -- Use case implementations (XxxServiceImpl)

infra/
  adapters/in/web/         -- REST controllers, DTOs, DTO mappers
  adapters/out/persistance/ -- JPA entities, JPA repositories, persistence mappers, repository adapters
  adapters/out/email/       -- JavaMail adapter
  adapters/out/storage/     -- AWS S3 adapter
  config/                   -- Security, JWT, S3, CORS, exception handling
```

### Domain Models

Domain entities use **private constructors with static factory methods**:
- `Entity.create(...)` — for creating new instances (generates UUID, sets timestamps)
- `Entity.fromState(...)` — for reconstituting from persistence

Setters call `touch()` to update `updatedAt`. No Lombok on domain models.

### Two Mapper Layers

There are two distinct mapper layers that should not be confused:

1. **Persistence mappers** (`infra/adapters/out/persistance/mapper/`) — MapStruct `@Mapper(componentModel = "spring")` abstract classes converting between domain models and JPA entities. Circular dependencies (e.g. Subject ↔ User) are handled with `@Autowired @Lazy` on injected sibling mappers.

2. **DTO mappers** (`infra/adapters/in/web/mapper/`) — Plain `@Component` classes converting between domain models and REST DTOs. Not MapStruct.

### Request Flow

`Controller → UseCase (port/in) → ServiceImpl → RepositoryPort (port/out) → RepositoryAdapter → JpaRepository`

Controllers inject the use case interface, not the service implementation. Repository adapters implement the output port interface, bridging domain and JPA.

### Security

JWT-based stateless auth. Public endpoints: `/auth/**`, Swagger. Admin-only: `/admin/**`. CORS is configured for `http://localhost:5173` (frontend dev server).

Password reset is email-based via `PasswordResetToken`.

### Adding a New Domain Feature

Follow the pattern established by any existing feature (e.g. `Subject`):
1. Domain model in `domain/model/`
2. Use case interface in `domain/port/in/`
3. Repository port interface in `domain/port/out/`
4. Service implementation in `domain/service/`
5. JPA entity in `infra/adapters/out/persistance/entity/`
6. MapStruct persistence mapper in `infra/adapters/out/persistance/mapper/`
7. JPA repository in `infra/adapters/out/persistance/repository/`
8. Repository adapter in `infra/adapters/out/persistance/`
9. DTOs and DTO mapper in `infra/adapters/in/web/dto/` and `mapper/`
10. Controller in `infra/adapters/in/web/`

### Testing Strategy

- Focus on Unit Tests for the Domain Layer (`domain/model/` and `domain/service/`).
- Use JUnit 5 and Mockito.
- Do not write integration tests (`@SpringBootTest`, `@DataJpaTest`, `@WebMvcTest`) unless explicitly requested.
- Every new Domain Model or ServiceImpl generated must be accompanied by its respective test class.

### Current Business Focus: Gamification & Productivity

We are shifting the app from a simple CRUD to a gamified productivity tool.
- **User Gamification:** `User` entity now manages `currentXp`, `currentLevel`, `coins`, and `currentStreak`. Leveling up requires progressively more XP.
- **Activity Checklists:** `Activity` entity now contains a simple to-do list instead of relational subtasks. This is mapped in JPA using a JSON/JSONB column via Hibernate 6 `@JdbcTypeCode(SqlTypes.JSON)`. XP is ONLY awarded when the entire `Activity` is marked as completed.
- **Focus Sessions:** A new feature to track study time (Pomodoro) and award XP based on `durationSeconds`.