1. Clone the repository
git clone https://github.com/Vandethom/chatop.git
cd chatop-api

2. Database setup
CREATE DATABASE chatop;

3. Configuration
Update application.properties with your credentials and settings:
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/chatop
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

# File Upload Configuration
spring.servlet.multipart.enabled=true
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
file.upload-dir=/path/to/upload/directory (best to have it below the /src folder)

# JWT Configuration
jwt.secret=your_jwt_secret_key
jwt.expiration=86400000

4. Install dependencies using Maven
mvn clean install

5. Run the api using SpringBoot
mvn spring-boot:run

6. URLs
To access the API     : http://localhost:3002
To access the swagger : http://localhost:3002/swagger-ui.html

7. Endpoints
Authentication
POST /api/auth/register - Register a new user
POST /api/auth/login    - Login and get JWT token
GET  /api/auth/me       - Get current user profile

Rentals
GET  /api/rentals      - Get all rentals
GET  /api/rentals/{id} - Get a specific rental
POST /api/rentals      - Create a new rental (multipart/form-data)
PUT  /api/rentals/{id} - Update a rental

Messages
POST /api/messages - Send a message about a rental

8. Features
User Authentication: Secure JWT-based authentication
Rental Management  : Create, read, update rental properties
File Storage       : Upload and serve property images
Messaging System   : Communication between users about properties
Input Validation   : Request validation with detailed error messages
API Documentation  : Comprehensive Swagger documentation

9. Project structure
controllers : REST API endpoints
services    : Business logic implementation
repositories: Data access layer
models      : Entity classes mapped to database tables
dto         : Data Transfer Objects for request/response
mappers     : Conversion between DTOs and entities
exceptions  : Custom exception classes
config      : Application configuration
utils       : Utility classes
constants   : Application constants and messages