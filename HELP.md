## Getting Started

### Prerequisites

Ensure the following are installed and configured on your machine:

1. **Java 21**
2. **Docker Engine** (e.g., [Docker Desktop](https://docs.docker.com/get-started/) or [Colima](https://github.com/abiosoft/colima))
3. **Docker Compose**
4. **MySQL Client or IDE** (optional, for inspecting database records)

### Building the Application

Run the build locally to verify setup and ensure tests pass:

```bash
./gradlew clean build
```

### Running Tests

To execute all tests locally:

```bash
./gradlew clean test
```

Explore integration tests under:

```
src/test/integration
```

## Code Coverage Report

We use **JaCoCo** for code coverage. After building the project, reports are available at:

```
build/reports/jacoco/test/html/index.html
```

Open `index.html` in your browser.

---

## Running the Application Locally

### Step 1: Start MySQL via Docker Compose

Navigate to the `docker` folder and bring up MySQL:

```bash
cd docker
docker-compose up -d
```

Use your SQL IDE to connect if required. Connection properties are defined in `docker-compose.yml`.

### Step 2: Start the Application

From the project root:

```bash
cd ..
./gradlew bootRun
```

Alternatively, use your IDE (e.g., IntelliJ IDEA).

### Step 3: Access API Documentation

Once the app is running, API docs are available at:
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
Note: Select public definition from the drop-down in the top right corner.

### Step 4: Test the API

Use Swagger UI or any REST client (e.g., Postman, HTTPie).

**Authentication:** All APIs require Basic Authentication.

* **Username:** `DEVELOPER`
* **Password:** configure a password of your choice and update the configuration files accordingly.

### Step 5: Shut Down Services

When done:

```bash
docker-compose down
```

---

## Contributing

* Follow Java and Spring Boot best practices.
* Write unit and integration tests for all new features.
* Maintain optimal code coverage.
* Ensure consistency with the existing project structure.

## Contact

For assistance, please reach out to the core backend team or the project maintainer.
