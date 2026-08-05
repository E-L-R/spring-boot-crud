# Spring Boot User CRUD Application

A CRUD web application with role-based security built with:

- Spring Boot
- Spring Security
- Spring Data JPA
- Thymeleaf
- MySQL
- Docker / Docker Compose

The app lets an **admin** create, edit, list, and delete users through a browser UI. Regular **users** can view their own profile page.

## Project structure

```
src/main/java/
├── SpringBootApplication.java
├── configs/
│   ├── DataInitializer.java      # Seeds default roles and users on startup
│   ├── MvcConfig.java            # View controller mappings
│   ├── SuccessUserHandler.java   # Post-login redirect logic
│   └── WebSecurityConfig.java    # URL security rules, BCrypt, form login
├── controller/
│   ├── UserController.java       # CRUD endpoints under /admin/
│   └── UserPageController.java   # /user profile page
├── dao/
│   ├── RoleRepository.java
│   └── UserRepository.java
├── dto/
│   ├── CreateUserRequest.java
│   └── UpdateUserRequest.java
├── model/
│   ├── Role.java                 # Implements GrantedAuthority
│   └── User.java                 # Implements UserDetails
└── service/
    ├── UserService.java
    └── UserServiceImpl.java      # Implements UserDetailsService
src/main/resources/
├── application.properties
└── templates/
    ├── edit.html                 # Create / edit user form (admin only)
    ├── index.html                # Public landing page
    ├── user.html                 # User profile page
    └── users.html                # User list (admin only)
```

## Requirements

- Java 21
- Maven
- Docker and Docker Compose

## Security & Default Accounts

Spring Security is enabled. All pages (except `/` and `/index`) require authentication.

On first startup, `DataInitializer` automatically creates two roles and two default accounts:

| Username | Password | Roles            | Redirected to  |
|----------|----------|------------------|----------------|
| `admin`  | `admin`  | ROLE_ADMIN, ROLE_USER | `/admin`  |
| `user`   | `user`   | ROLE_USER        | `/user`        |

### URL access rules

| URL pattern   | Required role         |
|---------------|-----------------------|
| `/`           | Public (no login)     |
| `/user`       | ROLE_USER or ROLE_ADMIN |
| `/admin/**`   | ROLE_ADMIN only       |

### Post-login redirect

- `ROLE_ADMIN` users are redirected to `/admin` (the user management list)
- `ROLE_USER` users are redirected to `/user` (their profile page)

> **Note:** The default `admin` account has **both** roles, so it can access `/user` as well as `/admin`.

### Logout

A **Logout** button is available in the navigation bar on every page.

### Changing passwords

Passwords are stored hashed with BCrypt. When editing a user, leave the password field blank to keep the existing password.

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

Log in with `admin` / `admin` for full access, or `user` / `user` for the user profile page.

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

> **Note:** `DataInitializer` only seeds default accounts when the `users` table is empty. Deleting the volume will cause them to be re-created on next startup.

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
- The app uses Thymeleaf templates under `src/main/resources/templates`, which is Spring Boot's default location.
- The database schema is managed by Hibernate with `ddl-auto=update` — tables are created/updated automatically on startup.
