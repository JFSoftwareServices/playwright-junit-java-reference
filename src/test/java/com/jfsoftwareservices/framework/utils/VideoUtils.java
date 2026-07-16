package com.jfsoftwareservices.framework.utils;

import com.microsoft.playwright.Page;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public final class VideoUtils {

    private VideoUtils() {
    }

    public static void capture(
            Page page,
            String testName) {

        if (page == null) {
            System.out.println(
                    "Video skipped - page is null"
            );
            return;
        }

        try {
            Path videos = Path.of("test-results", "videos", testName + ".webm");
            page.video().saveAs(videos);
            byte[] video = Files.readAllBytes(videos);
            Allure.addAttachment(
                    "Video on failure",
                    "video/webm",
                    new ByteArrayInputStream(video),
                    ".webm"
            );
        } catch (Exception e) {
            throw new RuntimeException("Unable to capture video", e);
        }
    }
}
