package com.jfsoftwareservices.framework.auth;

import com.jfsoftwareservices.framework.config.TestConfig;
import com.jfsoftwareservices.framework.model.Credentials;
import com.jfsoftwareservices.testdata.TestUsers;
import com.microsoft.playwright.*;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Manages Playwright authentication state for tests.
 *
 * Creates a reusable storage state after login and provides
 * authenticated browser contexts for test execution.
 */
public final class AuthStateManager {

    private static final Path AUTH_STATE =
            Path.of(
                    "test-results",
                    "auth",
                    "storageState.json"
            );

    private AuthStateManager() {
    }

    private static void createAuthState(Browser browser) {
        try {
            Files.createDirectories(AUTH_STATE.getParent());
            BrowserContext context = browser.newContext();
            /*
             * Temporary page used only to perform the initial login
             * and generate storageState.json. It is not exposed to tests.
             */
            Page page = context.newPage();
            page.navigate(TestConfig.baseUrl());
            Credentials credentials = TestUsers.standardUser();
            page.getByTestId("username").fill(credentials.username());
            page.getByTestId("password").fill(credentials.password());
            page.getByTestId("login-button").click();
            page.waitForURL("**/inventory.html");
            context.storageState(new BrowserContext.StorageStateOptions().setPath(AUTH_STATE));
            context.close();
        } catch (Exception e) {
            throw new RuntimeException("Unable to create authentication state", e);
        }
    }

    public static BrowserContext authenticatedContext(Browser browser) {
        if (!authStateExists()) {
            createAuthState(browser);
        }
        return browser.newContext(
                new Browser.NewContextOptions()
                        .setStorageStatePath(AUTH_STATE)
                        .setRecordVideoDir(
                                Path.of(
                                        "test-results",
                                        "videos"
                                )
                        )
        );
    }

    public static boolean authStateExists() {
        return Files.exists(AUTH_STATE);
    }
}