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

---

## Documentation

Detailed framework documentation:

| Document                                                     | Description |
|--------------------------------------------------------------|---|
| [IntelliJ Setup](docs/setup-intellij.md)                     | Development environment setup |
| [Architecture](docs/architecture.md)                         | Framework design and architecture |
| [Running Tests](docs/running-tests.md)                       | Test execution commands |
| [Allure Reporting](docs/allure-reporting.md)                 | Installing Allure CLI, generating reports, and viewing test results |
| [Allure Report Generation](docs/allure_report_generation.md) | Generating reports and viewing Playwright traces |
| [Dependency Management](docs/dependency-management.md)       | Maven dependencies, version alignment, and troubleshooting |
| [Browser Lifecycle](docs/browser-lifecycle.md)               | Browser and Playwright lifecycle management |
| [Parallel Execution](docs/parallel-execution.md)             | Parallel test execution design |
| [Troubleshooting](docs/troubleshooting.md)                   | Common issues and solutions |

---

## Getting Started

Follow the setup guide:

[Setup IntelliJ](docs/setup-intellij.md)

---

## Running Tests

Test execution instructions:

[Running Tests](docs/running-tests.md)

---

## Framework Architecture

The framework follows a Component-Based Page Object Model (CBPOM).

High-level structure:

```
Test Layer
    |
    v
Page Objects
    |
    v
Reusable Components
    |
    v
Framework Layer
    |
    v
Playwright Browser API
    |
    v
Browser
```

Pages represent application screens.

Components represent reusable UI sections shared across multiple pages.

The framework separates:

- Test logic
- Page behaviour
- Reusable UI components
- Browser management
- Configuration handling

---

## Core Framework Components

The framework includes:

- Playwright Factory
- BaseUiObject
- BasePage
- BaseComponent
- Page Object Model implementation
- Allure reporting
- Automatic screenshots on failure
- Playwright trace capture
- Browser console log capture
- Network request logs
- Video capture
- Browser lifecycle management
- Parallel execution support
- Configuration management

---

## Test Reports

### Allure Reports

Test execution generates Allure result files.  
See the guide below for generating and viewing the HTML report:

[Allure Report Generation Guide](docs/allure_report_generation.md)

## Future Enhancements

Planned framework capabilities:

- API testing integration
- Environment-based configuration
- Authentication handling
- Docker execution
- CI/CD pipeline integration
- Cloud browser execution
- Enhanced Allure reporting
