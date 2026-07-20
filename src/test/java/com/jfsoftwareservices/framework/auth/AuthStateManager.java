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
 *
 * Thread safety: {@link #authenticatedContext(Browser)} is called
 * concurrently by parallel test threads. A double-checked lock around
 * {@link #createAuthState(Browser)} ensures that only one thread performs
 * the initial login and writes storageState.json, even when several
 * threads reach the "does it exist?" check at the same time on a cold
 * start (i.e. before the file has ever been created). Once the file
 * exists, subsequent reads require no locking.
 */
public final class AuthStateManager {

    private static final Path AUTH_STATE =
            Path.of(
                    "test-results",
                    "auth",
                    "storageState.json"
            );

    /**
     * Guards creation of {@link #AUTH_STATE}. Only ever held briefly,
     * around the check-then-create sequence below - not around any
     * Playwright browser/context usage beyond that.
     */
    private static final Object AUTH_STATE_LOCK = new Object();

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
            synchronized (AUTH_STATE_LOCK) {
                // Re-check inside the lock: another thread may have
                // created the file while this thread was waiting to
                // acquire it. Without this second check, every thread
                // that queued up would redundantly log in again.
                if (!authStateExists()) {
                    createAuthState(browser);
                }
            }
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
