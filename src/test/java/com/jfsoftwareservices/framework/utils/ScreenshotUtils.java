package com.jfsoftwareservices.framework.utils;

import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ScreenshotUtils {

    private ScreenshotUtils() {
    }

    public static void capture(Page page, String testName) {
        System.out.println("Screenshot.capture()");
        System.out.println("page = " + page);
        if (page == null) {
            System.out.println("Screenshot skipped - page is null");
            return;
        }
        try {
            byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
            Path folder = Path.of("test-results", "screenshots");
            Files.createDirectories(folder);
            Files.write(folder.resolve(testName + ".png"), screenshot);
            Allure.addAttachment("Screenshot on failure", "image/png", new ByteArrayInputStream(screenshot), ".png");
        } catch (Exception e) {
            throw new RuntimeException("Unable to capture screenshot", e);
        }
    }
}