# Base UI Object

## Overview

The framework separates browser pages from reusable UI components by introducing a common `BaseUiObject`.

```
                 BaseUiObject
                       │
          ┌────────────┴────────────┐
          │                         │
      BasePage               BaseComponent
```

## Responsibilities

### BaseUiObject

Contains reusable Playwright interactions:

- click
- fill
- type
- hover
- clear
- getText
- waitForVisible
- waitForHidden

### BasePage

Contains page-level navigation:

- navigate
- refresh
- goBack
- goForward
- getTitle
- getCurrentUrl

### BaseComponent

Represents reusable sections of a page, such as:

- Header
- Footer
- Side menu
- Shopping cart
- Modal dialogs

## Benefits

- Eliminates duplicated interaction logic.
- Encourages reusable page components.
- Improves maintainability.
- Keeps page objects focused on business behaviour.