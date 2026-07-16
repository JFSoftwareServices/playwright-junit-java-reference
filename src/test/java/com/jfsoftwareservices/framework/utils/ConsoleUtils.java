package com.jfsoftwareservices.framework.utils;

import io.qameta.allure.Allure;

import java.util.List;

public final class ConsoleUtils {

    private ConsoleUtils() {
    }

    public static void attach(List<String> messages) {
        if (messages == null || messages.isEmpty()) {
            System.out.println("Console logs skipped - no messages");
            return;
        }

        Allure.addAttachment(
                "Browser Console Logs",
                String.join(
                        System.lineSeparator(),
                        messages
                )
        );
    }
}