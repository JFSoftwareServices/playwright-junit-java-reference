package com.jfsoftwareservices.framework.pages;

import com.jfsoftwareservices.framework.components.HeaderComponent;
import com.jfsoftwareservices.framework.components.SideMenuComponent;
import com.microsoft.playwright.Locator;

public class InventoryPage extends BasePage {

    private final Locator inventoryContainer =
            page.getByTestId("inventory-container");

    private final HeaderComponent header =
            new HeaderComponent(page);

    private final SideMenuComponent sideMenu =
            new SideMenuComponent(page);

    public boolean isLoaded() {

        waitForVisible(inventoryContainer);

        return isVisible(inventoryContainer);
    }

    public HeaderComponent header() {
        return header;
    }

    public SideMenuComponent menu() {
        return sideMenu;
    }

}