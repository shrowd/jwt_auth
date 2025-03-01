# JWT Authentication with Spring Boot & React

A full-stack application demonstrating secure authentication using JWT tokens with Spring Boot backend and React frontend.

## Table of Contents

- [About](#about)
- [Features](#features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Clone the repository](#clone-the-repository)
  - [Installation Options](#installation-options)
    - [Option 1: Manual Setup](#option-1-manual-setup)
    - [Option 2: Docker Compose](#option-2-docker-compose)
- [Configuration](#configuration)
  - [Environment Variables](#environment-variables)
  - [Spring Boot Configuration](#spring-boot-configuration)
- [Application Screens](#application-screens)
- [API Endpoints](#api-endpoints)
- [Accessing the Application](#accessing-the-application)

## About

This project provides a robust foundation for developing secure web applications with a complete authentication system implemented using JWT (JSON Web Tokens). It eliminates the need to build authentication mechanisms from scratch. Simply clone this repository, configure your environment variables, and you'll have a working authentication system ready to build upon. The modular architecture makes it easy to extend with additional features specific to your application's needs.
  
## Features

- 🔐 Secure authentication with JWT
- 👥 User registration and login
- 🔑 Role-based access control (Admin/User)
- 🐳 Docker support for easy deployment
- 🧪 H2 database for development

## Getting Started

### Prerequisites

- Java 17
- Maven 3.6.0 or higher
- Node.js & npm
- Docker & Docker Compose (optional)
- Git

### Clone the Repository
```bash
# Clone the repository
git clone https://github.com/shrowd/jwt_auth.git

# Navigate to project directory
cd jwt_auth
```

### Installation Options

#### Option 1: Manual Setup

**Backend:**

```bash
# Navigate to backend directory
cd api

# Build with Maven
./mvnw clean install

# Run Spring Boot application
./mvnw spring-boot:run
```

**Frontend:**

```bash
# Navigate to frontend directory
cd frontend

# Install dependencies
npm install

# Start development server
npm run dev
```

#### Option 2: Docker Compose

Run everything with a single command:

```bash
# From project root
docker-compose up --build
```

## Configuration

### Environment Variables

Create a `.env` file in the project root:

```
DB_URL=jdbc:h2:mem:db;
DB_USERNAME=your_database_username
DB_PASSWORD=your_database_password
JWT_SECRET=your_jwt_secret_key
JWT_EXPIRATION_TIME=3600000
```

### Spring Boot Configuration

The application reads these environment variables from `application.properties`:

```properties
spring.application.name=api

spring.datasource.url=${DB_URL}
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.open-in-view=false
spring.jpa.show-sql=true

spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration

security.jwt.token.secret-key=${JWT_SECRET}
security.jwt.token.expiration-time=${JWT_EXPIRATION_TIME}
```

## Application Screens

### Login Screen
Authenticate with email and password.
![Image](https://github.com/user-attachments/assets/2ef7df31-e065-490e-8c85-b67158f48cc2)

### Registration Screen
Create a new account with form validation.
![Image](https://github.com/user-attachments/assets/4f32c79d-7334-4272-895c-885012317be8)

### Admin View
Access admin dashboard.
![Image](https://github.com/user-attachments/assets/bdd33b96-50b6-4750-921c-ee34984b00fb)

### User View
Access user dashboard.
![Image](https://github.com/user-attachments/assets/3a570c21-6db7-4cca-b96b-9e161e4633db)

## API Endpoints

| Method | Endpoint | Description | Authentication |
|--------|----------|-------------|----------------|
| POST   | /api/v1/auth/signin | Authenticate user | None |
| POST   | /api/v1/auth/signup | Register new user | None |
| GET    | /api/v1/admin | Get admin dashboard | Admin only |
| GET    | /api/v1/user | Get user dashboard | Authenticated |

## Accessing the Application

- Backend API: [http://localhost:8080](http://localhost:8080)
- Frontend: [http://localhost:5173](http://localhost:5173)

#
⭐️ If you find this helpful, please give it a star!
