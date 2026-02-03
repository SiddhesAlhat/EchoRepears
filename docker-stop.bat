@echo off
echo Stopping Fixipy Docker Services...
echo.

docker-compose down

echo.
echo All services stopped.
echo To remove volumes (WARNING: This will delete all data):
echo docker-compose down -v
echo.
pause
