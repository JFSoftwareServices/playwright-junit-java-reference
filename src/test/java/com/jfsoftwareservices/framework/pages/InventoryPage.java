package com.jfsoftwareservices.framework.pages;

import com.microsoft.playwright.Locator;

public class InventoryPage extends BasePage {

    private final Locator pageTitle =
            page.locator("[data-test='title']");

    private final Locator inventoryContainer =
            page.locator("#inventory_container");

    private final Locator menuButton =
            page.locator("#react-burger-menu-btn");

    private final Locator logoutLink =
            page.locator("#logout_sidebar_link");

    /**
     * Returns the page heading.
     *
     * @return page heading
     */
    public String getPageHeading() {
        waitForVisible(pageTitle);
        return getText(pageTitle);
    }

    /**
     * Returns true when the inventory page is displayed.
     *
     * @return true if inventory page is displayed
     */
    public boolean isDisplayed() {
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