package com.jfsoftwareservices.framework.extensions;

import com.jfsoftwareservices.framework.factory.PlaywrightFactory;
import com.jfsoftwareservices.framework.utils.*;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

import java.nio.file.Path;

/**
 * JUnit 5 extension that captures diagnostic artifacts when a Playwright test fails.
 *
 * <p>
 * When a test execution exception occurs, this extension collects debugging
 * information including:
 * </p>
 *
 * <ul>
 *     <li>Failure screenshot</li>
 *     <li>Playwright trace</li>
 *     <li>Browser console messages</li>
 *     <li>Network logs</li>
 *     <li>Test execution video</li>
 * </ul>
 *
 * <p>
 * Captured artifacts are attached to the test report to support failure
 * investigation, particularly when running tests in CI/CD environments.
 * </p>
 *
 * <p>
 * The original exception is re-thrown after capturing diagnostics so that
 * JUnit correctly marks the test as failed.
 * </p>
 */
public class TestFailureHandler implements TestExecutionExceptionHandler {

    /**
     * Handles failed test execution and captures debugging artifacts.
     *
     * @param context  current JUnit execution context
     * @param throwable exception thrown during test execution
     * @throws Throwable rethrows the original test failure
     */
    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        String testName = context.getDisplayName();
        System.out.println("FAILURE HANDLER FIRED: " + testName);
        ScreenshotUtils.capture(PlaywrightFactory.page(), testName);
        TraceUtils.capture(PlaywrightFactory.context(), testName);
        ConsoleUtils.attach(PlaywrightFactory.consoleMessages());
        NetworkLogUtils.attach(PlaywrightFactory.networkLogs());
        Path video = PlaywrightFactory.videoPath();
        VideoUtils.attach(video);
        throw throwable;// allow JUnit to report the test failure
    }
}