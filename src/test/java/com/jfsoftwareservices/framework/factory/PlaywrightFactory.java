package com.jfsoftwareservices.framework.factory;

import com.jfsoftwareservices.framework.config.TestConfig;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public final class PlaywrightFactory {

    private static final ThreadLocal<Playwright> PLAYWRIGHT =
            new ThreadLocal<>();

    private static final ThreadLocal<Browser> BROWSER =
            new ThreadLocal<>();

    private static final ThreadLocal<BrowserContext> CONTEXT =
            new ThreadLocal<>();

    private static final ThreadLocal<Page> PAGE =
            new ThreadLocal<>();

    private PlaywrightFactory() {
    }

    public static void initialise() {
        System.out.println(
                "Browser: " + TestConfig.browser()
        );

        System.out.println(
                "Headless: " + TestConfig.headless()
        );

        System.out.println(
                "Base URL: " + TestConfig.baseUrl()
        );

        Playwright playwright = Playwright.create();
        playwright.selectors().setTestIdAttribute("data-test");
        PLAYWRIGHT.set(playwright);

        Browser browser = launchBrowser(playwright);
        BROWSER.set(browser);

        BrowserContext context = browser.newContext();
        CONTEXT.set(context);

        Page page = context.newPage();
        page.setDefaultTimeout(TestConfig.elementTimeout());
        page.setDefaultNavigationTimeout(TestConfig.navigationTimeout());
        PAGE.set(page);
    }

    private static Browser launchBrowser(Playwright playwright) {
        BrowserType browserType = switch (
                TestConfig.browser().toLowerCase()
                ) {
            case "chromium" -> playwright.chromium();
            case "firefox" -> playwright.firefox();
            case "webkit" -> playwright.webkit();
            default -> throw new IllegalArgumentException(
                    "Unsupported browser: " + TestConfig.browser()
            );
        };

        return browserType.launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(TestConfig.headless())
        );
    }

    public static Page page() {
        return PAGE.get();
    }

    public static BrowserContext context() {
        return CONTEXT.get();
    }

    public static void close() {
        closeSafely(PAGE.get());
        closeSafely(CONTEXT.get());
        closeSafely(BROWSER.get());
        closeSafely(PLAYWRIGHT.get());

        PAGE.remove();
        CONTEXT.remove();
        BROWSER.remove();
        PLAYWRIGHT.remove();
    }

    private static void closeSafely(AutoCloseable resource) {
        if (resource == null) {
            return;
        }

        try {
            resource.close();
        } catch (Exception exception) {
            throw new IllegalStateException(
                    "Failed to close Playwright resource",
                    exception
            );
        }
    }
}
