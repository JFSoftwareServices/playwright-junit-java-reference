package com.jfsoftwareservices.tests;

import com.jfsoftwareservices.framework.config.FrameworkConfig;
import com.jfsoftwareservices.framework.factory.PlaywrightFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SmokeTest extends BaseTest {

    @Test
    void shouldOpenApplication() {
        PlaywrightFactory.page().navigate(FrameworkConfig.baseUrl());

        assertEquals(
                "Swag Labs",
                PlaywrightFactory.page().title()
        );
    }
}