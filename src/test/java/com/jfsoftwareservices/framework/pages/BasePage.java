package com.jfsoftwareservices.framework.pages;

import com.jfsoftwareservices.framework.factory.PlaywrightFactory;
import com.jfsoftwareservices.framework.ui.BaseUiObject;

/**
 * Base class for all application pages.
 */
public abstract class BasePage extends BaseUiObject {

    protected BasePage() {
        super(PlaywrightFactory.page());
    }

    protected void navigate(String url) {
        page.navigate(url);
    }

    public String getTitle() {
        return page.title();
    }

    public String getCurrentUrl() {
        return page.url();
    }

    public void refresh() {
        page.reload();
    }

    public void goBack() {
        page.goBack();
    }

    public void goForward() {
        page.goForward();
    }
}