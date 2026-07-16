package com.jfsoftwareservices.framework.extensions;

import com.jfsoftwareservices.framework.factory.PlaywrightFactory;
import com.jfsoftwareservices.framework.utils.ScreenshotUtils;
import com.jfsoftwareservices.framework.utils.TraceUtils;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

public class TestFailureWatcher implements TestWatcher {

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        ScreenshotUtils.capture(PlaywrightFactory.page(), context.getDisplayName());
        TraceUtils.capture(PlaywrightFactory.context(), context.getDisplayName());
        PlaywrightFactory.close();
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        PlaywrightFactory.close();
    }
}