package com.jfsoftwareservices.tests;

import com.jfsoftwareservices.framework.pages.InventoryPage;
import com.jfsoftwareservices.framework.pages.LoginPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LogoutTest extends BaseTest {

    @Test
    void shouldLogoutSuccessfully() {

        LoginPage loginPage = new LoginPage();

        InventoryPage inventoryPage =
                loginPage
                        .open()
                        .login(
                        "standard_user",
                        "secret_sauce"
                );

        assertTrue(inventoryPage.isLoaded());

        inventoryPage.header().openMenu();

        LoginPage loggedOut =
                inventoryPage.menu().logout();

        assertTrue(loggedOut.isLoaded());
    }

}