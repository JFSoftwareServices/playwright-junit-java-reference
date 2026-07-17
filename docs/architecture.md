# Framework Architecture

The framework follows a layered architecture that separates test logic from browser automation concerns.

```
Test Layer
      |
      v
BDD / JUnit Tests
      |
      v
Page Objects
      |
      v
Playwright Framework
      |
      +----------------------+
      |                      |
      |                      |
      v                      v
PlaywrightFactory     AuthStateManager
      |                      |
      +----------+-----------+
                 |
                 v
          Browser Context
                 |
                 v
          Browser Engine
```

---

# Authentication State Management

## Overview

The framework supports authenticated test execution using Playwright's storage state feature.

Instead of logging in before every test, an authenticated browser session is created once and reused by subsequent tests.

This significantly reduces execution time for tests that require an authenticated user.

---

## How It Works

The first authenticated test performs the normal login flow using a temporary browser page.

After a successful login, Playwright saves the browser state to:

```
test-results/
    auth/
        storageState.json
```

The saved state contains the browser session information required to restore an authenticated user.

Subsequent authenticated tests create a new browser context using the saved storage state without performing another login.

The overall flow is:

```
Authenticated Test
        |
        v
Authentication state exists?
        |
   +----+----+
   |         |
  Yes        No
   |         |
   |     Login using
   |     standard user
   |         |
   |         v
   |   Save storage state
   |         |
   +---------+
        |
        v
Create authenticated BrowserContext
        |
        v
Execute test
```

---

## Framework Components

Authentication state is managed by:

```
AuthStateManager
```

Responsibilities include:

- creating Playwright authentication state
- saving authenticated browser state
- creating authenticated browser contexts
- reusing saved authentication state

---

## Authenticated Tests

Tests that require a logged-in user should extend:

```java
AuthenticatedBaseTest
```

Example:

```java
@Epic("SauceDemo Application")
@Feature("Inventory Management")
class InventoryTest extends AuthenticatedBaseTest {

    @Test
    @Story("Inventory page")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify an authenticated user can view the inventory page")
    @DisplayName("User can view inventory page")
    void shouldDisplayInventoryPage() {

        InventoryPage inventoryPage =
                new InventoryPage();

        Assertions.assertTrue(
                inventoryPage.isLoaded()
        );
    }
}
```

Tests that verify authentication behaviour (login, logout, invalid credentials, locked users, etc.) should continue extending:

```java
BaseTest
```

This ensures the complete authentication flow is exercised independently of the saved authentication state.

---

## Benefits

Using Playwright authentication state provides several advantages:

- faster test execution
- eliminates repeated login steps
- cleaner test code
- improved test readability
- consistent authenticated sessions
- reduced application load during large test suites

---

---

## Parallel Execution

The authentication mechanism is designed to work with the framework's parallel execution model.

Each test thread creates its own:

- `BrowserContext`
- `Page`

using the shared authentication state.

```
                storageState.json
                       |
        +--------------+--------------+
        |              |              |
        v              v              v
   BrowserContext BrowserContext BrowserContext
        |              |              |
        v              v              v
      Page           Page           Page
```

The authentication state file is created only when it does not already exist.

Once generated, it is treated as read-only and can be safely reused by multiple concurrent tests.

This approach provides:

- thread-safe authenticated execution
- isolated browser contexts for every test
- support for parallel execution without repeated logins
- improved execution performance for large test suites

## Authentication State File

The generated authentication state is stored at:

```
test-results/
    auth/
        storageState.json
```

The file contains Playwright browser state, including cookies and local storage used to restore an authenticated browser session.

It does not contain test code or application credentials. Instead, it stores the browser session information required for Playwright to recreate an authenticated browser context.

The file is generated automatically and should not be committed to source control.

---

## When to Use

Use authentication state for tests that require an already authenticated user, such as:

- inventory pages
- product pages
- shopping cart
- checkout flow
- account management
- order history

Do **not** use authentication state when testing the authentication process itself.

Tests covering login, logout, invalid credentials, locked users or authentication error handling should execute the complete login flow by extending `BaseTest`.