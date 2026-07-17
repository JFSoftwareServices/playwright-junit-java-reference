package com.jfsoftwareservices.tests;

import com.jfsoftwareservices.framework.pages.InventoryPage;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class InventoryTest extends AuthenticatedBaseTest {

    @Test
    @Story("View inventory")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify user can view the inventory page after authentication")
    @DisplayName("User can view inventory page")
    void shouldDisplayInventoryPage() {
        InventoryPage inventoryPage = new InventoryPage().navigateTo();

        Assertions.assertTrue(
                inventoryPage.isLoaded(),
                "Inventory page should be displayed"
        );
    }
}