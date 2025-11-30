# Database Configuration Guide

## 🎯 Tổng quan

VNO Backend sử dụng **Neon PostgreSQL** (cloud database) với **Hibernate ORM auto-migration** để tự động tạo và cập nhật database schema.

## ✨ Tính năng Auto-Migration

Tất cả services đã được config với:
```yaml
quarkus:
  hibernate-orm:
    schema-management:
      strategy: update
```

**Strategy: update** có nghĩa là:
- ✅ **Tự động tạo tables** nếu chưa tồn tại
- ✅ **Tự động thêm columns** mới khi có thay đổi entity
- ✅ **Không xóa** columns cũ (an toàn cho production)
- ✅ **Không xóa data** hiện có

## 🗄️ Database Setup - Neon PostgreSQL

### Current Configuration

Services đang sử dụng **Neon PostgreSQL** (external cloud database):

**Auth Service:**
```yaml
# auth-service/src/main/resources/application.yml
quarkus:
  datasource:
    username: neondb_owner
    password: npg_GMfXtR8H2nkK
    jdbc:
      url: jdbc:postgresql://ep-flat-feather-a1vmt3a1-pooler.ap-southeast-1.aws.neon.tech/vno-auth
```

**User Service:**
```yaml
# user-service/src/main/resources/application.yml
quarkus:
  datasource:
    username: neondb_owner
    password: npg_GMfXtR8H2nkK
    jdbc:
      url: jdbc:postgresql://ep-flat-feather-a1vmt3a1-pooler.ap-southeast-1.aws.neon.tech/vno-users
```

**Note Service:**
```yaml
# note-service/src/main/resources/application.yml
quarkus:
  datasource:
    username: vno_auth_2025
    password: vno@auth-2025
    reactive:
      url: vertx-reactive:postgresql://localhost:5432/vno_notes
```

### Neon PostgreSQL Databases

| Service | Database Name | Connection |
|---------|---------------|------------|
| Auth Service | vno-auth | Neon Cloud |
| User Service | vno-users | Neon Cloud |
| Note Service | vno_notes | Local (cần update) |

## 🚀 Workflows

### Workflow 1: Local Development

```cmd
REM 1. Start services locally
dev-start.bat

REM 2. Services tự động connect tới Neon PostgreSQL
REM 3. Tables tự động được tạo/cập nhật khi service start!
```

**Lợi ích:**
- ✅ Không cần setup database local
- ✅ Dùng chung database với team
- ✅ Data persistent trên cloud
- ✅ Không lo về backup

### Workflow 2: Docker Containers

```cmd
REM 1. Build images
build-and-deploy.bat

REM 2. Start services với docker-services.bat
docker-services.bat
# Chọn 1 → 7 (Start all services)

REM 3. Containers tự động connect tới Neon PostgreSQL
REM 4. Tables tự động được tạo!
```

**Lưu ý:** Containers vẫn connect tới Neon PostgreSQL, KHÔNG dùng database local.

### Workflow 3: Complete Stack với docker-compose

```cmd
REM 1. Build images
build-and-deploy.bat

REM 2. Start all services + Kong
cd gateway
docker-compose -f docker-compose-services.yml up -d

REM 3. All services connect to Neon PostgreSQL automatically!
```

## 🔍 Verify Database Tables

### Using Neon Console

1. Truy cập https://console.neon.tech
2. Login vào account
3. Chọn project
4. Vào SQL Editor
5. Chọn database (vno-auth, vno-users)
6. Run queries:

```sql
-- List all tables
SELECT table_name 
FROM information_schema.tables 
WHERE table_schema = 'public';

-- Check user table structure
SELECT column_name, data_type, is_nullable
FROM information_schema.columns
WHERE table_name = 'users';
```

### Using psql (Local)

Nếu có psql installed locally:

```cmd
REM Connect to Neon database
psql "postgresql://neondb_owner:npg_GMfXtR8H2nkK@ep-flat-feather-a1vmt3a1-pooler.ap-southeast-1.aws.neon.tech/vno-auth?sslmode=require"

REM List tables
\dt

REM Describe table
\d users

REM Exit
\q
```

## 📝 Migration Strategies

| Strategy | Hành vi | Use Case |
|----------|---------|----------|
| `none` | Không làm gì | Production (dùng Flyway/Liquibase) |
| `create` | Xóa và tạo lại schema | Testing only |
| `drop-and-create` | Xóa và tạo lại | Testing only |
| `update` | Tạo/cập nhật schema | **Development (CURRENT)** |
| `validate` | Chỉ validate, không thay đổi | Production validation |

