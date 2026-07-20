# Browser Lifecycle

## Overview

The framework manages Playwright browser resources through
`PlaywrightFactory`.

Each test execution receives isolated Playwright resources:

```
Test Thread
|
+-- Playwright
|
+-- Browser
|
+-- BrowserContext
|
+-- Page
```

## Why ThreadLocal?

JUnit 5 can execute tests concurrently.

Playwright Java objects should not be shared unsafely between test threads.
The framework therefore stores browser resources in `ThreadLocal`
containers.

This provides each executing test thread with its own isolated resources.

## Browser Context Isolation

A BrowserContext provides an isolated browser session.

Browser contexts do not share:

- Cookies
- Local storage
- Session storage

This improves test isolation and reduces cross-test contamination.

## Configuration

Default configuration is stored in:

`src/test/resources/config.properties`

Configuration can be overridden from the command line.

Example:

```bash
mvn test -Dbrowser=firefox -Dheadless=false
```
