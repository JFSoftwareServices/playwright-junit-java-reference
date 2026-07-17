package com.jfsoftwareservices.framework.pages;

import com.jfsoftwareservices.framework.components.HeaderComponent;
import com.jfsoftwareservices.framework.components.SideMenuComponent;
import com.jfsoftwareservices.framework.config.TestConfig;
import com.microsoft.playwright.Locator;
import io.qameta.allure.Step;

public class InventoryPage extends BasePage {

    private final Locator inventoryContainer =
            page.getByTestId("inventory-container");

    private final HeaderComponent header =
            new HeaderComponent(page);

    private final SideMenuComponent sideMenu =
            new SideMenuComponent(page);

    @Step("Navigate to inventory page")
    public InventoryPage navigateTo() {
        navigate(TestConfig.baseUrl()+ "/inventory.html" );
        return this;
    }

    @Step("Verify inventory page is displayed")
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