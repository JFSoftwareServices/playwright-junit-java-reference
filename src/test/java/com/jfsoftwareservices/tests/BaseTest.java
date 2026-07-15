package com.jfsoftwareservices.tests;

import com.jfsoftwareservices.framework.extensions.TestFailureWatcher;
import com.jfsoftwareservices.framework.factory.PlaywrightFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;

public abstract class BaseTest {

    @RegisterExtension
    TestFailureWatcher watcher = new TestFailureWatcher();

    @BeforeEach
    void setUp() {
        PlaywrightFactory.initialise();
    }

    @AfterEach
    void tearDown() {
    }
}