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
- Automatic videos
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
- Videos

These attachments are automatically added to the failed test in the Allure report for easier failure investigation.

<!-- ADDED: clarifies that video recording itself is not failure-only,
     even though video only appears as a report attachment on failure —
     this distinction wasn't previously stated anywhere in the doc. -->
> **Note:** Videos are the one exception to "capture on failure" — video
> recording runs for every test regardless of outcome (see
> [Video Capture](#video-capture) below). Only the failure-attachment
> *inclusion in the report* is failure-only, same as the other attachments
> in this list.

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

## Video Capture

<!-- ADDED: new subsection, matching the structure of Screenshot Capture and
     Playwright Trace Capture above. Documents the recording-vs-attachment
     distinction requested: videos record for every test, pass or fail, but
     are only attached to the Allure report for failed tests. -->

Unlike screenshots and traces, video recording is **not** conditional on
test outcome. The framework records a video for every test that runs,
regardless of whether it passes or fails.

Videos are:

- saved locally under:

```
test-results/
    videos/
```

- attached to the Allure report **only for failed tests**.

Videos for passing tests are still written to `test-results/videos/` on
disk, but are not attached to the Allure report — they are not needed to
diagnose a pass, and attaching video for every test would add unnecessary
size and noise to the report. If a passing test's video needs to be
reviewed, it can still be found in `test-results/videos/` directly, outside
of Allure.

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
    |
    +-- Videos
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

After execution, Allure results are generated:

```
allure-results/
  |
  +-- *.json
```

To install Allure Commandline and generate or view the HTML report from
these results, see [Allure Report Generation](allure_report_generation.md).

---

# CI/CD Integration

For the concrete GitHub Actions workflow steps used by this project
(including how results are uploaded and where the report is stored),
see [GitHub Actions](github-actions.md).

For running the suite — and generating results — inside a container, see
[Docker Execution](docker-execution.md).

Result files use the same `allure-results` path described in this document. However,
that directory exists inside the container's filesystem by default. To generate a report
afterward, mount `allure-results/` as a
volume so it's accessible on the host once
the container exits — otherwise it will
need to be copied out manually before running `allure generate`.

---
