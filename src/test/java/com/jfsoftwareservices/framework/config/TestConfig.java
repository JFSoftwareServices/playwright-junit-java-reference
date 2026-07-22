package com.jfsoftwareservices.framework.config;

/**
 * Provides strongly-typed access to test automation configuration values.
 *
 * <p>
 * This class acts as a convenience wrapper around {@link ConfigManager},
 * exposing commonly used framework settings such as:
 * </p>
 *
 * <ul>
 *     <li>Application base URL</li>
 *     <li>Browser selection</li>
 *     <li>Headless execution mode</li>
 *     <li>Navigation timeout</li>
 *     <li>Element timeout</li>
 * </ul>
 *
 * <p>
 * Configuration values are loaded from environment-specific property files
 * and can be overridden using JVM system properties.
 * </p>
 *
 * <pre>
 * mvn test -Denv=qa -Dbrowser=chrome
 * </pre>
 *
 * <p>
 * This is a utility class and cannot be instantiated.
 * </p>
 */
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