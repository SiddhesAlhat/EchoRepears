@echo off
echo Starting Fixipy Application with Docker...
echo.

REM Check if Docker is running
docker version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Docker is not running or not installed!
    echo Please install Docker Desktop and start it before running this script.
    pause
    exit /b 1
)

echo Docker is running...
echo.

REM Build and start services
echo Building and starting all services...
echo Note: Using MongoDB Atlas (cloud database)
docker-compose up --build

echo.
echo Services are starting up...
echo.
echo Access URLs:
echo - Node.js App: http://localhost:3000
echo - Spring Boot App: http://localhost:8080
echo - MongoDB Atlas: echorepears.2segylf.mongodb.net
echo.
echo Press Ctrl+C to stop all services
pause
