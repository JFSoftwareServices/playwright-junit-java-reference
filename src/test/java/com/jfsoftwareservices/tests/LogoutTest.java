package com.jfsoftwareservices.tests;

import com.jfsoftwareservices.framework.pages.LoginPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LogoutTest extends BaseTest {

    @Test
    void shouldLogoutSuccessfully() {

        LoginPage loginPage =
                new LoginPage()
                        .open()
                        .login(
                                "standard_user",
                                "secret_sauce"
                        )
                        .logout();

        assertTrue(
                loginPage.getCurrentUrl().contains("saucedemo")
        );
    }

}