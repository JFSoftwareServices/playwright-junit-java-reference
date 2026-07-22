# Troubleshooting Guide

## 1. Playwright Browser Installation Issues

### Problem

Tests fail with errors such as:

```
Executable doesn't exist
Looks like Playwright was just installed
```

### Cause

Playwright browser binaries have not been installed.

### Solution

Install Playwright browsers:

```bash
mvn exec:java \
-Dexec.mainClass=com.microsoft.playwright.CLI \
-Dexec.args="install"
```

or:

```bash
npx playwright install
```

---

# 2. Browser Launch Failures

## Problem

Browser fails to start:

```
BrowserType.launch: Executable doesn't exist
```

## Solution

Verify installed browsers:

```bash
playwright install --list
```

Reinstall browsers:

```bash
mvn exec:java \
-Dexec.mainClass=com.microsoft.playwright.CLI \
-Dexec.args="install --force"
```

---

# 3. Headless Mode Issues

## Problem

Tests pass locally but fail in CI.

## Cause

Browser requires headless execution in environments without a display server.

## Solution

Run with:

```bash
mvn clean test -Dheadless=true
```

For Docker and CI environments:

```yaml
HEADLESS: true
```

---

# 4. Test Fails Due to Missing Environment Variables

## Problem

Configuration values are missing:

```
Environment not found
Browser not configured
```

## Solution

Provide required parameters:

```bash
mvn clean test \
-Denv=qa \
-Dbrowser=chromium \
-Dheadless=true
```

---

# 5. Docker Build Failures

## Problem

Docker build fails:

```
Could not resolve dependencies
```

## Solution

Clean Docker cache:

```bash
docker builder prune
```

Rebuild:

```bash
docker build --no-cache -t playwright-java-framework .
```

---

# 6. Docker Container Cannot Write Test Results

## Problem

Allure results or screenshots are missing.

## Cause

Volume permissions or incorrect mount paths.

## Solution

Verify volumes:

```bash
docker run --rm \
-v $(pwd)/test-results:/app/test-results \
-v $(pwd)/allure-results:/app/allure-results \
playwright-java-framework
```

Check container paths:

```bash
docker exec -it <container_id> bash
```

---

# 7. Allure Report Shows No Tests

## Problem

Allure opens but displays:

```
0 test cases
```

## Cause

No files exist in:

```
allure-results/
```

## Solution

Check results:

```bash
ls -la allure-results
```

Generate report:

```bash
allure generate allure-results --clean -o allure-report
```

Open:

```bash
allure open allure-report
```

---

# 8. GitHub Actions Cannot Find Test Results

## Problem

Artifacts are missing after CI execution.

## Solution

Verify the workflow uploads:

```yaml
- name: Upload Test Results
  uses: actions/upload-artifact@v4
  with:
    name: test-results
    path: test-results/
```

Check:

- Artifact path
- Directory creation
- Workflow permissions

---

# 9. Tests Fail in Parallel Execution

## Problem

Tests interfere with each other.

Example:

```
Page is closed
Browser context already disposed
```

## Cause

Shared Playwright objects between threads.

## Solution

Use ThreadLocal isolation:

```
Thread
 |
 +-- Playwright
      |
      +-- Browser
           |
           +-- BrowserContext
                |
                +-- Page
```

Each test thread should have its own browser context.

---

# 10. Debugging Failed Tests

Enable trace capture:

```bash
mvn clean test -Dtrace=true
```

View trace:

```bash
npx playwright show-trace trace.zip
```

Capture screenshots:

```
test-results/screenshots/
```

Capture videos:

```
test-results/videos/
```

---

# 11. Maven Dependency Issues

## Problem

Build fails after dependency changes.

## Solution

Clean local Maven cache:

```bash
mvn dependency:purge-local-repository
```

Rebuild:

```bash
mvn clean test
```

---

# 12. CI Debug Checklist

Before investigating test failures:

Check:

☐ Java version matches project requirement

```bash
java -version
```

☐ Maven version

```bash
mvn -version
```

☐ Playwright browsers installed

☐ Environment variables configured

☐ Docker image built successfully

☐ Test artifacts uploaded

☐ Allure results generated

---

# Getting Help

When reporting a failure include:

- Operating system
- Java version
- Maven version
- Browser
- Environment
- Full stack trace
- Test name
- CI build link