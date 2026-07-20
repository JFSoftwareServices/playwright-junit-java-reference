# GitHub Actions CI Pipeline

## Overview

The framework includes a GitHub Actions workflow to automatically execute the Playwright test suite.

The pipeline provides:

- automated test execution
- consistent build validation
- Maven dependency caching
- Playwright browser installation
- test artefact collection

The workflow runs automatically when changes are pushed or pull requests are created.

---

# Pipeline Location

The workflow is located at:

```
.github/
└── workflows/
    └── ci.yml
```

---

# Pipeline Flow

```
Git Push / Pull Request
        |
        v
GitHub Actions Runner
        |
        v
Checkout Repository
        |
        v
Setup Java 21
        |
        v
Restore Maven Dependencies
        |
        v
Install Playwright Browsers
        |
        v
Execute Automated Tests
        |
        v
Upload Test Artefacts
```

---

# Trigger Conditions

The pipeline executes on:

## Push to main

```yaml
push:
  branches:
    - main
```

## Pull requests targeting main

```yaml
pull_request:
  branches:
    - main
```

This ensures changes are validated before merging.

---

# Runtime Environment

The workflow executes on:

```
ubuntu-latest
```

The environment provides:

- Linux runner
- Java 21
- Maven
- Playwright dependencies

---

# Java Configuration

The pipeline uses:

```
Java 21
```

matching the project requirement.

Configured using:

```yaml
actions/setup-java@v4
```

---

# Maven Dependency Caching

Maven dependencies are cached using:

```yaml
cache: maven
```

This reduces execution time by avoiding repeated dependency downloads.

---

# Test Execution

The framework is executed using:

```bash
mvn clean test
```

This:

1. Cleans previous build output
2. Compiles the project
3. Executes JUnit tests
4. Generates test results

---

# Test Artefacts

Regardless of test success or failure, the pipeline uploads:

```
test-results/
    screenshots/
    traces/
    videos/
    auth/

allure-results/
```

These artefacts allow investigation of failed CI executions.

> **Note:** This pipeline uploads the raw `test-results/` and
> `allure-results/` artefacts only. It does not currently run
> `allure generate` or publish an HTML report anywhere (e.g. GitHub
> Pages). To view a report from a CI run, download the `allure-results`
> artefact and generate it locally — see
> [Allure Report Generation](allure_report_generation.md). Publishing the
> report automatically as part of the pipeline is listed under
> [Future CI/CD Expansion](#future-cicd-expansion) below.

> **Security note:** The uploaded `test-results/auth/` directory
> includes `storageState.json`, which contains browser session
> cookies capable of restoring an authenticated session (see
> [Architecture](architecture.md#authentication-state-file)). Anyone
> with access to this CI artifact can potentially reuse that session.
> Restrict artifact access accordingly, or exclude `auth/` from the
> uploaded artefact if this exposure isn't acceptable for your
> environment.

---

# Future CI/CD Expansion

The current workflow provides continuous integration.

Future enhancements can include:

- Docker image execution
- environment promotion
- manual approval gates
- deployment pipelines
- cloud browser execution
- automatic Allure report generation and publishing (e.g. to GitHub Pages)
