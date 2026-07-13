package com.jfsoftwareservices.framework.pages;

import com.microsoft.playwright.Locator;

public class InventoryPage extends BasePage {

    private final Locator pageTitle =
            page.getByTestId("title");

    private final Locator inventoryContainer =
            page.getByTestId("inventory_container");

    private final Locator menuButton =
            page.getByTestId("react-burger-menu-btn");

    private final Locator logoutLink =
            page.getByTestId("logout_sidebar_link");

    /**
     * Returns the page heading.
     *
     * @return page heading
     */
    public String getPageHeading() {
        return getText(pageTitle);
    }

    /**
     * Returns true when the inventory page is loaded.
     *
     * @return true if inventory page is loaded
     */
    public boolean isLoaded() {
        waitForVisible(inventoryContainer);
        return isVisible(inventoryContainer);
    }

    /**
     * Opens the application menu.
     *
     * @return InventoryPage
     */
    public InventoryPage openMenu() {
        click(menuButton);
        return this;
    }

    /**
     * Logs the current user out.
     *
     * @return LoginPage
     */
    public LoginPage logout() {
        openMenu();
        click(logoutLink);

        return new LoginPage();
    }

}