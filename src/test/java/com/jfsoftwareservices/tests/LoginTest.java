package com.jfsoftwareservices.tests;

import com.jfsoftwareservices.framework.pages.InventoryPage;
import com.jfsoftwareservices.framework.pages.LoginPage;
import com.jfsoftwareservices.testdata.TestUsers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginTest extends BaseTest {

    @Test
    void shouldLoginSuccessfully() {

        InventoryPage inventoryPage =
                new LoginPage()
                        .navigateTo()
                        .loginValidUser(
                                TestUsers.standardUser()
                        );

        assertEquals(
                "Products",
                inventoryPage.header().getTitle()
        );
    }

    @Test
    void shouldRejectInvalidCredentials() {

        LoginPage loginPage =
                new LoginPage()
                        .navigateTo()
                        .loginInvalidUser(
                                TestUsers.invalidCredentials()
                        );

        assertEquals(
                "Epic sadface: Username and password do not match any user in this service",
                loginPage.getErrorMessage()
        );
    }
}