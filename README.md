# Spring MVC & JPA User CRUD Application

A CRUD web application built using **Core Spring Framework (Spring MVC, Spring ORM)**, **Jakarta Persistence API (Hibernate)**, **Thymeleaf**, and **Apache Tomcat**, without using Spring Boot.

The entire stack (Apache Tomcat web server and MySQL database) is containerized with **Docker**, allowing you to run and test the application with a single command.

---

## 🛠️ Requirements & Tech Stack

* **Docker & Docker Compose** (Required)
* **Java 21+** (Only if building locally without Docker)
* **Apache Maven** (Only if building locally without Docker)

---

## 🚀 Getting Started (Docker Compose)

The easiest way to run and test the full application is using Docker Compose. It automatically compiles the Spring MVC `.war` artifact via a multi-stage build, provisions the MySQL database, wires up the environment variables, and deploys the app to Apache Tomcat.

### 1. Launch the Application

Run the following command from the project root directory:

```bash
docker compose up --build
```

### 2. Access the Application

Once the containers initialize, open your web browser and navigate to:

```text
http://localhost:8080/
```

### 3. Stop the Application

To shut down and clean up the containers, press `Ctrl + C` in your terminal or run:

```bash
docker compose down
```

---

## ⚙️ How It Works Under the Hood

Docker Compose automatically configures the application environment:

* **MySQL Database Container:** Spins up a MySQL 9.0 instance, creating the `user_crud_db` database on port `3306` with no root password.
* **Apache Tomcat Container:** Uses a multi-stage Docker build to package the Maven `.war` file, cleans default Tomcat sample applications, and deploys the app as `ROOT.war` on port `8080`.
* **Networking & Health Check:** Tomcat waits for the MySQL health check to pass before initializing. The web application dynamically connects to MySQL via internal service discovery (`jdbc:mysql://db:3306/user_crud_db`).