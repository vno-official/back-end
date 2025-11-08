@echo off
title Spring Boot Microservices

echo ========================================
echo    STARTING MICROSERVICES IN PARALLEL
echo ========================================

echo Starting all services... This may take a few minutes.

start "Service Discovery" cmd /k ".\gradlew.bat :service-discovery:bootRun && Service Discovery Ready!"
timeout /t 10 >nul

start "Config Server" cmd /k ".\gradlew.bat :config-server:bootRun && Config Server Ready!"
timeout /t 5 >nul

start "Auth Service" cmd /k ".\gradlew.bat :src:auth-service:bootRun && Auth Service Ready!"
timeout /t 5 >nul

start "User Service" cmd /k ".\gradlew.bat :src:user-service:bootRun && User Service Ready!"
timeout /t 5 >nul

start "API Gateway" cmd /k ".\gradlew.bat :src:api-gateway:bootRun && API Gateway Ready!"

echo.
echo ========================================
echo    SERVICES STARTING IN BACKGROUND
echo ========================================
echo.
echo Monitor progress in individual terminal windows
echo Once all are ready, access:
echo    • Gateway: http://localhost:8080
echo    • Eureka: http://localhost:8761
echo    • Auth API: http://localhost:8080/api/auth/swagger-ui/index.html
echo    • User API: http://localhost:8080/api/users/swagger-ui/index.html
echo.
pause