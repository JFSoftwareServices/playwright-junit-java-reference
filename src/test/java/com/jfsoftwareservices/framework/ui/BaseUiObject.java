package com.jfsoftwareservices.framework.ui;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

/**
 * Base class for all UI objects.
 *
 * Provides common Playwright interactions shared by pages
 * and reusable page components.
 */
public abstract class BaseUiObject {

    protected final Page page;

    protected BaseUiObject(Page page) {
        this.page = page;
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

    protected boolean isHidden(Locator locator) {

        return locator.isHidden();
    }

    protected boolean isEnabled(Locator locator) {

        return locator.isEnabled();
    }

    protected boolean isDisabled(Locator locator) {

        return locator.isDisabled();
    }

    protected void hover(Locator locator) {

        waitForVisible(locator);

        locator.hover();
    }

    protected void clear(Locator locator) {

        waitForVisible(locator);

        locator.fill("");
    }

    protected void waitForVisible(Locator locator) {

        locator.waitFor(
                new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.VISIBLE)
        );
    }

    protected void waitForHidden(Locator locator) {

        locator.waitFor(
                new Locator.WaitForOptions()
                        .setState(WaitForSelectorState.HIDDEN)
        );
    }
}