package com.jfsoftwareservices.tests;

import com.jfsoftwareservices.framework.factory.PlaywrightFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ParallelExecutionTest extends BaseTest {


    @Test
    void shouldRunParallelTestOne() throws InterruptedException {

        PlaywrightFactory.page()
                .navigate("https://www.saucedemo.com/");


        System.out.println(
                "TEST ONE THREAD: "
                        + Thread.currentThread().getName()
        );


        Thread.sleep(2000);


        assertNotNull(
                PlaywrightFactory.page()
        );
    }



    @Test
    void shouldRunParallelTestTwo() throws InterruptedException {


        PlaywrightFactory.page()
                .navigate("https://www.saucedemo.com/");


        System.out.println(
                "TEST TWO THREAD: "
                        + Thread.currentThread().getName()
        );


        Thread.sleep(2000);


        assertNotNull(
                PlaywrightFactory.page()
        );
    }

}