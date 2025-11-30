@echo off
setlocal enabledelayedexpansion

REM VNO Environment Manager
REM Manage Dev and Prod Docker environments

:main_menu
cls
echo.
echo ========================================
echo    VNO Environment Manager
echo ========================================
echo.
echo 1. Run DEV Environment (Live Reload + Dev UI)
echo 2. Run PROD Environment (Optimized Images)
echo 3. Stop DEV Environment
echo 4. Stop PROD Environment
echo 5. Start Kong Gateway (Standalone)
echo 6. Stop Kong Gateway (Standalone)
echo 7. View Logs
echo 8. Exit
echo.
set /p "choice=Choose an action (1-8): "

if "%choice%"=="1" goto run_dev
if "%choice%"=="2" goto run_prod
if "%choice%"=="3" goto stop_dev
if "%choice%"=="4" goto stop_prod
if "%choice%"=="5" goto start_gateway
if "%choice%"=="6" goto stop_gateway
if "%choice%"=="7" goto view_logs
if "%choice%"=="8" goto end

echo Invalid choice. Please try again.
timeout /t 2 >nul
goto main_menu

:run_dev
cls
echo.
echo ========================================
echo    Starting DEV Environment...
echo ========================================
echo.
echo Features:
echo - Live Reload (Source mounted)
echo - Quarkus Dev UI (http://localhost:8080/q/dev)
echo - Debug Ports (5005-5010)
echo.

cd gateway
docker-compose -f docker-compose-dev.yml up -d --build

if errorlevel 1 (
    echo.
    echo [ERROR] Failed to start DEV environment.
    pause
) else (
    echo.
    echo [OK] DEV environment started!
    echo.
    echo Access Points:
    echo - Kong Proxy: http://localhost:8000
    echo - Auth Dev UI: http://localhost:8080/q/dev
    echo - User Dev UI: http://localhost:8081/q/dev
    echo.
    pause
)
cd ..
goto main_menu

:run_prod
cls
echo.
echo ========================================
echo    Starting PROD Environment...
echo ========================================
echo.
echo Features:
echo - Optimized JVM Images
echo - Production Configuration
echo - Restart Policies
echo.

echo Note: Ensure images are built first (build-all-images.bat)
echo.
pause

cd gateway
docker-compose -f docker-compose-prod.yml up -d

if errorlevel 1 (
    echo.
    echo [ERROR] Failed to start PROD environment.
    pause
) else (
    echo.
    echo [OK] PROD environment started!
    echo.
    echo Access Points:
    echo - Kong Proxy: http://localhost:8000
    echo.
    pause
)
cd ..
goto main_menu

:stop_dev
echo.
echo Stopping DEV environment...
cd gateway
docker-compose -f docker-compose-dev.yml down
cd ..
echo.
pause
goto main_menu

:stop_prod
echo.
echo Stopping PROD environment...
cd gateway
docker-compose -f docker-compose-prod.yml down
cd ..
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
echo Useful when running services locally (e.g. via IDE or dev-start.bat).
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

:view_logs
cls
echo.
echo ========================================
echo    View Logs
echo ========================================
echo.
echo 1. DEV Logs
echo 2. PROD Logs
echo 3. Gateway Logs
echo 4. Back
echo.
set /p "log_choice=Choose environment (1-4): "

if "%log_choice%"=="1" (
    cd gateway
    docker-compose -f docker-compose-dev.yml logs -f
    cd ..
    goto main_menu
)
if "%log_choice%"=="2" (
    cd gateway
    docker-compose -f docker-compose-prod.yml logs -f
    cd ..
    goto main_menu
)
if "%log_choice%"=="3" (
    cd gateway
    docker-compose -f docker-compose.yml logs -f
    cd ..
    goto main_menu
)
if "%log_choice%"=="4" goto main_menu

goto view_logs

:end
endlocal
