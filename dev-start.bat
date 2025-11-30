@echo off
setlocal enabledelayedexpansion

REM VNO Local Development Manager
REM Starts services in Quarkus Dev Mode (Gradle) and manages Gateway

:main_menu
cls
echo.
echo ========================================
echo    VNO Local Development Manager
echo ========================================
echo.
echo 1. Start All Services (Local Gradle)
echo 2. Start Kong Gateway (Standalone)
echo 3. Stop Kong Gateway (Standalone)
echo 4. Exit
echo.
set /p "choice=Choose an action (1-4): "

if "%choice%"=="1" goto start_services
if "%choice%"=="2" goto start_gateway
if "%choice%"=="3" goto stop_gateway
if "%choice%"=="4" goto end

echo Invalid choice. Please try again.
timeout /t 2 >nul
goto main_menu

:start_services
cls
echo.
echo Starting VNO Backend Services in Development Mode...
echo.
echo This will start all services using Gradle's quarkusDev mode.
echo Each service will run in a separate Command Prompt window.
echo.

REM Start all services in separate windows
echo Starting Auth Service on port 8080...
start "Auth Service - Port 8080" cmd /k "cd auth-service && gradlew quarkusDev"
timeout /t 2 /nobreak >nul

echo Starting User Service on port 8081...
start "User Service - Port 8081" cmd /k "cd user-service && gradlew quarkusDev"
timeout /t 2 /nobreak >nul

echo Starting Note Service on port 8082...
start "Note Service - Port 8082" cmd /k "cd note-service && gradlew quarkusDev"
timeout /t 2 /nobreak >nul

echo Starting Realtime Collab Service on port 8083...
start "Realtime Collab Service - Port 8083" cmd /k "cd realtime-collab-service && gradlew quarkusDev"
timeout /t 2 /nobreak >nul

echo Starting Notification Producer on port 8084...
start "Notification Producer - Port 8084" cmd /k "cd notification-service\producer && gradlew quarkusDev"
timeout /t 2 /nobreak >nul

echo Starting Notification Processor on port 8085...
start "Notification Processor - Port 8085" cmd /k "cd notification-service\processor && gradlew quarkusDev"

echo.
echo [OK] All services are starting in separate windows.
echo.
pause
goto main_menu

:start_gateway
cls
echo.
echo ========================================
echo    Starting Kong Gateway (Standalone)...
echo ========================================
echo.
echo This starts ONLY Kong Gateway using local config (kong.yml).
echo.

cd gateway
docker-compose -f docker-compose.yml up -d

if errorlevel 1 (
    echo.
    echo [ERROR] Failed to start Kong Gateway.
    pause
) else (
    echo.
    echo [OK] Kong Gateway started!
    echo.
    echo Access Points:
    echo - Proxy: http://localhost:8000
    echo - Admin: http://localhost:8001
    echo.
    pause
)
cd ..
goto main_menu

:stop_gateway
echo.
echo Stopping Kong Gateway...
cd gateway
docker-compose -f docker-compose.yml down
cd ..
echo.
pause
goto main_menu

:end
echo.
echo Goodbye!
endlocal
