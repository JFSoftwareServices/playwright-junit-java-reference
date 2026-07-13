package com.jfsoftwareservices.tests;

import com.jfsoftwareservices.framework.pages.InventoryPage;
import com.jfsoftwareservices.framework.pages.LoginPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginTest extends BaseTest {

    @Test
    void shouldLoginSuccessfully() {

        LoginPage loginPage = new LoginPage();

        InventoryPage inventoryPage =
                loginPage
                        .open()
                        .login(
                        "standard_user",
                        "secret_sauce"
                );

        assertTrue(
                inventoryPage.isLoaded()
        );

        assertEquals(
                "Products",
                inventoryPage.header().getTitle()
        );
    }

}