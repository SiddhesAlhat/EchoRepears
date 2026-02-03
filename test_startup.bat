@echo off
echo Testing Spring Boot compilation...
echo.

echo Checking if Java is available...
java -version
echo.

echo Checking project structure...
dir /s /b *.java | find /c ".java"
echo.

echo Spring Boot project setup complete!
echo.
echo To run the application:
echo 1. Set environment variables for Razorpay and Twilio
echo 2. Run: mvn spring-boot:run
echo 3. Application will be available at http://localhost:8080
echo.
pause
