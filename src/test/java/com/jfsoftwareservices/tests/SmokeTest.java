package com.jfsoftwareservices.tests;

import com.jfsoftwareservices.framework.pages.InventoryPage;
import com.jfsoftwareservices.framework.pages.LoginPage;
import com.jfsoftwareservices.testdata.TestUsers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SmokeTest extends BaseTest {

    @Test
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