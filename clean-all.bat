@echo off
title Clean All + Cache Killer
color 0A
echo.
echo =====================================================
echo       CLEAN ALL + GRADLE CACHE + DOCKER CACHE
echo =====================================================
echo.

REM --- Core modules ---
set CORE_MODULES=config-server service-discovery api-gateway common

REM --- Service modules ---
set SERVICE_MODULES=services:auth-service services:user-service services:note-service services:tag-service services:notification-service services:organization-service

echo [1/5] Cleaning core modules...
for %%M in (%CORE_MODULES%) do (
    echo.
    echo   Cleaning %%M ...
    call .\gradlew.bat :%%M:clean
)

echo [2/5] Cleaning service modules...
for %%M in (%SERVICE_MODULES%) do (
    echo.
    echo   Cleaning %%M ...
    call .\gradlew.bat :%%M:clean
    if %errorlevel% neq 0 (
        echo.
        echo ❌ FAILED: %%M
        pause
        exit /b %errorlevel%
    )
)
echo   All modules cleaned!

echo.
echo [2/5] Deleting root build folders...
if exist build rmdir /s /q build
if exist .gradle rmdir /s /q .gradle
echo   Root build + .gradle deleted!

echo.
echo [3/5] Clearing Gradle global cache...
set GRADLE_USER_HOME=%USERPROFILE%\.gradle
if exist "%GRADLE_USER_HOME%\caches" (
    echo   Deleting %GRADLE_USER_HOME%\caches ...
    rmdir /s /q "%GRADLE_USER_HOME%\caches"
)
if exist "%GRADLE_USER_HOME%\wrapper" (
    echo   Deleting %GRADLE_USER_HOME%\wrapper\dists ...
    rmdir /s /q "%GRADLE_USER_HOME%\wrapper\dists"
)
echo   Global Gradle cache cleared!

echo.
echo [4/5] Pruning Docker build cache (if Docker exists)...
docker builder prune -f >nul 2>&1
if %errorlevel% equ 0 (
    echo   Docker build cache pruned!
) else (
    echo   Docker not found or no cache to prune.
)

echo.
echo [5/5] Optional: Clear IntelliJ/Eclipse caches (manual)...
echo   - Close IDE
echo   - Delete: .idea/ or .settings/ + *.iml
echo   - Or run: gradlew --no-daemon clean

echo.
echo =====================================================
echo       ALL CLEANED SUCCESSFULLY!
echo       Safe to rebuild: ./gradlew build
echo =====================================================
pause