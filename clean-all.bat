@echo off
title Clean All Spring Boot Microservices
echo =====================================================
echo       Cleaning all services (Gradle clean build)
echo =====================================================

REM --- List of all your modules ---
set MODULES=src:config-server src:service-discovery src:api-gateway src:auth-service src:user-service src:note-service src:common

REM --- Loop through each module and clean ---
for %%M in (%MODULES%) do (
    echo.
    echo Cleaning %%M ...
    call .\gradlew.bat :%%M:clean
    if %errorlevel% neq 0 (
        echo ❌ Failed to clean %%M
        pause
        exit /b %errorlevel%
    )
)

echo.
echo =====================================================
echo All modules cleaned successfully!
echo =====================================================
pause
