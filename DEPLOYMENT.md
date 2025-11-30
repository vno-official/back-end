# VNO Backend - Deployment Guide

## 🎯 Quick Start

### 1. Build All Services
```cmd
REM Build all services
gradlew buildAll

REM Build Docker images
build-all-images.bat
```

### 2. Run Services (Environments)

**Option A: Environment Manager (Recommended) ⭐**
```cmd
manage-env.bat
```
Choose between:
1. **DEV Environment**: Live reload, Quarkus Dev UI, Debug ports.
2. **PROD Environment**: Optimized images, production settings.

**Option B: Interactive Container Manager**
```cmd
docker-services.bat
```
Good for managing individual containers or quick tests.

**Option C: Manual Docker Compose**
```cmd
cd gateway
# Dev
docker-compose -f docker-compose-dev.yml up -d

# Prod
docker-compose -f docker-compose-prod.yml up -d
```

## 📁 Essential Files

### Environment Scripts
- `manage-env.bat` - Switch between Dev/Prod environments ⭐
- `docker-services.bat` - Manage individual containers
- `build-all-images.bat` - Build Prod images

### Docker Compose Files
- `gateway/docker-compose-dev.yml` - Dev environment (Live Reload)
- `gateway/docker-compose-prod.yml` - Prod environment (Optimized)
- `gateway/docker-compose.yml` - Kong Gateway only

### Kong Configuration
- `gateway/kong-dev.yml` - Dev environment config
- `gateway/kong-docker.yml` - Prod environment config

### Documentation
- `QUICKSTART.md` - Quick reference guide
- `DATABASE_CONFIG.md` - Database configuration
- `BUILD.md` - Build instructions
- `PORTS.md` - Port mappings
- `README.md` - Project overview

## 🐳 Docker Services Manager

The `docker-services.bat` script is your main tool for managing containers.

### Start Services
```
Choose an action (1-5): 1

Available services:
1. Auth Service (Port 8080)
2. User Service (Port 8081)
3. Note Service (Port 8082)
4. Realtime Collab Service (Port 8083)
5. Notification Producer (Port 8084)
6. Notification Processor (Port 8085)
7. All Services ⭐
8. Back to main menu
```

### Stop Services
```
Choose an action (1-5): 2

Running services:
  - vno-auth-service-container
  - vno-user-service-container

Available services to stop:
1. Auth Service
2. User Service
...
7. All Services ⭐
```

### View Logs
```
Choose an action (1-5): 4

Running services:
1. vno-auth-service-container
2. vno-user-service-container
3. Back to main menu

Choose a service to view logs (1-3):
```

## 🗄️ Database Configuration

### Auto-Migration Enabled

All services use **Hibernate auto-migration** with `strategy: update`:
- ✅ Automatically creates tables if they don't exist
- ✅ Automatically adds new columns when entities change
- ✅ Doesn't delete existing columns or data
- ✅ Safe for development and staging

### Neon PostgreSQL (Current Setup)

Services connect to **Neon PostgreSQL** (cloud database):

| Service | Database | URL |
|---------|----------|-----|
| Auth | vno-auth | Neon Cloud |
| User | vno-users | Neon Cloud |
| Note | vno_notes | Local (to be migrated) |

**Configuration:** Already set in `application.yml` files.

**Verify tables:**
1. Go to https://console.neon.tech
2. Select database
3. SQL Editor → `\dt` or `SELECT * FROM information_schema.tables;`

See `DATABASE_CONFIG.md` for details.

## 🌐 Kong Gateway

### Declarative Configuration (Auto-Registration)

Kong uses **DB-less mode** with declarative YAML files:
- `kong.yml` - For local development
- `kong-docker.yml` - For Docker deployment

**Services auto-register** when Kong starts - no manual scripts needed!

### Start Kong

**Local Development:**
```cmd
cd gateway
docker-compose up -d
```

**Complete Stack:**
```cmd
cd gateway
docker-compose -f docker-compose-services.yml up -d
```

### Access Points

**Through Kong Gateway:**
- Base URL: `http://localhost:8000`
- Auth: `http://localhost:8000/api/auth`
- User: `http://localhost:8000/api/users`
- Note: `http://localhost:8000/api/notes`
- Collab: `http://localhost:8000/api/collab`
- Notifications: `http://localhost:8000/api/notifications`

**Kong Admin:**
- Admin API: `http://localhost:8001`
- Admin GUI: `http://localhost:8002`