**Hiện tại tất cả services dùng `update`** - an toàn cho development.

## 🔧 Override Database Configuration

### Option 1: Environment Variables (Docker)

Nếu muốn override database URL khi chạy container:

```cmd
REM Với docker run
docker run -p 8080:8080 \
  -e QUARKUS_DATASOURCE_JDBC_URL=jdbc:postgresql://other-host:5432/other_db \
  -e QUARKUS_DATASOURCE_USERNAME=other_user \
  -e QUARKUS_DATASOURCE_PASSWORD=other_password \
  vno-auth-service:latest

REM Với docker-services.bat - edit docker-compose-services.yml
```

### Option 2: Update application.yml

Sửa trực tiếp file `application.yml` của service:

```yaml
quarkus:
  datasource:
    username: your_username
    password: your_password
    jdbc:
      url: jdbc:postgresql://your-host:5432/your_database
```

## ⚠️ Lưu ý quan trọng

### 1. Neon PostgreSQL Free Tier Limits

Neon free tier có giới hạn:
- **Storage**: 0.5 GB
- **Compute**: 100 hours/month
- **Branches**: 10

Nếu vượt quá, cần upgrade hoặc chuyển sang database khác.

### 2. Connection Pooling

Neon sử dụng connection pooler:
- URL có `-pooler` trong hostname
- Tối ưu cho serverless và container workloads
- Tự động quản lý connections

### 3. SSL/TLS Required

Neon yêu cầu SSL connection:
- Quarkus tự động handle SSL
- Không cần config thêm
- Connection string đã bao gồm SSL mode

### 4. Data Persistence

- ✅ Data được lưu trên Neon cloud
- ✅ Automatic backups
- ✅ Point-in-time recovery (paid plans)
- ⚠️ Free tier: 7 days retention

## 🐛 Troubleshooting

### Tables không được tạo

**Kiểm tra:**
1. Service có connect được Neon không?
   ```cmd
   REM Xem logs
   docker logs vno-auth-service-container
   
   REM Hoặc khi chạy local
   gradlew :auth-service:quarkusDev
   ```

2. Config có đúng không?
   ```yaml
   quarkus:
     hibernate-orm:
       schema-management:
         strategy: update  # Phải có!
   ```

3. Neon database có tồn tại không?
   - Login vào Neon Console
   - Kiểm tra database đã được tạo

### Connection timeout

**Nếu không connect được Neon:**

1. Kiểm tra internet connection
2. Kiểm tra Neon database status (có thể bị sleep)
3. Kiểm tra credentials trong application.yml
4. Thử connect bằng psql để test:
   ```cmd
   psql "postgresql://neondb_owner:npg_GMfXtR8H2nkK@ep-flat-feather-a1vmt3a1-pooler.ap-southeast-1.aws.neon.tech/vno-auth?sslmode=require"
   ```

### Schema changes không apply

**Restart service:**
```cmd
REM Với docker-services.bat
docker-services.bat
# Chọn 2 → Stop service
# Chọn 1 → Start service

REM Hoặc manual
docker restart vno-auth-service-container

REM Local development
# Ctrl+C để stop
# Chạy lại gradlew quarkusDev
```

## 🔄 Chuyển sang Local PostgreSQL (Optional)

Nếu muốn dùng local PostgreSQL thay vì Neon:

### 1. Start Local PostgreSQL

```cmd
cd gateway
docker-compose -f docker-compose-database.yml up -d
```

### 2. Update application.yml

```yaml
# auth-service/src/main/resources/application.yml
quarkus:
  datasource:
    username: vno_admin
    password: vno_password_2025
    jdbc:
      url: jdbc:postgresql://localhost:5432/vno_auth
```

### 3. Rebuild và restart services

```cmd
REM Build lại
gradlew :auth-service:build

REM Nếu dùng Docker, rebuild image
docker build -f auth-service/src/main/docker/Dockerfile.jvm -t vno-auth-service:latest auth-service

REM Restart service
docker-services.bat
```

## 📚 Tài liệu tham khảo

- [Neon PostgreSQL](https://neon.tech/docs)
- [Quarkus Hibernate ORM](https://quarkus.io/guides/hibernate-orm)
- [Quarkus Datasource](https://quarkus.io/guides/datasource)
- [PostgreSQL Connection Strings](https://www.postgresql.org/docs/current/libpq-connect.html#LIBPQ-CONNSTRING)
