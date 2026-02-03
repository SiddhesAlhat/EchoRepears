# Fixipy Spring Boot Backend

This is the Spring Boot version of the Fixipy repair service backend, converted from Node.js/Express to Java/Spring Boot.

## Tech Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data MongoDB** - Database integration
- **Razorpay Java SDK** - Payment processing
- **Twilio Java SDK** - WhatsApp messaging
- **Maven** - Build tool

## Project Structure

```
src/main/java/com/fixipy/
├── FixipyApplication.java          # Main application class
├── controller/
│   ├── OrderController.java        # Order management endpoints
│   ├── ApplicationController.java   # Job application endpoints
│   └── RepairmanController.java    # Repairman registration endpoints
├── entity/
│   ├── Order.java                  # Order entity
│   ├── Application.java            # Job application entity
│   └── Repairman.java              # Repairman entity
├── repository/
│   ├── OrderRepository.java        # Order data access
│   ├── ApplicationRepository.java # Application data access
│   └── RepairmanRepository.java    # Repairman data access
├── service/
│   ├── OrderService.java           # Order business logic
│   ├── ApplicationService.java     # Application business logic
│   └── RepairmanService.java       # Repairman business logic
├── config/
│   └── FileUploadConfig.java       # File upload configuration
└── dto/
    └── BookingDetails.java          # Data transfer object
```

## Environment Variables

Create a `.env` file or set the following environment variables:

```bash
# Razorpay Configuration
RAZORPAY_KEY_ID=your_razorpay_key_id
RAZORPAY_KEY_SECRET=your_razorpay_key_secret

# Twilio Configuration
TWILIO_SID=your_twilio_sid
TWILIO_AUTH_TOKEN=your_twilio_auth_token
TWILIO_WHATSAPP_FROM=whatsapp:+14155238886
ADMIN_WHATSAPP_NUMBER=whatsapp:+919876543210
```

## API Endpoints

### Order Management
- `POST /api/create-order` - Create Razorpay order
- `POST /api/verify-payment` - Verify payment and save order

### Job Applications
- `POST /api/join-team` - Submit job application with resume upload

### Repairman Registration
- `POST /api/register-repairman` - Register new repairman

## Docker Setup (Recommended)

### Quick Start with Docker
```bash
# Start all services (MongoDB + Node.js + Spring Boot)
docker-start.bat

# Stop all services
docker-stop.bat

# Or use Docker commands directly
docker-compose up --build
docker-compose down
```

### Services Available
- **Node.js App**: http://localhost:3000
- **Spring Boot App**: http://localhost:8080  
- **MongoDB Atlas**: echorepears.2segylf.mongodb.net (cloud database)

### Docker Configuration
- **MongoDB Atlas**: Uses cloud MongoDB (no local MongoDB required)
- **Node.js**: Multi-stage build with Alpine Linux
- **Spring Boot**: OpenJDK 17 with Maven build
- **Network**: All services communicate via Docker bridge network
- **Volumes**: Persistent storage for file uploads only

## Manual Setup (Without Docker)

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- MongoDB running on localhost:27017

### Build and Run
```bash
mvn clean install
mvn spring-boot:run
```

### Access the Application
   - Application runs on `http://localhost:8080`
   - Uploaded files accessible at `http://localhost:8080/uploads/`

## Key Differences from Node.js Version

1. **Type Safety:** Java provides compile-time type checking
2. **Dependency Injection:** Spring Boot's IoC container manages dependencies
3. **Data Access:** Spring Data MongoDB provides repository pattern
4. **Configuration:** Properties-based configuration instead of .env files
5. **Error Handling:** Spring's exception handling mechanisms
6. **File Upload:** Built-in multipart support with configuration

## Database Collections

The application uses MongoDB with the following collections:
- `orders` - Customer repair orders
- `applications` - Job applications
- `repairmen` - Registered repair technicians

## File Upload

- Resume files are stored in the `uploads/` directory
- Maximum file size: 2MB
- Files are accessible via `/uploads/` endpoint
