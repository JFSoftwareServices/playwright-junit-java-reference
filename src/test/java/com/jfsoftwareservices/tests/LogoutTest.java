package com.jfsoftwareservices.tests;

import com.jfsoftwareservices.framework.pages.InventoryPage;
import com.jfsoftwareservices.framework.pages.LoginPage;
import com.jfsoftwareservices.testdata.TestUsers;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("SauceDemo Application")
@Feature("Logout")
class LogoutTest extends BaseTest {

    @Test
    @Story("User logout")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify user can logout successfully")
    @DisplayName("User logout successfully")
    void shouldLogoutSuccessfully() {

        InventoryPage inventoryPage =
                new LoginPage()
                        .navigateTo()
                        .loginValidUser(
                                TestUsers.standardUser()
                        );

        assertTrue(inventoryPage.isLoaded());

        inventoryPage.header().openMenu();

        LoginPage loggedOut =
                inventoryPage.menu().logout();

        assertTrue(loggedOut.isLoaded());
    }

}