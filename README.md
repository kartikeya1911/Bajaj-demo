# BFHL REST API

A Spring Boot REST API built for the Chitkara API Round (24 June). It accepts an array of mixed data (numbers, alphabets, special characters) and returns categorized results.

## Features

- **POST `/bfhl`** — Processes an input array and returns:
  - Even & odd numbers (as strings)
  - Alphabets (uppercased)
  - Special characters
  - Sum of all numbers
  - Concatenated string (reversed alphabets with alternating caps)
  - User metadata (user_id, email, roll_number)

## Tech Stack

- Java 17+
- Spring Boot 3.3.0
- Maven

---

## Prerequisites

- **Java 17** or higher installed ([Download](https://adoptium.net/))
- **Maven** (bundled via `mvnw` wrapper — no separate install needed)
- **Git** (for cloning)

Verify Java is installed:
```bash
java -version
```

---

## How to Run Locally

### 1. Clone the repository

```bash
git clone <your-repo-url>
cd demo
```

### 2. Build the project

```bash
# Windows
.\mvnw.cmd clean package

# Linux / macOS
./mvnw clean package
```

### 3. Run the application

```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux / macOS
./mvnw spring-boot:run
```

The API will start on **http://localhost:8080**.

### 4. Test the API

```bash
curl -X POST http://localhost:8080/bfhl \
  -H "Content-Type: application/json" \
  -d '{"data": ["a", "1", "334", "4", "R", "$"]}'
```

**Expected response:**
```json
{
  "is_success": true,
  "user_id": "jain_kartik_24062006",
  "email": "jain.kartik@example.com",
  "roll_number": "ABCD123",
  "odd_numbers": ["1"],
  "even_numbers": ["334", "4"],
  "alphabets": ["A", "R"],
  "special_characters": ["$"],
  "sum": "339",
  "concat_string": "Ra"
}
```

### 5. Run tests

```bash
# Windows
.\mvnw.cmd test

# Linux / macOS
./mvnw test
```

---

## How to Deploy

### Option A: Deploy to Railway

1. **Push your code to GitHub** (if not already):
   ```bash
   git init
   git add .
   git commit -m "Initial commit"
   git remote add origin <your-github-repo-url>
   git push -u origin main
   ```

2. **Go to [Railway](https://railway.app)** and sign in with GitHub.

3. **Create a new project** → Select **"Deploy from GitHub repo"** → Choose your repository.

4. Railway will auto-detect the Java/Maven project. If not, add these settings:
   - **Build Command:** `./mvnw clean package -DskipTests`
   - **Start Command:** `java -jar target/demo-0.0.1-SNAPSHOT.jar`

5. **Set the PORT environment variable:**
   - Go to **Variables** tab → Add: `PORT` = `8080`
   - Or add this to `application.properties`:
     ```properties
     server.port=${PORT:8080}
     ```

6. Railway will provide a public URL like `https://your-app.up.railway.app`. Your API endpoint will be:
   ```
   https://your-app.up.railway.app/bfhl
   ```

---

### Option B: Deploy to Render

1. **Push your code to GitHub** (same as above).

2. **Go to [Render](https://render.com)** and sign in.

3. **Create a new Web Service** → Connect your GitHub repo.

4. **Configure the service:**
   - **Environment:** `Docker` or `Native`
   - **Build Command:** `./mvnw clean package -DskipTests`
   - **Start Command:** `java -jar target/demo-0.0.1-SNAPSHOT.jar`
   - **Environment Variable:** `PORT` = `8080`

5. Click **Deploy**. Render will provide a URL like:
   ```
   https://your-app.onrender.com/bfhl
   ```

---

### Option C: Deploy using Docker

1. **Create a `Dockerfile`** in the project root:
   ```dockerfile
   FROM eclipse-temurin:17-jdk-alpine AS build
   WORKDIR /app
   COPY . .
   RUN ./mvnw clean package -DskipTests

   FROM eclipse-temurin:17-jre-alpine
   WORKDIR /app
   COPY --from=build /app/target/demo-0.0.1-SNAPSHOT.jar app.jar
   EXPOSE 8080
   ENTRYPOINT ["java", "-jar", "app.jar"]
   ```

2. **Build and run:**
   ```bash
   docker build -t bfhl-api .
   docker run -p 8080:8080 bfhl-api
   ```

3. You can deploy this Docker image to **Railway**, **Render**, **Fly.io**, **AWS**, **GCP**, or any provider that supports Docker.

---

## Project Structure

```
demo/
├── pom.xml
├── mvnw / mvnw.cmd
└── src/
    ├── main/java/com/example/demo/
    │   ├── DemoApplication.java            # Spring Boot entry point
    │   ├── controller/
    │   │   └── BfhlController.java         # REST controller (POST /bfhl)
    │   ├── dto/
    │   │   ├── RequestDto.java             # Request body DTO
    │   │   └── ResponseDto.java            # Response body DTO
    │   ├── service/
    │   │   ├── BfhlService.java            # Service interface
    │   │   └── BfhlServiceImpl.java        # Business logic implementation
    │   └── exception/
    │       └── GlobalExceptionHandler.java # Global error handling
    └── test/java/com/example/demo/
        └── BfhlServiceTest.java            # Unit tests (9 test cases)
```

---

## Configuration

Update your personal details in `BfhlServiceImpl.java`:

```java
private static final String USER_ID = "your_name_ddmmyyyy";
private static final String EMAIL = "your_email@example.com";
private static final String ROLL_NUMBER = "YOUR_ROLL_NUMBER";
```

---

## API Reference

### POST `/bfhl`

**Request Body:**
```json
{
  "data": ["a", "1", "334", "4", "R", "$"]
}
```

**Success Response (200):**
```json
{
  "is_success": true,
  "user_id": "john_doe_17091999",
  "email": "john@xyz.com",
  "roll_number": "ABCD123",
  "odd_numbers": ["1"],
  "even_numbers": ["334", "4"],
  "alphabets": ["A", "R"],
  "special_characters": ["$"],
  "sum": "339",
  "concat_string": "Ra"
}
```

**Error Response (400):**
```json
{
  "is_success": false,
  "message": "Data field cannot be null"
}
```
