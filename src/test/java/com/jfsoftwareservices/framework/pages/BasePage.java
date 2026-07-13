package com.jfsoftwareservices.framework.pages;

import com.jfsoftwareservices.framework.factory.PlaywrightFactory;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public abstract class BasePage {

    protected final Page page;

    protected BasePage() {
        this.page = PlaywrightFactory.page();
    }

    protected void navigate(String url) {
        page.navigate(url);
    }

    protected void click(Locator locator) {
        waitForVisible(locator);
        locator.click();
    }

    protected void fill(Locator locator, String value) {
        waitForVisible(locator);
        locator.fill(value);
    }

    protected void type(Locator locator, String value) {
        waitForVisible(locator);
        locator.type(value);
    }

    protected String getText(Locator locator) {
        waitForVisible(locator);
        return locator.textContent();
    }

    protected boolean isVisible(Locator locator) {
        return locator.isVisible();
    }

    protected void waitForVisible(Locator locator) {
        locator.waitFor(
                new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
        );
    }

    public String getTitle() {
        return page.title();
    }

    public String getCurrentUrl() {
        return page.url();
    }
}