package com.jfsoftwareservices.tests;

import com.jfsoftwareservices.framework.pages.InventoryPage;
import com.jfsoftwareservices.framework.pages.LoginPage;
import com.jfsoftwareservices.testdata.TestUsers;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("SauceDemo Application")
@Feature("Application Smoke Tests")
class SmokeTest extends BaseTest {

    @Test
    @Story("User can access the inventory page")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that a standard user can open the application, login successfully, and access the inventory page")
    @DisplayName("Smoke test - Open application and login successfully")
    void shouldOpenApplication() {

        InventoryPage inventoryPage =
                new LoginPage()
                        .navigateTo()
                        .loginValidUser(
                                TestUsers.standardUser()
                        );

        assertTrue(
                inventoryPage.isLoaded());
    }
}