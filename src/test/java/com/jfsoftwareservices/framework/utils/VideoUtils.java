package com.jfsoftwareservices.framework.utils;

import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public final class VideoUtils {

    private VideoUtils() {
    }

    public static void attach(Path videoPath) {
        System.out.println("Video.attach()");
        System.out.println(videoPath);
        if (videoPath == null) {
            System.out.println("Video skipped - no video path");
            return;
        }
        try {
            byte[] video = Files.readAllBytes(videoPath);
            Allure.addAttachment(
                    "Video on failure",
                    "video/webm",
                    new ByteArrayInputStream(video),
                    ".webm"
            );
        } catch (Exception e) {
            throw new RuntimeException("Unable to attach video", e);
        }
    }
}