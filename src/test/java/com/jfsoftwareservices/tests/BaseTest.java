package com.jfsoftwareservices.tests;

import com.jfsoftwareservices.framework.extensions.TestFailureHandler;
import com.jfsoftwareservices.framework.manager.PlaywrightManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;


public abstract class BaseTest {

    @RegisterExtension
    TestFailureHandler failureHandler = new TestFailureHandler();

    protected boolean authenticated() {
        return false;
    }

    @BeforeEach
    void setUp() {
        PlaywrightManager.initialise(authenticated());
    }

    @AfterEach
    void tearDown() {
        PlaywrightManager.close();
    }
}