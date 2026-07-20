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
- Modal Dialogs

Rather than duplicating locators across multiple page objects,
these sections are modelled as reusable components, each extending
`BaseComponent` — see [Base UI Object](base-ui-object.md) for that
base-class hierarchy.

## Architecture

```
InventoryPage
    |
    +-- HeaderComponent
    |
    +-- SideMenuComponent
```

## Benefits

- Improved reuse
- Better encapsulation
- Smaller page objects
- Easier maintenance
