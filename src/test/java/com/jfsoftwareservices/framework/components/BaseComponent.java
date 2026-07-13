package com.jfsoftwareservices.framework.components;

import com.jfsoftwareservices.framework.ui.BaseUiObject;
import com.microsoft.playwright.Page;

/**
 * Base class for reusable page components.
 *
 * Examples:
 * - Header
 * - Footer
 * - Navigation menu
 * - Shopping cart
 * - Modal dialogs
 */
public abstract class BaseComponent extends BaseUiObject {

    protected BaseComponent(Page page) {
        super(page);
    }
}