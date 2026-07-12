package com.jfsoftwareservices.tests;

import com.jfsoftwareservices.framework.factory.PlaywrightFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseTest {

    @BeforeEach
    void setUp() {
        PlaywrightFactory.initialise();
    }

    @AfterEach
    void tearDown() {
        PlaywrightFactory.close();
    }
}