**Direct Access (Bypass Kong):**
- Auth: `http://localhost:8080`
- User: `http://localhost:8081`
- Note: `http://localhost:8082`
- Collab: `http://localhost:8083`
- Notif Producer: `http://localhost:8084`
- Notif Processor: `http://localhost:8085`

## 🚀 Common Workflows

### Local Development
```cmd
REM 1. Start services locally
dev-start.bat

REM 2. Start Kong (services auto-register)
cd gateway && docker-compose up -d

REM 3. Test
curl http://localhost:8000/api/auth/health
```

### Docker Deployment
```cmd
REM 1. Build everything
build-and-deploy.bat

REM 2. Start complete stack
cd gateway && docker-compose -f docker-compose-services.yml up -d

REM 3. Test
curl http://localhost:8000/api/auth/health
```

### Test Individual Service
```cmd
REM 1. Start service
docker-services.bat
# Choose 1 → Choose service number

REM 2. View logs
docker-services.bat
# Choose 4 → Choose service

REM 3. Test
curl http://localhost:8080/q/health

REM 4. Stop
docker-services.bat
# Choose 2 → Choose service number
```

## 🔧 Build Commands

### Build All Services
```cmd
gradlew buildAll
```

### Build Individual Service
```cmd
gradlew :auth-service:build
gradlew :user-service:build
gradlew :note-service:build
```

### Build Docker Images
```cmd
REM All images
build-all-images.bat

REM Individual image
cd auth-service
gradlew build
docker build -f src/main/docker/Dockerfile.jvm -t vno-auth-service:latest .
```

### Clean Build
```cmd
REM Clean all
gradlew cleanAll

REM Clean and rebuild
gradlew cleanAll buildAll
```

## 🐛 Troubleshooting

### Service won't start

**Check logs:**
```cmd
docker-services.bat
# Choose 4 (View logs) → Select service
```

**Or manual:**
```cmd
docker logs vno-auth-service-container
docker logs -f vno-auth-service-container  # Follow mode
```

### Database connection issues

**Check configuration:**
```yaml
# auth-service/src/main/resources/application.yml
quarkus:
  datasource:
    jdbc:
      url: jdbc:postgresql://...  # Check this URL
```

**Test connection:**
```cmd
# View service logs for connection errors
docker logs vno-auth-service-container | findstr "database\|connection\|error"
```

### Kong not accessible

**Check Kong is running:**
```cmd
docker ps | findstr kong
```

**Check Kong logs:**
```cmd
docker logs vno-kong-gateway
```

**Verify services registered:**
```cmd
curl http://localhost:8001/services
curl http://localhost:8001/routes
```

### Port conflicts

**Check what's using a port:**
```cmd
netstat -ano | findstr :8080
```

**Kill process:**
```cmd
taskkill /PID <PID> /F
```

## 📊 Service Ports

| Service | Port | Container Name |
|---------|------|----------------|
| Auth | 8080 | vno-auth-service-container |
| User | 8081 | vno-user-service-container |
| Note | 8082 | vno-note-service-container |
| Realtime Collab | 8083 | vno-realtime-collab-service-container |
| Notification Producer | 8084 | vno-notification-producer-container |
| Notification Processor | 8085 | vno-notification-processor-container |
| Kong Proxy | 8000 | vno-kong-gateway |
| Kong Admin API | 8001 | vno-kong-gateway |
| Kong Admin GUI | 8002 | vno-kong-gateway |

## 💡 Best Practices

### Development
- Use `dev-start.bat` for local development
- Use `docker-services.bat` for testing containers
- Check logs regularly with option 4 in docker-services.bat

### Deployment
- Always run `build-and-deploy.bat` before deploying
- Use `docker-compose-services.yml` for complete stack
- Monitor logs: `docker-compose -f gateway/docker-compose-services.yml logs -f`

### Database
- Auto-migration is enabled (strategy: update)
- Tables created automatically on first run
- Data persists on Neon cloud
- No manual schema management needed

### Kong Gateway
- Services auto-register from YAML files
- No manual registration scripts needed
- Edit `kong.yml` or `kong-docker.yml` to add/remove services
- Restart Kong to apply changes: `docker-compose restart kong`

## 📚 Additional Resources

- [QUICKSTART.md](QUICKSTART.md) - Quick reference
- [DATABASE_CONFIG.md](DATABASE_CONFIG.md) - Database details
- [BUILD.md](BUILD.md) - Build instructions
- [gateway/README.md](gateway/README.md) - Kong Gateway guide
- [PORTS.md](PORTS.md) - Port configuration
