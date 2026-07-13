# Playwright JUnit Java Automation Framework


## Overview

Enterprise-style UI automation framework built using:

- Java 21
- Maven
- Playwright Java
- JUnit 5


## Purpose

This project demonstrates modern test automation practices:

- Page Object Model
- Parallel execution
- Test data management
- BDD testing
- Allure reporting
- Visual testing
- CI/CD integration
- Docker execution
- Cloud execution

## Application Under Test

This framework uses:

https://www.saucedemo.com/


## Technology Stack

| Technology | Purpose |
|------------|---|
| Java 21    | Programming language |
| Maven  3.9.16    | Build management |
| Playwright | Browser automation |
| JUnit 5    | Test execution |
| Cucumber   | BDD |
| Allure     | Reporting |


## Documentation

- [IntelliJ Setup](docs/setup-intellij.md)
- [Architecture](docs/architecture.md)
- [Running Tests](docs/running-tests.md)
- [Browser Lifecycle](docs/browser-lifecycle.md)
- [Troubleshooting](docs/troubleshooting.md)
- [Parallel Execution](docs/parallel-execution.md)


## Getting Started

See:

/docs/setup-intellij.md


## Running Tests

See:

/docs/running-tests.md


## Architecture

See:

/docs/architecture.md

## Core Framework

- Playwright Factory
- BaseUiObject
- BasePage
- BaseComponent
- Parallel Execution
- Page Object Model

## Page Object Model

The framework uses a Component-Based Page Object Model (CBPOM).

Pages represent application screens.

Components represent reusable UI sections shared across pages.