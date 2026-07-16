package com.jfsoftwareservices.framework.extensions;

import com.jfsoftwareservices.framework.factory.PlaywrightFactory;
import com.jfsoftwareservices.framework.utils.ScreenshotUtils;
import com.jfsoftwareservices.framework.utils.TraceUtils;
import com.jfsoftwareservices.framework.utils.VideoUtils;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

import java.nio.file.Path;

public class TestFailureHandler implements TestExecutionExceptionHandler {

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        String testName = context.getDisplayName();
        System.out.println("FAILURE HANDLER FIRED: " + testName);
        ScreenshotUtils.capture(PlaywrightFactory.page(), testName);
        TraceUtils.capture(PlaywrightFactory.context(), testName);
        Path video = PlaywrightFactory.videoPath();
        VideoUtils.attach(video);
        throw throwable;// allow JUnit to report the test failure
    }
}