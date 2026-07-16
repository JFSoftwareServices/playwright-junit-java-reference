package com.jfsoftwareservices.framework.utils;

import com.microsoft.playwright.BrowserContext;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public final class TraceUtils {

    private TraceUtils() {
    }

    public static void capture(BrowserContext context, String testName) {
        System.out.println("Trace.capture()");
        System.out.println("context = " + context);
        if (context == null) {
            System.out.println("Trace skipped - context is null");
            return;
        }
        try {
            Path folder = Path.of("test-results", "traces");
            Files.createDirectories(folder);
            Path tracePath = folder.resolve(testName + ".zip");
            context.tracing().stop(
                    new com.microsoft.playwright.Tracing.StopOptions()
                            .setPath(tracePath)
            );
            byte[] trace = Files.readAllBytes(tracePath);
            Allure.addAttachment(
                    "Playwright Trace",
                    "application/zip",
                    new ByteArrayInputStream(trace),
                    ".zip"
            );
        } catch (Exception e) {
            throw new RuntimeException("Unable to capture trace", e);
        }
    }
}