# Page Components

## Why components?

Large web applications contain many reusable UI sections.

Examples:

- Header
- Footer
- Navigation
- Side Menu
- Shopping Cart
- Product Grid

Rather than duplicating locators across multiple page objects,
these sections are modelled as reusable components.

## Architecture

InventoryPage
│
├── HeaderComponent
│
└── SideMenuComponent

## Benefits

- Improved reuse
- Better encapsulation
- Smaller page objects
- Easier maintenance