package com.jfsoftwareservices.framework.pages;

import com.jfsoftwareservices.framework.config.TestConfig;
import com.jfsoftwareservices.framework.model.Credentials;
import com.microsoft.playwright.Locator;

public class LoginPage extends BasePage {

    private final Locator usernameField =
            page.getByTestId("username");

    private final Locator passwordField =
            page.getByTestId("password");

    private final Locator loginButton =
            page.getByTestId("login-button");

    private final Locator errorMessage =
            page.locator("[data-test='error']");

    public LoginPage navigateTo() {
        navigate(TestConfig.baseUrl());
        return this;
    }

    public LoginPage enterUsername(String username) {
        fill(usernameField, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        fill(passwordField, password);
        return this;
    }

    public LoginPage clickLogin() {
        click(loginButton);
        return this;
    }

    public InventoryPage loginValidUser(Credentials credentials) {
        enterUsername(credentials.username());
        enterPassword(credentials.password());

        click(loginButton);

        return new InventoryPage();
    }

    public LoginPage loginInvalidUser(Credentials credentials) {
        enterUsername(credentials.username());
        enterPassword(credentials.password());

        click(loginButton);

        return this;
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isLoaded() {
        waitForVisible(usernameField);
        return isVisible(usernameField);
    }
}