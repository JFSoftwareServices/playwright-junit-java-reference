package com.jfsoftwareservices.framework.manager;

import com.jfsoftwareservices.factory.BrowserFactory;
import com.jfsoftwareservices.framework.auth.AuthStateManager;
import com.jfsoftwareservices.framework.config.TestConfig;
import com.microsoft.playwright.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the lifecycle of Playwright test execution resources.
 *
 * <p>
 * This class provides thread-safe management of Playwright components using
 * {@link ThreadLocal}, allowing tests to execute in parallel without sharing
 * browser instances, contexts, or pages.
 * </p>
 *
 * <p>
 * The manager is responsible for:
 * </p>
 *
 * <ul>
 *     <li>Creating and closing Playwright instances</li>
 *     <li>Launching browser instances via {@link BrowserFactory}</li>
 *     <li>Creating browser contexts and pages</li>
 *     <li>Applying authentication state when required</li>
 *     <li>Configuring tracing, video recording, and timeouts</li>
 *     <li>Capturing browser console messages and network activity</li>
 *     <li>Cleaning up resources after test execution</li>
 * </ul>
 *
 * <p>
 * Browser configuration is controlled through {@code TestConfig} and can be
 * overridden using Maven system properties:
 * </p>
 *
 * <pre>
 * mvn test -Dbrowser=chromium -Dheadless=true
 * </pre>
 *
 * <p>
 * This is a utility class and cannot be instantiated.
 * </p>
 */
public final class PlaywrightManager {

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

    private PlaywrightManager() {
    }

    public static void  initialise(boolean useAuthenticationState) {
        System.out.println("Browser: " + TestConfig.browser());
        System.out.println("Headless: " + TestConfig.headless());
        System.out.println("Base URL: " + TestConfig.baseUrl());

        Playwright playwright = Playwright.create();
        playwright.selectors().setTestIdAttribute("data-test");
        PLAYWRIGHT.set(playwright);

        Browser browser = BrowserFactory.create(playwright);
        BROWSER.set(browser);

        BrowserContext context;
        if (useAuthenticationState){
            context = AuthStateManager.authenticatedContext(browser);
        }else {
            context =
                    browser.newContext(
                            new Browser.NewContextOptions()
                                    .setRecordVideoDir(
                                            java.nio.file.Path.of(
                                                    "test-results",
                                                    "videos"
                                            )
                                    )
                    );
        }

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