# VNO Microservices - Setup Guide

## Architecture Overview

```
┌─────────────────┐
│  Config Server  │ :8888
└────────┬────────┘
         │
    ┌────┴────┐
    │         │
┌───▼──────┐  │  ┌──────────────┐
│ Service  │  │  │ Auth Service │ :8081
│Discovery │  │  └──────┬───────┘
│ (Eureka) │  │         │
└────┬─────┘  │    ┌────▼────────┐
     │        └────► User Service│ :8082
     │             └─────────────┘
```

## Prerequisites

- **Java 21** (JDK 21)
- **PostgreSQL 14+**
- **Gradle 8.5+** (included via wrapper)

## Database Setup

### 1. Create Databases

```sql
-- Auth Service Database
CREATE DATABASE vno_auth;

-- User Service Database
CREATE DATABASE vno_user;
```

### 2. Database Configuration

Default credentials (can be overridden via environment variables):
- Host: `localhost`
- Port: `5432`
- Username: `postgres`
- Password: `postgres`

## Services Startup Order

### 1. Start Service Discovery (Eureka)

```bash
./gradlew :src:service-discovery:bootRun
```

**Verify**: http://localhost:8761 (username: `eureka`, password: `eureka-secret`)

### 2. Start Config Server

```bash
./gradlew :src:config-server:bootRun
```

**Verify**: http://localhost:8888/actuator/health (username: `config`, password: `config123`)

**Test config**: http://localhost:8888/auth-service/default

### 3. Start User Service

```bash
./gradlew :src:user-service:bootRun
```

**Port**: 8082

### 4. Start Auth Service

```bash
./gradlew :src:auth-service:bootRun
```

**Port**: 8081

## Configuration Management

### Config Repository Location

All service configurations are stored in: `config-repo/`

- `application.yml` - Common configuration for all services
- `auth-service.yml` - Auth service specific config
- `user-service.yml` - User service specific config
- `service-discovery.yml` - Eureka config

### Refresh Configuration

To refresh configuration without restart:

```bash
curl -X POST http://localhost:8081/actuator/refresh \
  -H "Content-Type: application/json"
```

## API Endpoints

### Auth Service (Port 8081)

#### Register User
```bash
POST http://localhost:8081/api/auth/register
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "Password123",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+1234567890"
}
```

#### Verify Email
```bash
POST http://localhost:8081/api/auth/verify-email
Content-Type: application/json

{
  "email": "john@example.com",
  "code": "123456"
}
```

#### Login
```bash
POST http://localhost:8081/api/auth/login
Content-Type: application/json

{
  "usernameOrEmail": "john_doe",
  "password": "Password123"
}
```

#### Refresh Token
```bash
POST http://localhost:8081/api/auth/refresh-token
Content-Type: application/json

{
  "refreshToken": "your-refresh-token-here"
}
```

#### Logout
```bash
POST http://localhost:8081/api/auth/logout
Content-Type: application/json

{
  "refreshToken": "your-refresh-token-here"
}
```

#### Get Active Sessions
```bash
GET http://localhost:8081/api/auth/sessions
Authorization: Bearer {access-token}
```

#### Logout All Sessions
```bash
POST http://localhost:8081/api/auth/logout-all
Authorization: Bearer {access-token}
```

## Environment Variables

### Auth Service

```bash
# Database
DB_HOST=localhost
DB_PORT=5432
DB_NAME=vno_auth
DB_USERNAME=postgres
DB_PASSWORD=postgres

# JWT
JWT_SECRET=your-base64-encoded-secret-key
JWT_ACCESS_EXPIRATION=3600000
JWT_REFRESH_EXPIRATION=604800000

# Config Server
CONFIG_SERVER_URI=http://localhost:8888
CONFIG_SERVER_USER=config
CONFIG_SERVER_PASSWORD=config123

# Eureka
EUREKA_SERVER_URL=http://eureka:eureka-secret@localhost:8761/eureka/
```

### User Service

```bash
# Database
DB_HOST=localhost
DB_PORT=5432
DB_NAME=vno_user
DB_USERNAME=postgres
DB_PASSWORD=postgres

# Email (Mock for development)
MOCK_EMAIL=true

# Config Server
CONFIG_SERVER_URI=http://localhost:8888
CONFIG_SERVER_USER=config
CONFIG_SERVER_PASSWORD=config123
```

## Troubleshooting

### Services can't connect to Config Server

1. Ensure Config Server is running on port 8888
2. Check credentials in `bootstrap.yml`
3. Verify `config-repo` directory exists and contains config files

### Services can't register with Eureka

1. Ensure Service Discovery is running on port 8761
2. Check Eureka credentials in config files
3. Verify network connectivity

### Database connection errors

1. Ensure PostgreSQL is running
2. Verify databases `vno_auth` and `vno_user` exist
3. Check database credentials in config files

### Flyway migration errors

```bash
# Reset Flyway (development only)
./gradlew :src:auth-service:flywayClean
./gradlew :src:auth-service:flywayMigrate
```

## Development Tips

### Hot Reload

Use Spring DevTools for hot reload during development.

### View Logs

```bash
# Auth Service
./gradlew :src:auth-service:bootRun --console=plain

# User Service
./gradlew :src:user-service:bootRun --console=plain
```

### Build All Services

```bash
./gradlew build
```

### Clean Build

```bash
./gradlew clean build
```

## Production Deployment

For production, switch Config Server to Git mode:

1. Update `config-server/application.yml`
2. Set `spring.profiles.active=git`
3. Configure Git repository URL and credentials
4. Use environment-specific profiles (dev, staging, prod)

## Security Notes

⚠️ **Important**: Change default credentials before deploying to production!

- Eureka: `eureka:eureka-secret`
- Config Server: `config:config123`
- Database: `postgres:postgres`
- JWT Secret: Generate a strong 256-bit key

## Next Steps

1. ✅ Config Server setup complete
2. ✅ Service Discovery running
3. ✅ Auth Service configured
4. ✅ User Service configured
5. 🔄 Implement email verification service
6. 🔄 Add API Gateway
7. 🔄 Add monitoring (Prometheus/Grafana)
8. 🔄 Add distributed tracing (Zipkin)
