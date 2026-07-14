package com.jfsoftwareservices.testdata;

import com.jfsoftwareservices.framework.model.Credentials;

/**
 * Factory class providing test credentials used throughout the test suite.
 *
 * Encapsulating credentials in one place avoids hard-coded values in tests
 * and makes future migration to JSON, YAML, databases or secret stores simple.
 */
public final class TestUsers {

    private static final String PASSWORD = "secret_sauce";

    private TestUsers() {
        // Utility class
    }

    public static Credentials standardUser() {
        return new Credentials(
                "standard_user",
                PASSWORD
        );
    }

    public static Credentials lockedOutUser() {
        return new Credentials(
                "locked_out_user",
                PASSWORD
        );
    }

    public static Credentials problemUser() {
        return new Credentials(
                "problem_user",
                PASSWORD
        );
    }

    public static Credentials performanceGlitchUser() {
        return new Credentials(
                "performance_glitch_user",
                PASSWORD
        );
    }

    public static Credentials errorUser() {
        return new Credentials(
                "error_user",
                PASSWORD
        );
    }

    public static Credentials visualUser() {
        return new Credentials(
                "visual_user",
                PASSWORD
        );
    }

    public static Credentials invalidCredentials() {
        return new Credentials(
                "invalid_user",
                "wrong_password"
        );
    }

    public static Credentials invalidPassword() {
        return new Credentials(
                "standard_user",
                "wrong_password"
        );
    }

    public static Credentials invalidUsername() {
        return new Credentials(
                "invalid_user",
                PASSWORD
        );
    }

    public static Credentials emptyCredentials() {
        return new Credentials(
                "",
                ""
        );
    }
}