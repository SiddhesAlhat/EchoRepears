@echo off
echo Starting Fixipy Spring Boot Application...
echo.

REM Set default environment variables (replace with your actual values)
set RAZORPAY_KEY_ID=rzp_test_XXXXXXXXXXXXXXXX
set RAZORPAY_KEY_SECRET=XXXXXXXXXXXXXXXXXXXXXXXX
set TWILIO_SID=ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
set TWILIO_AUTH_TOKEN=your_auth_token
set TWILIO_WHATSAPP_FROM=whatsapp:+14155238886
set ADMIN_WHATSAPP_NUMBER=whatsapp:+919876543210

echo Environment variables set with default values
echo.
echo To use your actual API keys:
echo 1. Edit this file and replace the placeholder values
echo 2. Or set them in your IDE's environment variables
echo.
echo Starting application...
mvn spring-boot:run

pause
