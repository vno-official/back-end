# WARP.md — Warp AI Rules for VNO Backend Project

## 🧠 Project Context
This repository contains the **backend for the VNO platform**, a microservices-based note and collaboration system built with **Spring Boot**, **Gradle**, and **Docker**.

### Architecture Overview
- **Type**: Microservices Architecture  
- **Services**:
  - `api-gateway`: Spring Cloud Gateway for routing and authentication
  - `auth-service`: JWT-based authentication and refresh token management
  - `user-service`: Manages user profiles and CRUD operations
  - `organization-service`: Handles organizations, members, and roles
  - `note-service`: Manages notes and tags, event-driven for notifications
  - `tag-service`: Centralized tag management
  - `notification-service`: Real-time notifications (WebSocket)
  - `service-discovery`: Eureka server for service registration
  - `config-server`: Spring Cloud Config for centralized configuration
  - `common`: Shared DTOs, constants, and exceptions

### Tech Stack
- **Language**: Java 21  
- **Framework**: Spring Boot 3.x  
- **Build Tool**: Gradle (multi-module)  
- **Database**: PostgreSQL (via Spring Data JPA)  
- **Containerization**: Docker & Docker Compose  
- **Configuration**: Spring Cloud Config Server  
- **Service Discovery**: Eureka  
- **Gateway**: Spring Cloud Gateway  
- **Security**: Spring Security + JWT  
- **Messaging/Event**: Spring Events (future Kafka/RabbitMQ support)  

---

## 🎯 Development Goals (from `todo.md`)
### Completed
- ✅ JWT authentication & refresh tokens  
- ✅ API Gateway with route and security filters  
- ✅ Config Server for centralized configuration  

### In Progress
- 🔄 Eureka service discovery  
- 🔄 Circuit breaker & centralized logging  
- 🔄 Unit & integration tests  

### Upcoming
- ⏳ Organization & member management  
- ⏳ Note & tag APIs  
- ⏳ WebSocket-based real-time notifications  
- ⏳ Docker Compose & Kubernetes manifests  
- ⏳ CI/CD pipeline & monitoring  

---

## 💡 Warp AI Rules

When assisting in this project, **Warp AI should always:**
1. **Use Spring Boot best practices**
   - Keep each service self-contained.
   - Use `@Service`, `@Repository`, `@Controller` layering properly.
   - Apply constructor-based dependency injection.
   - Use DTOs for all API communication.

2. **Follow Gradle conventions**
   - Define dependencies in each module’s `build.gradle`.
   - Use the root `build.gradle` for shared dependencies (like Spring Boot version and common libs).

3. **Code style**
   - Use Google Java Style (2-space indentation).
   - Class names: PascalCase  
   - Methods/variables: camelCase  
   - Constants: UPPER_SNAKE_CASE  
   - Public methods
