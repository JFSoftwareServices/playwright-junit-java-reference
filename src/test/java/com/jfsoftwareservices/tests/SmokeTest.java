package com.jfsoftwareservices.tests;

import com.jfsoftwareservices.framework.pages.LoginPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SmokeTest extends BaseTest {

    @Test
    void shouldOpenApplication() {

        LoginPage loginPage = new LoginPage();

        loginPage.open();

        assertEquals(
                "Swag Labs",
                loginPage.getTitle()
        );
    }

}