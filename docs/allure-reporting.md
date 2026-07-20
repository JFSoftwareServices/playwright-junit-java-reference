# Allure Reporting

## Overview

This framework integrates **Allure Reporting** with **JUnit 5** to provide detailed test execution reports.

Allure enhances test reporting by providing:

- Test execution status
- Test steps
- Test descriptions
- Severity classification
- Test history
- Failure analysis
- Automatic screenshot capture on failure
- Automatic Playwright trace capture on failure
- Automatic browser console log capture on failure
- Support for additional diagnostic attachments

The integration uses:

- Allure JUnit 5 adapter
- Allure Maven plugin
- Allure Commandline

> **Note:** This document covers what the framework *records* into a test
> run (annotations, steps, failure attachments, network logs) and how that
> flows into CI. For installing Allure Commandline and generating/viewing
> the HTML report itself, see [Allure Report Generation](allure_report_generation.md).

---

# Architecture

The reporting flow is:

```
JUnit 5 Tests
       |
       v
allure-junit5
       |
       v
allure-results
       |
       v
Allure Commandline
       |
       v
HTML Test Report
```

---

# Maven Dependencies

The framework uses:

```xml
<dependency>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-junit5</artifactId>
    <version>2.29.1</version>
    <scope>test</scope>
</dependency>
```

The dependency integrates JUnit 5 test execution with Allure.

The Maven plugin generates Allure reports:

```xml
<plugin>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-maven</artifactId>
</plugin>
```

---

# Test Annotations

Allure annotations provide metadata for test reporting.

Example:

```java
@Epic("SauceDemo Application")
@Feature("Application Smoke Tests")
class SmokeTest extends BaseTest {


    @Test
    @Story("User can access inventory page")
    @Severity(SeverityLevel.BLOCKER)
    @Description(
        "Verify a standard user can login successfully and access the inventory page"
    )
    @DisplayName(
        "Smoke test - Open application and login successfully"
    )
    void shouldOpenApplication() {

    }
}
```

The report hierarchy becomes:

```
Epic
 |
 +-- Feature
       |
       +-- Story
             |
             +-- Test
```

---

# Allure Steps

Page Object methods are annotated with `@Step` to provide readable execution details.

Example:

```java
@Step("Login with valid user: {credentials.username}")
public InventoryPage loginValidUser(
        Credentials credentials) {

    enterUsername(credentials.username());

    enterPassword(credentials.password());

    clickLogin();

    return new InventoryPage();
}
```

The Allure report displays:

```
Login with valid user: standard_user

    ✓ Enter username
    ✓ Enter password
    ✓ Click login button
```

---

# Failure Attachments

The framework automatically captures diagnostic artifacts when a test fails.

Current failure attachments include:

- Playwright screenshots
- Playwright traces
- Browser console logs

These attachments are automatically added to the failed test in the Allure report for easier failure investigation.

---

## Screenshot Capture

When a test fails, the framework captures a full-page screenshot of the browser.

The screenshot is:

- saved locally under:

```
test-results/
    screenshots/
```

- attached to the corresponding failed test in the Allure report.

This allows the final browser state to be inspected without rerunning the test.

---

## Playwright Trace Capture

The framework starts Playwright tracing when a browser context is created.

When a test fails, tracing is stopped and the recorded trace is:

- saved locally under:

```
test-results/
    traces/
```

- attached to the failed test in the Allure report.

Playwright traces contain:

- browser actions
- page navigation
- screenshots
- DOM snapshots
- timing information

These traces provide detailed insight into how the browser reached the failure state.


To open a captured trace interactively, see **Viewing Playwright Traces**
in [Allure Report Generation](allure_report_generation.md).

---

## Browser Console Log Capture

The framework captures browser console messages during test execution.

Console messages are collected from the Playwright page and attached to failed tests in the Allure report.

Browser console logging helps identify client-side problems that may not be visible from the test failure alone.

Captured messages may include:

- JavaScript errors
- Browser warnings
- Failed resource requests
- Frontend application logs

Example:

```
ERROR: Failed to load resource: server returned 500

WARNING: Deprecated API usage

INFO: User authentication completed
```

The Allure report displays the console logs as an attachment:

```
Failed Test

    +-- Screenshot on failure
    |
    +-- Playwright Trace
    |
    +-- Browser Console Logs
```

Browser console capture is particularly useful for diagnosing:

- Frontend JavaScript failures
- API integration issues
- Authentication problems
- Client-side rendering errors

---

# Network Request Logging

The framework captures browser network activity using Playwright request and response listeners.

Network logging provides visibility into communication between the browser and backend services.

Captured information includes:

- HTTP method
- Request URL
- Response status code
- Failed requests

Example:

```text
GET https://www.saucedemo.com/
STATUS: 200


POST https://api.example.com/login
STATUS: 401


GET https://api.example.com/products
STATUS: 500
```

Network reporting helps diagnose:

- API failures
- Authentication problems
- Incorrect backend responses
- Missing resources
- Frontend/backend integration issues

The captured network information is attached to failed tests in the Allure report.

Example:

```
Failed Test

    +-- Screenshot on failure
    |
    +-- Playwright Trace
    |
    +-- Browser Console Logs
    |
    +-- Network Logs
```

Network capture is especially useful for:

- REST API integrations
- Microservice applications
- Authentication flows
- Financial/trading applications where UI behaviour depends on backend services

---

# Running Tests

Execute the test suite:

```bash
mvn clean test
```

After execution, Allure results are generated at the project root:

```
allure-results/
 |
 +-- *.json
```

To install Allure Commandline and generate or view the HTML report from
these results, see [Allure Report Generation](allure_report_generation.md).

---

For JUnit Platform / Allure version alignment issues (including the
`OutputDirectoryProvider not available` test-discovery error and how it
was resolved), see [Dependency Management](dependency-management.md).

# CI/CD Integration

In CI/CD pipelines:

```
Build Pipeline
      |
      v
mvn test
      |
      v
allure-results
      |
      v
CI Allure Plugin
      |
      v
Published HTML Report
```

The pipeline does not require:

```bash
allure serve
```

The CI server generates and publishes the report automatically.

For the concrete GitHub Actions workflow steps used by this project
(including how results are uploaded and where the report is published),
see [GitHub Actions](github-actions.md).

For running the suite — and generating results — inside a container, see
[Docker Execution](docker-execution.md); result-file locations follow the
same `allure-results` path shown above, but the report must be
generated from a location where the `allure-results/` directory is
accessible (e.g. a mounted volume) once the container exits.

---
