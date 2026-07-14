package com.jfsoftwareservices.framework.config;

public final class TestConfig {

    private TestConfig() {

    }

    public static String baseUrl() {
        return ConfigManager.get("base.url");
    }

    public static String browser() {
        return ConfigManager.get("browser");
    }

    public static boolean headless() {
        return ConfigManager.getBoolean("headless");
    }

    public static int navigationTimeout() {
        return ConfigManager.getInt(
                "timeout.navigation"
        );
    }

    public static int elementTimeout() {
        return ConfigManager.getInt(
                "timeout.element"
        );
    }
}