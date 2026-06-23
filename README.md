# TechDispatch — Backend

Spring Boot REST API for **TechDispatch**, a platform for scheduling and managing
technician visits. This is the server; the web client lives in the companion
[frontend repo](https://github.com/sokinkin/techdispatch-frontend).

## Features

- **JWT authentication** (login + registration) with role-based access
- **Two roles** — `CUSTOMER` and `TECHNICIAN` — each with its own data and actions
- **Visit lifecycle** — schedule, reschedule, and move through `SCHEDULED → IN_PROGRESS → COMPLETED`
- **Technician availability** management and conflict checking (no double-booking)
- **Customers, locations, technicians, and jobs** CRUD
- **Interactive API docs** via Swagger UI / OpenAPI

## Tech stack

| | |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4.0.5 |
| Persistence | Spring Data JPA / Hibernate |
| Database | MySQL |
| Security | Spring Security + JWT (jjwt) |
| API docs | springdoc-openapi (Swagger UI) |
| Build | Maven (wrapper included) |

## Prerequisites

- **JDK 21**
- **MySQL** running locally with a database named `techdispatch`
  (e.g. via XAMPP). The default connection is `root` with no password — see
  `src/main/resources/application.properties` to change it.

```sql
CREATE DATABASE techdispatch;
```

The schema is created/updated automatically on startup (`spring.jpa.hibernate.ddl-auto=update`).

## Getting started

```bash
# run the API (http://localhost:8080)
./mvnw spring-boot:run          # macOS/Linux
mvnw.cmd spring-boot:run        # Windows
```

On Windows with Git Bash you can also use `./mvnw.cmd spring-boot:run`.

### Build a runnable jar

```bash
./mvnw clean package
java -jar target/my-springboot-platform-0.0.1-SNAPSHOT.jar
```

## API documentation

With the app running:

- **Swagger UI:** http://localhost:8080/api-ui
- **OpenAPI JSON:** http://localhost:8080/api

A full endpoint reference is in [`ENDPOINTS.md`](ENDPOINTS.md).

## Authentication

All endpoints except `/auth/**` require a JWT. Obtain one from `/auth/login` or
`/auth/register`, then send it on each request:

```
Authorization: Bearer <token>
```

### Seeded test logins

All seeded logins use the password **`password123`**.

| Role | Email |
|------|-------|
| Technician | `nikos@techdispatch.gr` |
| Customer | `maria@acme.gr` |

## Configuration

Key settings in `src/main/resources/application.properties`:

| Property | Purpose |
|----------|---------|
| `spring.datasource.url` | MySQL connection URL (`jdbc:mysql://localhost/techdispatch`) |
| `spring.datasource.username` / `password` | DB credentials |
| `spring.jpa.hibernate.ddl-auto` | Schema management (`update`) |
| `app.jwt.secret` | HS256 signing key (move to an env var for real deployments) |
| `app.jwt.expiration-ms` | Token lifetime |
| `app.seed.default-password` | Password given to seeded logins |

## Project structure

```
src/main/java/com/techdispatch/my_springboot_platform/
  Controllers/   REST controllers (Auth, Customer, Technician, Visit, Job, Location, Availability)
  Services/      Business logic
  Repositories/  Spring Data JPA repositories
  Models/        JPA entities (Account, Customer, Technician, Location, Visit, Job, Availability)
  DTO/           Response/request DTOs
  Config/        Security, JWT, CORS, and the data seeder
```
