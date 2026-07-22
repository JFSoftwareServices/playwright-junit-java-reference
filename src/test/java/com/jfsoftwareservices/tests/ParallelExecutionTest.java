package com.jfsoftwareservices.tests;

import com.jfsoftwareservices.framework.manager.PlaywrightManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ParallelExecutionTest extends BaseTest {


    @Test
    void shouldRunParallelTestOne() throws InterruptedException {

        PlaywrightManager.page()
                .navigate("https://www.saucedemo.com/");


        System.out.println(
                "TEST ONE THREAD: "
                        + Thread.currentThread().getName()
        );


        Thread.sleep(2000);


        assertNotNull(
                PlaywrightManager.page()
        );
    }



    @Test
    void shouldRunParallelTestTwo() throws InterruptedException {


        PlaywrightManager.page()
                .navigate("https://www.saucedemo.com/");


        System.out.println(
                "TEST TWO THREAD: "
                        + Thread.currentThread().getName()
        );


        Thread.sleep(2000);


        assertNotNull(
                PlaywrightManager.page()
        );
    }

}