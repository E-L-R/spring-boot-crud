# Spring Boot User CRUD Application

A simple CRUD web application built with:

- Spring Boot
- Spring Data JPA
- Thymeleaf
- MySQL
- Docker / Docker Compose

The app lets you create, edit, list, and delete users through a browser UI.

## Project structure

- Main application class: `src/main/java/SpringBootApplication.java`
- Controller: `src/main/java/controller/UserController.java`
- Repository: `src/main/java/dao/UserRepository.java`
- Service: `src/main/java/service/UserServiceImpl.java`
- Entity: `src/main/java/model/User.java`
- Templates: `src/main/resources/templates/`
- Boot config: `src/main/resources/application.properties`

## Requirements

- Java 21
- Maven
- Docker and Docker Compose

## Local configuration

The application reads database settings from environment variables:

- `DB_URL`
- `DB_USER`
- `DB_PASSWORD`

`src/main/resources/application.properties` maps those into Spring Boot datasource properties.

## Run with Docker

This is the recommended way to run the full stack.

```bash
docker compose up --build
```

The compose setup starts:

- `db`: MySQL 9.0
- `app`: Spring Boot application

Then open:

```text
http://localhost:8080
```

## Persistence

Database data is stored in the named Docker volume `mysql_data`.

That means the data survives:

- container restarts
- `docker compose down`
- rebuilding the app image

Data is removed only if you explicitly delete the volume, for example with:

```bash
docker compose down -v
```

## Run locally without Docker

If you want to run the app directly from Maven, make sure MySQL is available and the environment variables are set.

Example:

```bash
export DB_URL='jdbc:mysql://localhost:3306/user_crud_db?serverTimezone=UTC'
export DB_USER='root'
export DB_PASSWORD=''
mvn spring-boot:run
```

Then open:

```text
http://localhost:8080
```

## Docker details

`Dockerfile` uses a two-stage build:

1. build the Spring Boot jar with Maven
2. run it on a JRE image with `java -jar`

`docker-compose.yml` also starts a MySQL container and injects the datasource environment variables into the app container.

## Notes

- The current entry point is in the default package. That works here because package scanning is explicit.
- The app uses Thymeleaf templates under `src/main/resources/templates`, which is Spring Boot’s default location.
