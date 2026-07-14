package com.jfsoftwareservices.tests;

import com.jfsoftwareservices.framework.pages.InventoryPage;
import com.jfsoftwareservices.framework.pages.LoginPage;
import com.jfsoftwareservices.testdata.TestUsers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LogoutTest extends BaseTest {

    @Test
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