package com.jfsoftwareservices.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class FrameworkConfig {

    private static final Properties PROPERTIES = loadProperties();

    private FrameworkConfig() {
    }

    public static String baseUrl() {
        return getProperty("base.url");
    }

    public static String browser() {
        return System.getProperty(
                "browser",
                getProperty("browser")
        );
    }

    public static boolean headless() {
        return Boolean.parseBoolean(
                System.getProperty(
                        "headless",
                        getProperty("headless")
                )
        );
    }

    private static String getProperty(String key) {
        String value = PROPERTIES.getProperty(key);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Missing required configuration property: " + key
            );
        }

        return value.trim();
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();

        try (InputStream inputStream =
                     FrameworkConfig.class
                             .getClassLoader()
                             .getResourceAsStream("config.properties")) {

            if (inputStream == null) {
                throw new IllegalStateException(
                        "config.properties was not found on the classpath"
                );
            }

            properties.load(inputStream);
            return properties;

        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Unable to load config.properties",
                    exception
            );
        }
    }
}