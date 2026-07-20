# Browser Lifecycle

## Overview

The framework manages Playwright browser resources through
`PlaywrightFactory`.

Each test execution receives its own isolated `Playwright`, `Browser`,
`BrowserContext`, and `Page`, managed via `ThreadLocal`. For the full
explanation of why, and how thread isolation works across the framework,
see [Parallel Execution](../../../Desktop/files/parallel-execution.md).

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
