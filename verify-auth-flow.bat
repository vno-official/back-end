@echo off
setlocal enabledelayedexpansion

echo ========================================
echo    VNO Backend Verification Script
echo ========================================
echo.

REM Generate random email
set "RANDOM_NUM=%RANDOM%"
set "EMAIL=testuser%RANDOM_NUM%@example.com"
set "PASSWORD=password123"

echo [1/4] Testing OpenAPI Endpoint...
curl -s -o nul -w "%%{http_code}" http://localhost:8000/api/auth/openapi > temp_code.txt
set /p HTTP_CODE=<temp_code.txt
del temp_code.txt

if "%HTTP_CODE%"=="200" (
    echo [OK] OpenAPI endpoint accessible (HTTP 200)
) else (
    echo [ERROR] OpenAPI endpoint failed (HTTP %HTTP_CODE%)
    echo Ensure Kong is running and configured correctly.
)
echo.

echo [2/4] Registering new user: %EMAIL%...
curl -s -X POST http://localhost:8000/api/auth/register ^
    -H "Content-Type: application/json" ^
    -d "{\"email\": \"%EMAIL%\", \"password\": \"%PASSWORD%\", \"name\": \"Test User\"}" > register_response.txt

type register_response.txt
echo.
echo.

echo [3/4] Logging in...
curl -s -X POST http://localhost:8000/api/auth/login ^
    -H "Content-Type: application/json" ^
    -d "{\"email\": \"%EMAIL%\", \"password\": \"%PASSWORD%\"}" > login_response.txt

REM Extract Access Token (Simple parsing for batch)
REM Note: This requires 'jq' or similar for robust parsing, but we'll try basic string manipulation or just show the output
echo Login Response:
type login_response.txt
echo.

REM Attempt to extract token using PowerShell for better parsing
for /f "tokens=*" %%i in ('powershell -Command "Get-Content login_response.txt | ConvertFrom-Json | Select-Object -ExpandProperty accessToken"') do set ACCESS_TOKEN=%%i

if "%ACCESS_TOKEN%"=="" (
    echo [ERROR] Failed to extract access token. Login might have failed.
    goto end
) else (
    echo [OK] Access Token obtained.
)
echo.

echo [4/4] Accessing Protected Resource (Roles Allowed)...
curl -s -X GET http://localhost:8000/api/auth/roles-allowed ^
    -H "Authorization: Bearer %ACCESS_TOKEN%" > protected_response.txt

type protected_response.txt
echo.
echo.

echo ========================================
echo    Verification Complete
echo ========================================
echo.
echo Check the outputs above for success/failure.
echo.

:end
del register_response.txt
del login_response.txt
del protected_response.txt
endlocal
