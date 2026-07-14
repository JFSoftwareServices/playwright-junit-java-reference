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
- Attachments (screenshots, traces, logs)

The integration uses:

- Allure JUnit 5 adapter
- Allure Maven plugin
- Allure Commandline


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
target/allure-results
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

# Installing Allure Commandline

Allure Commandline is required to generate and view reports locally.

## Windows

### Using Chocolatey

```powershell
choco install allure
```

### Using Scoop

```powershell
scoop install allure
```


Verify installation:

```powershell
allure --version
```


---

## macOS

Using Homebrew:

```bash
brew install allure
```

Verify:

```bash
allure --version
```


---

# Running Tests

Execute the test suite:

```bash
mvn clean test
```

After execution, Allure results are generated:

```
target/
 |
 +-- allure-results/
       |
       +-- *.json
```


---

# Viewing Allure Reports

Start a local report:

```bash
allure serve target/allure-results
```

This will:

1. Read the generated Allure results
2. Generate an HTML report
3. Start a temporary local web server
4. Open the report in the browser


---

# Generating a Permanent Report

To generate a report directory:

```bash
allure generate target/allure-results -o target/allure-report
```

Open the generated report:

```bash
allure open target/allure-report
```


---

# Troubleshooting

## No Allure Results Generated

Check that tests completed successfully:

```bash
mvn clean test
```

Verify:

```
target/allure-results
```

exists.


---

## Allure Command Not Found

Example:

```
allure: command not found
```

Install Allure Commandline and restart the terminal.


---

## JUnit Platform Version Alignment

JUnit 5 consists of multiple components:

```
JUnit Jupiter
        |
        v
JUnit Platform Engine
        |
        v
JUnit Platform Launcher
```

All components must use compatible versions.

A version mismatch can cause errors during test discovery, for example:

```
OutputDirectoryProvider not available;
probably due to unaligned versions of the
junit-platform-engine and junit-platform-launcher jars
```

This framework resolves version alignment by ensuring:

```
JUnit Jupiter          5.13.4
JUnit Platform         1.13.4
```

are using the same release family.


---

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

The pipeline does not require `allure serve`.

The CI server generates and publishes the report automatically.


---

# Future Enhancements

Planned reporting improvements:

- Screenshot capture on failure
- Playwright trace attachment
- Browser console log capture
- Network request logging
- Video capture for failed tests