Complete user authentication and authorization system built with Spring Boot, featuring JWT tokens, BCrypt password hashing, and role-based access control.

## Features

### Security
- **BCrypt Password Hashing**: Passwords are securely hashed using BCrypt algorithm
- **JWT Authentication**: Stateless authentication using JSON Web Tokens
- **Spring Security**: Integrated Spring Security for robust authentication
- **Role-Based Access Control**: User roles for authorization

### Architecture
- **Layered Architecture**: Clean separation of Controllers, Services, and Repositories
- **DTOs**: Separate Data Transfer Objects for different operations
- **Custom Exceptions**: Meaningful exceptions with centralized error handling
- **Interface-Based Design**: Services implement interfaces for better testability

### User Management
- User registration with validation
- Secure login with JWT token generation
- Token validation
- User state management (Active, Inactive, Deleted)
- Role assignment and management

## Tech Stack

- **Spring Boot 4.0.2**: Latest Spring Boot framework
- **Spring Security 4.0.1**: Authentication and authorization
- **Spring Data JPA**: Database operations
- **JWT (jjwt 0.13.0)**: Token-based authentication
- **BCrypt**: Password hashing
- **MySQL**: Database
- **Lombok**: Reduce boilerplate code
- **Maven**: Build tool

## API Endpoints

### User Registration
```http
POST /User/signup
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "securePassword123"
}
```

**Response:**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "roles": ["USER"]
}
```

### User Login
```http
POST /User/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "securePassword123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "name": "John Doe",
    "email": "john@example.com",
    "roles": ["USER"]
  }
}
```

### Token Validation
```http
POST /User/validate
Content-Type: application/json

{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

## Project Structure

```
src/main/java/dev/sasidhar/userauth/
├── Configurations/
│   ├── AuthConfig.java          # Authentication configuration
│   └── SecurityConfig.java      # Spring Security configuration
├── Controllers/
│   ├── UserAuthController.java  # REST endpoints
│   └── ControllerAdvisor.java   # Global exception handler
├── Services/
│   ├── IUserAuthService.java    # Service interface
│   └── UserAuthService.java     # Business logic implementation
├── Repositories/
│   ├── UserRepository.java      # User data access
│   └── RoleRepository.java      # Role data access
├── Models/
│   ├── User.java                # User entity
│   ├── Role.java                # Role entity
│   ├── BaseModel.java           # Common entity fields
│   └── State.java               # User state enum
├── DTOs/
│   ├── UserSignUpDto.java       # Registration request
│   ├── UserLoginDto.java        # Login request
│   ├── ValidateTokenDto.java    # Token validation request
│   └── UserDto.java             # User response
└── Exceptions/
    ├── UserAlreadyExists.java
    ├── UserNotFound.java
    ├── IncorrectCredentials.java
    └── InsufficientDetails.java
```

## Setup & Installation

### Prerequisites
- Java 17 or higher
- MySQL 8.0+
- Maven 3.6+

### Database Configuration

1. Create MySQL database:
```sql
CREATE DATABASE userauth;
```

2. Update `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/userauth
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Run Application

```bash
# Clone repository
git clone https://github.com/sasidharpaluri/user-auth.git
cd user-auth

# Build project
mvn clean install

# Run application
mvn spring-boot:run
```

Application will start on `http://localhost:8080`

## Security Features

### Password Security
- Passwords are hashed using BCrypt with salt
- Original passwords are never stored
- One-way hashing prevents password recovery

### JWT Tokens
- Tokens are signed with secret key
- Include user information and expiration
- Stateless authentication (no server-side sessions)
- Tokens can be validated without database lookup

### Exception Handling
- Custom exceptions for different error scenarios
- Centralized error handling with ControllerAdvisor
- User-friendly error messages
- Proper HTTP status codes

## Design Patterns Used

- **Layered Architecture**: Separation of concerns across layers
- **DTO Pattern**: Data transfer between layers
- **Repository Pattern**: Data access abstraction
- **Dependency Injection**: Spring's IoC container
- **Exception Handling Pattern**: Centralized error handling
