# Playwright JUnit Java Automation Framework

## Overview

Enterprise-style UI automation framework built with:

- Java 21
- Maven
- Playwright Java
- JUnit 5

This project demonstrates a scalable automation framework design suitable for enterprise UI testing.

---

## Purpose

The framework demonstrates modern test automation practices:

- Component-Based Page Object Model (CBPOM)
- Parallel test execution
- Test data management
- Allure reporting
- Visual testing
- CI/CD integration
- Docker-based execution
- Cloud execution support

BDD testing with Cucumber can be integrated as an extension of the framework.

---

## Application Under Test

This framework uses:

https://www.saucedemo.com/

---

## Technology Stack

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| Maven 3.9.16 | Build management |
| Playwright Java | Browser automation |
| JUnit 5 | Test execution framework |
| Cucumber | BDD support |
| Allure | Test reporting |
| Allure Commandline | Local report generation and viewing |
| GitHub Actions | Continuous integration pipeline |
| Docker | Containerised test execution |

---

## Documentation

Detailed framework documentation:

<!-- AMENDED: allure-reporting.md and allure_report_generation.md descriptions
     updated to reflect actual, non-overlapping scope of each doc. -->

| Document                                                      | Description |
|-----------------------------------------------------------------|---|
| [IntelliJ Setup](docs/setup-intellij.md)                        | Development environment setup |
| [Architecture](docs/architecture.md)                             | Framework design and architecture |
| [Base UI Object](docs/base-ui-object.md)                         | BaseUiObject/BasePage/BaseComponent hierarchy underlying the framework's page objects |
| [Fluent Page Object Model](docs/fluent-page-object-model.md)     | Fluent method chaining pattern used by page objects, and the return-`this`-vs-return-new-page design principle |
| [Running Tests](docs/running-tests.md)                           | Test execution commands |
| [Allure Reporting](docs/allure-reporting.md)                     | Allure/JUnit 5 integration: annotations, `@Step` reporting, failure attachments (screenshots, traces, console logs), network request logging, and CI/CD reporting flow |
| [Allure Report Generation](docs/allure_report_generation.md)     | Installing Allure Commandline (Windows/macOS/Linux) and generating, serving, and troubleshooting the HTML report |
| [Dependency Management](docs/dependency-management.md)          | Maven dependencies, version alignment, and troubleshooting |
| [Browser Lifecycle](docs/browser-lifecycle.md)                  | Browser and Playwright lifecycle management |
| [Parallel Execution](docs/parallel-execution.md)                | Parallel test execution design |
| [Troubleshooting](docs/troubleshooting.md)                      | Common issues and solutions |
| [GitHub Actions](docs/github-actions.md)                        | CI pipeline configuration and automated test execution |
| [Docker Execution](docs/docker-execution.md)                    | Running tests using Docker containers |

---

## Getting Started

Follow the setup guide:

[Setup IntelliJ](docs/setup-intellij.md)

---

## Running with Docker

Docker execution requires Docker to be installed on the host machine.

See:

[Docker Execution](docs/docker-execution.md)

---

## Running Tests

Test execution instructions:

[Running Tests](docs/running-tests.md)

---

## Framework Architecture

The framework follows a Component-Based Page Object Model (CBPOM), separating
test logic, page behaviour, reusable UI components, and browser management
into distinct layers.

Pages represent application screens.

Components represent reusable UI sections shared across multiple pages.

For the full architecture diagram — including authentication state
management and browser context handling — see [Architecture](docs/architecture.md).

The framework separates:

- Test logic
- Page behaviour
- Reusable UI components
- Browser management
- Configuration management
- GitHub Actions CI pipeline
- Docker-based execution

---

## Core Framework Components

The framework includes:

- Playwright Factory
- BaseUiObject
- BasePage
- BaseComponent
- Page Object Model implementation
- Playwright authentication state management
- Allure reporting
- Automatic screenshots on failure
- Playwright trace capture
- Browser console log capture
- Network request logging
- Video capture
- Browser lifecycle management
- Parallel execution support
- Configuration management

---

## Test Reports

Test execution generates Allure result files under `allure-results` (project root).

- For details on test annotations, `@Step` reporting, and what gets attached to a failed test (screenshots, Playwright traces, console logs, network logs), see [Allure Reporting](docs/allure-reporting.md).
- For instructions on installing Allure Commandline and generating/viewing the HTML report locally or in CI, see [Allure Report Generation](docs/allure_report_generation.md).

---

## Future Enhancements

Planned framework capabilities:

- API testing integration
- Environment-based configuration
- Cloud browser execution
- Enhanced Allure reporting
- Deployment promotion workflows
