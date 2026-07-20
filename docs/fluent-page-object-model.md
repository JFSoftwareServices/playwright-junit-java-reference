# Fluent Page Object Model

## Overview

The framework uses a fluent Page Object Model to improve readability and maintainability.

Rather than exposing low-level browser interactions in tests, page objects provide high-level business actions.

## Traditional Page Object

```java
LoginPage loginPage = new LoginPage();

loginPage.enterUsername("standard_user");
loginPage.enterPassword("secret_sauce");
loginPage.clickLogin();

InventoryPage inventoryPage = new InventoryPage();
```

## Fluent Page Object

```java
InventoryPage inventoryPage =
        new LoginPage()
                .navigateTo()
                .login(
                        TestUsers.standardUser()
                );
```

The fluent approach is shorter, easier to read, and better represents the user's journey.

## Design Principles

### Return `this`

Methods that do not navigate to another page return the current page object.

Example:

```java
loginPage.enterUsername("standard_user")
         .enterPassword("secret_sauce");
```

### Return a new page

Methods that navigate to another page return the destination page object.

Example:

```java
InventoryPage inventoryPage =
        loginPage.login(
                TestUsers.standardUser()
        );
```

## Benefits

- Improved readability
- Reduced boilerplate
- Better encapsulation
- Easier maintenance
- Tests express business behaviour rather than UI mechanics

## Guidelines

Page objects should:

- Encapsulate Playwright interactions.
- Expose business actions.
- Hide locators from tests.
- Never contain assertions.

Tests should:

- Perform assertions.
- Describe business scenarios.
- Never interact directly with Playwright APIs.
