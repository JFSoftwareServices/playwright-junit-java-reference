# Parallel Execution

## Overview

The framework supports concurrent test execution using:

- JUnit 5 parallel execution engine
- Maven Surefire plugin
- ThreadLocal Playwright resource management

Parallel execution allows multiple automated tests to run simultaneously while maintaining complete browser isolation between tests.

---

## Why Parallel Execution?

As an automation suite grows, sequential execution becomes slower and reduces feedback speed.

Example:

```text
Test 1  →  10 seconds
Test 2  →  10 seconds
Test 3  →  10 seconds
```

Sequential execution:

```text
Total execution time = 30 seconds
```

With parallel execution:

```text
Test 1
Test 2
Test 3

Execute simultaneously
```

The total execution time can be reduced depending on available CPU, memory, and execution environment resources.

Parallel execution is especially valuable in:

- CI/CD pipelines
- Large regression suites
- Cross-browser testing
- Smoke test execution

---

# Thread Isolation

Each executing test thread receives independent:

- Playwright instance
- Browser instance
- BrowserContext
- Page

Example:

```text
Thread 1

JUnit Test

      |
      v

Playwright Instance

      |
      v

Browser

      |
      v

BrowserContext

      |
      v

Page
```

```text
Thread 2

JUnit Test

      |
      v

Playwright Instance

      |
      v

Browser

      |
      v

BrowserContext

      |
      v

Page
```

Each test execution has its own isolated browser session.

This prevents tests from sharing **live** session data during execution,
such as:

- Cookies mutated during a test
- Local storage
- In-session data
- Browser pages

This is distinct from the *shared authentication state seed*
(`storageState.json`), which is intentionally created once and reused by
every thread's `BrowserContext` — see
[Architecture: Authentication State Management](architecture.md#authentication-state-management)
for how that sharing is made thread-safe.

---

# ThreadLocal Design

The framework uses Java `ThreadLocal` to manage Playwright resources.

Example:

```java
private static final ThreadLocal<Page> PAGE =
        new ThreadLocal<>();
```

`ThreadLocal` ensures every parallel test thread receives its own Playwright objects.

Without proper isolation, parallel execution could cause tests to interfere with each other through shared:

- Browser sessions
- Pages
- Test data
- Application state

---

# Browser Context Isolation

Playwright BrowserContext provides lightweight isolated browser sessions.

Each context maintains its own:

- Cookies
- Permissions
- Storage
- Authentication information

Example:

```text
Browser

 |
 +-- BrowserContext 1
 |        |
 |        +-- Test A Page
 |
 |
 +-- BrowserContext 2
          |
          +-- Test B Page
```

This allows multiple users or scenarios to execute independently in parallel.

---

# Configuration

Parallel execution is enabled through:

```text
src/test/resources/junit-platform.properties
```

Example configuration:

```properties
junit.jupiter.execution.parallel.enabled=true

junit.jupiter.execution.parallel.mode.default=concurrent

junit.jupiter.execution.parallel.mode.classes.default=concurrent

junit.jupiter.execution.parallel.config.strategy=dynamic
```

---

# Dynamic Thread Allocation

The framework uses JUnit 5 dynamic parallel execution.

The number of concurrent threads is calculated automatically based on available system resources.

This allows the framework to run consistently across:

- Developer machines
- Build servers
- CI/CD environments

---

# Running Parallel Tests

Execute the complete test suite:

```bash
mvn clean test
```

Run using a specific browser:

```bash
mvn test -Dbrowser=firefox
```

Run with visible browser execution:

```bash
mvn test -Dheadless=false
```

---

# Parallel Testing Best Practices

## Independent Tests

Tests should not depend on another test completing first.

Bad:

```text
Test A creates user

Test B uses user created by Test A
```

Preferred:

```text
Test A creates its own user

Test B creates its own user
```

---

## Avoid Shared Mutable State

Avoid static variables shared between tests.

Bad:

```java
static Customer customer;
```

Preferred:

```java
Customer customer =
        customerFactory.create();
```

<!-- AMENDED: added caveat so this doesn't read as contradicting
     AuthStateManager's own static shared state (AUTH_STATE, AUTH_STATE_LOCK). -->

> **Note:** This doesn't rule out shared static state entirely —
> `AuthStateManager`'s `storageState.json` is deliberately shared static
> state, created once and reused by design. The anti-pattern being warned
> against here is *unguarded, per-test mutable* shared state (like a
> `static Customer` a test writes to), not shared state in general. Shared
> state that's read-only after a single, lock-guarded creation — as with
> `AuthStateManager` — is the safe exception, not the rule being broken.

---

## Use Isolated Test Data

Parallel tests should use independent test data.

Examples:

- Unique usernames
- Unique orders
- Unique customer records
- Unique accounts

---

## Avoid Shared Resources

Do not share:

- Browser instances
- Pages
- Files
- Database records without isolation

---

# Parallel Execution in CI/CD

Parallel execution allows CI pipelines to complete faster.

<!-- AMENDED: fixed a broken arrow flow — an extra blank line between the
     two branches and "Test Results" made the diagram's final arrow read
     as disconnected from the branches above it. -->

Example:

```text
CI Pipeline
      |
      +----------------+
      |                |
      v                v
 Test Thread 1     Test Thread 2
      |                |
      v                v
 Browser Context   Browser Context
      |                |
      +--------+-------+
               |
               v
        Test Results
```

This approach provides faster feedback while maintaining reliable test execution.

---

# Troubleshooting

## Tests interfere with each other

Check that tests are not using:

- static mutable variables
- shared browser objects
- shared test data
- live session data leaking between BrowserContexts (not the same as the
  intentionally shared `storageState.json` seed — see Thread Isolation
  above)

---

## Browser crashes during execution

Reduce the number of parallel workers or increase available resources.

Example:

Developer machine:

```text
2-4 parallel tests
```

CI server:

```text
10+ parallel tests
```

depending on available resources.

---

## Flaky Tests

Parallel execution can expose existing test design problems.

Investigate:

- Missing waits
- Timing dependencies
- Shared application state
- Unreliable test data
- Environment instability

---

# Summary

The framework provides scalable parallel browser automation through:

- JUnit 5 concurrency support
- Maven Surefire integration
- ThreadLocal Playwright management
- BrowserContext isolation

This design enables reliable parallel execution suitable for enterprise automation frameworks and CI/CD environments.
