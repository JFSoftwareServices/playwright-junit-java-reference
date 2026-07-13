package com.jfsoftwareservices.framework.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * Represents the common application header.
 */
public class HeaderComponent extends BaseComponent {

    private final Locator pageTitle =
            page.getByTestId("title");

    private final Locator menuButton =
            page.getByTestId("open-menu");

    private final Locator shoppingCart =
            page.getByTestId("shopping-cart-link");

    public HeaderComponent(Page page) {
        super(page);
    }

    public String getTitle() {
        return getText(pageTitle);
    }

    public void openMenu() {
        click(menuButton);
    }

    public void openCart() {
        click(shoppingCart);
    }
}