# Running Tests

## Execute All Tests

Run the complete test suite:

```bash
mvn clean test
```

---

## Execute a Specific Test

Run a specific test class:

```bash
mvn test -Dtest=LoginTest
```

Run a specific test method:

```bash
mvn test -Dtest=LoginTest#validLogin
```

---

# Test Configuration Parameters

The framework supports runtime configuration using Maven system properties.

## Browser

Specify the browser to execute tests against:

```bash
mvn clean test -Dbrowser=chrome
```

Supported browsers:

- chrome
- firefox
- webkit

Example:

```bash
mvn clean test -Dbrowser=firefox
```

---

## Headless Mode

Run tests without opening the browser UI:

```bash
mvn clean test -Dheadless=true
```

Run tests with the browser UI visible:

```bash
mvn clean test -Dheadless=false
```

---

## Environment

Specify the target environment:

```bash
mvn clean test -Denv=qa
```

Supported environments:

- qa
- staging
- prod

Example:

```bash
mvn clean test -Denv=staging
```

---

# Combining Parameters

Example: Run Chrome tests against QA environment in headless mode:

```bash
mvn clean test \
-Denv=qa \
-Dbrowser=chrome \
-Dheadless=true
```

---

# Running Tests with Docker

The framework provides a Docker-based execution environment containing:

- Java 21
- Maven
- Playwright Java dependencies
- Playwright browser binaries
- Test execution dependencies

Docker ensures tests run consistently across developer machines and CI environments.

---

## Build Docker Image

Create the Docker image:

```bash
docker build -t playwright-java-framework .
```

---

## Run Tests Using Docker

Execute tests inside the Docker container:

```bash
docker run --rm \
-e BROWSER=chromium \
-e HEADLESS=true \
-e TEST_ENV=qa \
playwright-java-framework
```

---

## Persist Test Results

Save test output, screenshots, videos, traces and Allure results:

```bash
docker run --rm \
-v $(pwd)/test-results:/app/test-results \
-v $(pwd)/allure-results:/app/allure-results \
playwright-java-framework
```

Generated files:

```
test-results/
    screenshots
    videos
    traces

allure-results/
    test execution data
    attachments
```

---

# Running Tests with Docker Compose

Docker Compose provides a simplified way to execute the complete test environment.

Start test execution:

```bash
docker compose up --build
```

Docker Compose provides:

- Consistent execution environment
- Browser configuration through environment variables
- Test result persistence
- Easy integration with CI/CD pipelines

Example `docker-compose.yml` configuration:

```yaml
version: "3.8"

services:
  playwright-tests:
    build: .
    environment:
      BROWSER: chromium
      HEADLESS: true
      TEST_ENV: qa
    volumes:
      - ./test-results:/app/test-results
      - ./allure-results:/app/allure-results
```

---

# CI/CD Pipeline

The project uses GitHub Actions to execute automated tests in a CI/CD pipeline.

Pipeline workflow:

```
Developer Push
       |
       v
GitHub Actions
       |
       v
Checkout Source Code
       |
       v
Setup Java 21
       |
       v
Build Docker Image
       |
       v
Execute Playwright Tests
       |
       v
Generate Allure Report
       |
       v
Upload Test Artifacts
```

---

## CI Pipeline Stages

The pipeline performs the following steps:

1. Checkout repository
2. Configure Java 21
3. Install Maven dependencies
4. Install Playwright browsers
5. Build Docker image
6. Execute automated tests
7. Collect screenshots, videos and traces
8. Generate Allure test report
9. Upload execution artifacts

---

## Example GitHub Actions Test Command

```bash
mvn clean test \
-Denv=qa \
-Dbrowser=chromium \
-Dheadless=true
```

---

# Test Reports and Artifacts

After execution, the following artifacts are generated:

## Allure Report

Contains:

- Test execution summary
- Test history
- Screenshots
- Videos
- Trace attachments
- Failure details

Generate the report locally:

```bash
allure generate allure-results --clean -o allure-report
```

Open the report:

```bash
allure open allure-report
```

---

## Test Artifacts

The framework captures:

```
test-results/

├── screenshots/
├── videos/
├── traces/
└── logs/

allure-results/

├── test-results.json
├── attachments
└── execution metadata
```

---

# Default Configuration

If no parameters are provided, the framework uses:

```
Browser  = chromium
Headless = true
Environment = qa
```

Example:

```bash
mvn clean test
```

will execute:

```
Chromium Browser
+
Headless Mode
+
QA Environment
```
