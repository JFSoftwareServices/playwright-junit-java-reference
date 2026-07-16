package com.jfsoftwareservices.framework.factory;

import com.jfsoftwareservices.framework.config.TestConfig;
import com.microsoft.playwright.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class PlaywrightFactory {

    private static final ThreadLocal<Playwright> PLAYWRIGHT =
            new ThreadLocal<>();

    private static final ThreadLocal<Browser> BROWSER =
            new ThreadLocal<>();

    private static final ThreadLocal<BrowserContext> CONTEXT =
            new ThreadLocal<>();

    private static final ThreadLocal<Page> PAGE =
            new ThreadLocal<>();

    private static final ThreadLocal<List<String>> CONSOLE_MESSAGES =
            ThreadLocal.withInitial(ArrayList::new);

    private static final ThreadLocal<List<String>> NETWORK_LOGS =
            ThreadLocal.withInitial(ArrayList::new);

    private PlaywrightFactory() {
    }

    public static void  initialise() {
        System.out.println("Browser: " + TestConfig.browser());
        System.out.println("Headless: " + TestConfig.headless());
        System.out.println("Base URL: " + TestConfig.baseUrl());

        Playwright playwright = Playwright.create();
        playwright.selectors().setTestIdAttribute("data-test");
        PLAYWRIGHT.set(playwright);

        Browser browser = launchBrowser(playwright);
        BROWSER.set(browser);

        BrowserContext context =
                browser.newContext(
                        new Browser.NewContextOptions()
                                .setRecordVideoDir(
                                        java.nio.file.Path.of(
                                                "test-results",
                                                "videos"
                                        )
                                )
                );

        context.tracing().start(
                new Tracing.StartOptions()
                        .setScreenshots(true)
                        .setSnapshots(true)
                        .setSources(true)
        );

        CONTEXT.set(context);

        Page page = context.newPage();
        page.onConsoleMessage(message ->
                CONSOLE_MESSAGES.get().add(
                        "[" + message.type() + "] " + message.text()
                )
        );

        page.onRequest(request ->
                NETWORK_LOGS.get().add(
                        String.format(
                                "REQUEST  %s %s",
                                request.method(),
                                request.url()
                        )
                )
        );

        page.onResponse(response ->
                NETWORK_LOGS.get().add(
                        String.format(
                                "RESPONSE %3d %s",
                                response.status(),
                                response.url()
                        )
                )
        );

        page.onRequestFailed(request ->
                NETWORK_LOGS.get().add(
                        String.format(
                                "FAILED   %s %s (%s)",
                                request.method(),
                                request.url(),
                                request.failure()
                        )
                )
        );
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

    public static Path videoPath() {
        if (PAGE.get() == null) {
            return null;
        }
        return PAGE.get()
                .video()
                .path();
    }

    public static List<String> consoleMessages() {
        return CONSOLE_MESSAGES.get();
    }

    public static List<String> networkLogs() {
        return NETWORK_LOGS.get();
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
        CONSOLE_MESSAGES.remove();
        NETWORK_LOGS.remove();
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