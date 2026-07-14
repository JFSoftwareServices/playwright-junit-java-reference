package com.jfsoftwareservices.framework.pages;

import com.jfsoftwareservices.framework.config.TestConfig;
import com.jfsoftwareservices.framework.model.Credentials;
import com.microsoft.playwright.Locator;
import io.qameta.allure.Step;

public class LoginPage extends BasePage {

    private final Locator usernameField =
            page.getByTestId("username");

    private final Locator passwordField =
            page.getByTestId("password");

    private final Locator loginButton =
            page.getByTestId("login-button");

    private final Locator errorMessage =
            page.locator("[data-test='error']");

    @Step("Navigate to application login page")
    public LoginPage navigateTo() {
        navigate(TestConfig.baseUrl());
        return this;
    }

    @Step("Enter username: {username}")
    public LoginPage enterUsername(String username) {
        fill(usernameField, username);
        return this;
    }

    @Step("Enter password")
    public LoginPage enterPassword(String password) {
        fill(passwordField, password);
        return this;
    }

    @Step("Click login button")
    public LoginPage clickLogin() {
        click(loginButton);
        return this;
    }

    @Step("Login with valid user: {credentials.username}")
    public InventoryPage loginValidUser(Credentials credentials) {
        enterUsername(credentials.username());
        enterPassword(credentials.password());
        clickLogin();
        return new InventoryPage();
    }

    @Step("Login with invalid user: {credentials.username}")
    public LoginPage loginInvalidUser(Credentials credentials) {
        enterUsername(credentials.username());
        enterPassword(credentials.password());
        clickLogin();
        return this;
    }

    @Step("Retrieve login error message")
    public String getErrorMessage() {
        return getText(errorMessage);
    }

    @Step("Verify login page is displayed")
    public boolean isLoaded() {
        waitForVisible(usernameField);
        return isVisible(usernameField);
    }
}