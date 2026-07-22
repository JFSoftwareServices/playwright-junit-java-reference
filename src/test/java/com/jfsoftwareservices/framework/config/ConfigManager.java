package com.jfsoftwareservices.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Central configuration manager for the automation framework.
 *
 * <p>
 * Loads environment-specific configuration from:
 * {@code config/application-{env}.properties}
 *
 * <p>
 * The environment is selected using the Maven system property:
 *
 * <pre>
 * mvn test -Denv=qa
 * </pre>
 *
 * <p>
 * Configuration values can be overridden at runtime using JVM system properties:
 *
 * <pre>
 * mvn test -Dbrowser=chrome -Dheadless=true
 * </pre>
 *
 * <p>
 * Configuration priority:
 * <ol>
 *     <li>JVM system properties</li>
 *     <li>Environment properties file</li>
 * </ol>
 *
 * <p>
 * This class is a utility class and cannot be instantiated.
 */
public final class ConfigManager {

    private static final Properties properties = new Properties();

    static {
        loadProperties();
    }

    private ConfigManager() {

    }

    private static void loadProperties() {
        String env =
                System.getProperty(
                        "env",
                        "local"
                );
        String file =
                "config/application-"
                        + env
                        + ".properties";
        try (InputStream input =
                     ConfigManager.class
                             .getClassLoader()
                             .getResourceAsStream(file)) {
            if (input == null) {
                throw new RuntimeException(
                        "Configuration file not found: "
                                + file
                );
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Unable to load configuration",
                    e
            );
        }
    }

    public static String get(String key) {
        return System.getProperty(
                key,
                properties.getProperty(key)
        );
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}