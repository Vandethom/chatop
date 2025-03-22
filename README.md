# ChatOp API 🏠

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=json-web-tokens&logoColor=white)

A rental property API with user authentication, property management, and messaging capabilities.

## 📋 Table of Contents

- [Installation](#installation)
- [Database Setup](#database-setup)
- [Configuration](#configuration)
- [Running the API](#running-the-api)
- [API Access](#api-access)
- [Endpoints](#endpoints)
- [Features](#features)
- [Project Structure](#project-structure)

## 🚀 Installation

```bash
# Clone the repository
git clone https://github.com/Vandethom/chatop.git
cd chatop-api

# Install dependencies
mvn clean install
```

## 💾 Database Setup

```sql
CREATE DATABASE chatop;
```
## Environment Variables Configuration

This application uses environment variables to manage sensitive configuration and deployment-specific settings. This approach keeps credentials secure and allows for environment-specific configurations.

### Option 1: Using a `.env` File (Recommended for Development)

1. Create a `.env` file in the project root directory:
Database Configuration
```
MYSQL_URL=jdbc:mysql://localhost:3306/chatop 
MYSQL_USERNAME=your_mysql_username 
MYSQL_PASSWORD=your_mysql_password

JWT Configuration
JWT_SECRET=your_jwt_secret_key JWT_EXPIRATION=86400000

File Upload Configuration
FILE_UPLOAD_DIR=/path/to/your/upload/directory

Server Configuration
PORT=3002
```

2. Install the `spring-dotenv` dependency in your `pom.xml`:
```xml
<dependency>
    <groupId>me.paulschwarz</groupId>
    <artifactId>spring-dotenv</artifactId>
    <version>3.0.0</version>
</dependency>
```

## ⚙️ Configuration

Update `application.properties` with your credentials and settings:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/chatop
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

# File Upload Configuration
spring.servlet.multipart.enabled=true
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
file.upload-dir=/path/to/upload/directory

# JWT Configuration
jwt.secret=your_jwt_secret_key
jwt.expiration=86400000
```

> **Note:** It's recommended to place the upload directory below the `/src` folder.

## 🏃‍♂️ Running the API

```bash
mvn spring-boot:run
```

## 🌐 API Access

| Resource | URL |
|----------|-----|
| API Base URL | http://localhost:3002 |
| Swagger Documentation | http://localhost:3002/swagger-ui.html |

## 🔌 Endpoints

<details>
<summary><b>Authentication</b></summary>

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Login and get JWT token |
| GET | `/api/auth/me` | Get current user profile |
</details>

<details>
<summary><b>Rentals</b></summary>

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/rentals` | Get all rentals |
| GET | `/api/rentals/{id}` | Get a specific rental |
| POST | `/api/rentals` | Create a new rental (multipart/form-data) |
| PUT | `/api/rentals/{id}` | Update a rental |
</details>

<details>
<summary><b>Messages</b></summary>

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/messages` | Send a message about a rental |
</details>

## ✨ Features

| Feature | Description |
|---------|-------------|
| 🔐 **User Authentication** | Secure JWT-based authentication |
| 🏠 **Rental Management** | Create, read, update rental properties |
| 📁 **File Storage** | Upload and serve property images |
| 💬 **Messaging System** | Communication between users about properties |
| ✅ **Input Validation** | Request validation with detailed error messages |
| 📚 **API Documentation** | Comprehensive Swagger documentation |

## 🏗️ Project Structure

| Directory | Purpose |
|-----------|---------|
| `controllers` | REST API endpoints |
| `services` | Business logic implementation |
| `repositories` | Data access layer |
| `models` | Entity classes mapped to database tables |
| `dto` | Data Transfer Objects for request/response |
| `mappers` | Conversion between DTOs and entities |
| `exceptions` | Custom exception classes |
| `config` | Application configuration |
| `utils` | Utility classes |
| `constants` | Application constants and messages |