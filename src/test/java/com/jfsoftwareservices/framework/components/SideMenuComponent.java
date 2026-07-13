package com.jfsoftwareservices.framework.components;

import com.jfsoftwareservices.framework.pages.LoginPage;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

/**
 * Represents the application side menu.
 */
public class SideMenuComponent extends BaseComponent {

    private final Locator logout =
            page.getByTestId("logout-sidebar-link");

    public SideMenuComponent(Page page) {
        super(page);
    }

    public LoginPage logout() {

        click(logout);

        return new LoginPage();
    }

}