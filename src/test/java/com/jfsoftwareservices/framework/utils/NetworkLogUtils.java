package com.jfsoftwareservices.framework.utils;

import io.qameta.allure.Allure;

import java.util.List;

public final class NetworkLogUtils {

    private NetworkLogUtils() {
    }

    public static void attach(List<String> networkLogs) {

        if (networkLogs == null || networkLogs.isEmpty()) {
            System.out.println(
                    "Network logs skipped - no requests captured"
            );
            return;
        }

        Allure.addAttachment(
                "Network Requests",
                String.join(
                        System.lineSeparator(),
                        networkLogs
                )
        );
    }
}