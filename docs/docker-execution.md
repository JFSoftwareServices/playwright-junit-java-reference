# Docker Execution

## Overview

The framework supports Docker-based test execution using the official Playwright Java Docker image.

Docker provides a consistent execution environment containing:

- Java runtime
- Maven
- Playwright Java dependencies
- Browser binaries
- Browser system dependencies

This removes environment differences between developer machines and CI pipelines.

---

# Prerequisites

Docker must be installed on the host machine before running the framework using containers.

The host machine requires:

- Docker Engine
- Docker CLI
- Docker Compose

The container provides the test execution environment, including:

- Java
- Maven
- Playwright
- Browser binaries
- Browser dependencies

The host machine does not need a local Java, Maven, or Playwright installation when executing tests through Docker.

---

## Verify Docker Installation

Check Docker is available:

```bash
docker --version
```

Example:

```
Docker version 28.x.x
```

Check Docker Compose:

```bash
docker compose version
```

Example:

```
Docker Compose version v2.x.x
```

# Docker Architecture

The execution flow is:

```
Developer / CI Pipeline
        |
        v
Docker Container
        |
        +----------------+
        |                |
        v                v
     Java 21          Maven
        |
        v
  Playwright Java
        |
        v
 Browser Engines
        |
        v
 Test Execution
        |
        v
 Allure Results
```

---

# Docker Image

The framework uses:

```dockerfile
FROM mcr.microsoft.com/playwright/java:v1.52.0-noble
```

The image provides:

- Playwright runtime
- Browser dependencies
- Browser binaries
- Java runtime
- Maven

The project verifies the runtime versions during image creation:

```dockerfile
RUN java -version
RUN mvn -version
```

The Docker environment must remain compatible with the project requirements:

| Component | Version |
|---|---|
| Java | 21 |
| Maven | 3.9.16 compatible |
| Playwright Java | 1.52.0 |

---

# Building the Docker Image

<!-- AMENDED: removed a duplicate "Example" block that repeated the exact
     same build command a second time for no reason. -->

From the project root:

```bash
docker build -t playwright-java-framework .
```

Docker will:

1. Download the Playwright Java base image
2. Copy project files
3. Download Maven dependencies
4. Create the test execution image

---

# Running Tests

Execute the framework inside Docker:

```bash
docker run playwright-java-framework
```

The container will execute:

```bash
mvn clean test
```

---

# Persisting Test Results

By default, files created inside a Docker container are removed when the container stops.

To keep test artefacts — including the Allure results needed to generate
a report afterward (see [Allure Report Generation](../../../Desktop/allure_report_generation.md))
— mount both the `test-results` and `allure-results` directories:

```bash
docker run \
-v ./test-results:/app/test-results \
-v ./allure-results:/app/allure-results \
playwright-java-framework
```

Generated artefacts remain available locally:

```
test-results/
    screenshots/
    traces/
    videos/
    auth/
        storageState.json

allure-results/
    *.json
```

Once the container exits, generate and view the report from the host
exactly as described in [Allure Report Generation](../../../Desktop/allure_report_generation.md):

```bash
allure generate allure-results -o allure-report --clean
```

---

# Browser Configuration

Browser settings are controlled through environment variables.

Supported browsers:

```
chromium
firefox
webkit
```

Example:

```bash
docker run \
-e BROWSER=firefox \
-e HEADLESS=true \
playwright-java-framework
```

Configuration flow:

```
Docker Environment Variables
            |
            v
       TestConfig
            |
            v
    PlaywrightManager
            |
            v
      Browser Launch
```

---

# Authentication State

Authentication state management works inside Docker.

The generated Playwright storage state file:

```
test-results/auth/storageState.json
```

can be persisted using the mounted volume.

This allows authenticated execution while keeping browser session data outside the container.

---

# Docker Compose

The framework can also be executed using Docker Compose.

Docker Compose is used instead of running Docker commands directly because it allows the container configuration to be defined as code and version controlled with the project.

Benefits of using Docker Compose:

- avoids long `docker run` commands
- stores container configuration in a reusable YAML file
- simplifies environment variable configuration
- makes volume mappings explicit
- provides a consistent execution command for developers and CI pipelines
- allows additional services to be added easily in the future (for example databases, mock services, or test dependencies)

The Docker Compose configuration is defined in:

```
docker-compose.yml
```

Example configuration:

```yaml
services:

  playwright-tests:
    build:
      context: .

    volumes:
      - ./test-results:/app/test-results
      - ./allure-results:/app/allure-results

    environment:
      BROWSER: chromium
      HEADLESS: true

    command:
      mvn clean test
```

Run the framework using:

```bash
docker compose up --build
```

Docker Compose will:

1. Build the Playwright test image
2. Create the test container
3. Apply configured environment variables
4. Mount test result and Allure result directories
5. Execute the Maven test suite
6. Persist test results and Allure results on exit

For a single local execution, `docker run` is sufficient.

For repeatable framework execution, CI pipelines, and future expansion, Docker Compose provides a more maintainable approach.

---

# CI/CD Integration

Docker provides a consistent runtime for CI platforms.

Example pipeline:

```
Git Push
    |
    v
CI Pipeline
    |
    v
Build Docker Image
    |
    v
Run Automated Tests
    |
    v
Generate Allure Results
    |
    v
Publish Test Report
```

The same Docker image can be used locally and within CI environments.

Supported CI platforms include:

- GitHub Actions
- Jenkins
- Azure DevOps
- TeamCity

---

# Troubleshooting

## Verify Docker Installation

Check Docker is available:

```bash
docker --version
```

---

## Verify Container Runtime

Run:

```bash
docker run playwright-java-framework
```

The build output should display:

```text
Java version
Maven version
Test execution results
```

---

## Browser Issues

The Playwright Docker image includes browser dependencies.

If browser execution fails:

- rebuild the image
- ensure the Playwright image version matches the project Playwright version
- check container logs

---

# Summary

Docker execution provides:

- consistent test environments
- reproducible execution
- simplified CI integration
- pre-installed Playwright browsers
- isolated test execution
