package com.jfsoftwareservices.framework.pages;

import com.jfsoftwareservices.framework.config.FrameworkConfig;
import com.microsoft.playwright.Locator;

public class LoginPage extends BasePage {

    private final Locator usernameField =
            page.locator("#user-name");

    private final Locator passwordField =
            page.locator("#password");

    private final Locator loginButton =
            page.locator("#login-button");

    private final Locator errorMessage =
            page.locator("[data-test='error']");

    /**
     * Navigates to the application's login page.
     *
     * @return LoginPage
     */
    public LoginPage open() {
        navigate(FrameworkConfig.baseUrl());
        return this;
    }

    /**
     * Enters the username.
     *
     * @param username username
     * @return LoginPage
     */
    public LoginPage enterUsername(String username) {
        fill(usernameField, username);
        return this;
    }

    /**
     * Enters the password.
     *
     * @param password password
     * @return LoginPage
     */
    public LoginPage enterPassword(String password) {
        fill(passwordField, password);
        return this;
    }

    /**
     * Clicks Login.
     *
     * @return InventoryPage
     */
    public InventoryPage clickLogin() {
        click(loginButton);
        return new InventoryPage();
    }

    /**
     * Performs a complete login.
     *
     * @param username username
     * @param password password
     * @return InventoryPage
     */
    public InventoryPage login(String username,
                               String password) {

        enterUsername(username);
        enterPassword(password);

        return clickLogin();
    }

    /**
     * Returns the login error message.
     *
     * @return error message
     */
    public String getErrorMessage() {
        waitForVisible(errorMessage);
        return getText(errorMessage);
    }

}