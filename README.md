# Spring MVC & JPA User CRUD Application

A CRUD web application built using **Core Spring Framework (Spring MVC, Spring ORM)**, **Jakarta Persistence API (Hibernate)**, **Thymeleaf**, and **Apache Tomcat**, without using Spring Boot.

---

## 🛠️ Tech Stack & Requirements
* **Java 26**
* **Apache Maven**
* **Apache Tomcat (10.x+)**
* **Docker** (Recommended for running MySQL)

---

## 🚀 Getting Started & Setup Guide

### 1. Database Setup (Docker)
This application expects a MySQL database named `user_crud_db` running on port `3306` with a `root` user and an empty password.

You can instantly spin up a container matching these requirements by running the following command in your terminal:

```bash
docker run --name mysql-user-db \
  -e MYSQL_ALLOW_EMPTY_PASSWORD=yes \
  -e MYSQL_DATABASE=user_crud_db \
  -p 3306:3306 \
  -d mysql:9.0