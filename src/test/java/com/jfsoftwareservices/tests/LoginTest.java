package com.jfsoftwareservices.tests;

import com.jfsoftwareservices.framework.pages.InventoryPage;
import com.jfsoftwareservices.framework.pages.LoginPage;
import com.jfsoftwareservices.testdata.TestUsers;
import io.qameta.allure.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("SauceDemo Application")
@Feature("Login")
class LoginTest extends BaseTest {

    @Test
    @Story("Valid user login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify user can login successfully with valid credentials")
    @DisplayName("User login with valid credentials")
    void shouldLoginSuccessfully() {

        InventoryPage inventoryPage =
                new LoginPage()
                        .navigateTo()
                        .loginValidUser(
                                TestUsers.standardUser()
                        );

        assertEquals("Products", inventoryPage.header().getTitle()
        );
    }

    @Test
    @Story("Invalid user login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify user can not login with invalid credentials")
    @DisplayName("User login with invalid credentials")
    void shouldRejectInvalidCredentials() {

        LoginPage loginPage =
                new LoginPage()
                        .navigateTo()
                        .loginInvalidUser(
                                TestUsers.invalidCredentials()
                        );

        assertEquals("Epic sadface: Username and password do not match any user in this service", loginPage.getErrorMessage()
        );
    }
}