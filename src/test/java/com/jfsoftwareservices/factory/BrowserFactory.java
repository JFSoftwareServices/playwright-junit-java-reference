package com.jfsoftwareservices.factory;

import com.jfsoftwareservices.framework.config.TestConfig;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

/**
 * Factory responsible for creating Playwright browser instances.
 *
 * <p>
 * The browser type is selected using framework configuration:
 * </p>
 *
 * <pre>
 * mvn test -Dbrowser=chromium
 * </pre>
 *
 * <p>
 * Browser launch options, such as headless execution, are also configured
 * through the framework configuration.
 * </p>
 *
 * <p>
 * Supported browsers:
 * </p>
 *
 * <ul>
 *     <li>chromium</li>
 *     <li>firefox</li>
 *     <li>webkit</li>
 * </ul>
 */
public final class BrowserFactory {

    private BrowserFactory() {
    }

    /**
     * Creates and launches a Playwright browser instance.
     *
     * @param playwright active Playwright instance
     * @return launched browser instance
     * @throws IllegalArgumentException if the configured browser is unsupported
     */
    public static Browser create(Playwright playwright) {

        BrowserType.LaunchOptions options =
                new BrowserType.LaunchOptions()
                        .setHeadless(TestConfig.headless());

        return switch (TestConfig.browser().toLowerCase()) {

            case "chromium" -> playwright.chromium()
                    .launch(options);

            case "firefox" -> playwright.firefox()
                    .launch(options);

            case "webkit" -> playwright.webkit()
                    .launch(options);

            default -> throw new IllegalArgumentException(
                    "Unsupported browser: " + TestConfig.browser()
            );
        };
    }
}