package com.jengine.feature.concurrent.pattern;

/**
 * @author bl07637
 * @date 6/28/2016
 * @description
 */
public class FutureTest {

    @org.junit.Test
    public void futureTest() {
        FutureRunner futureRunner = new FutureRunner();
        try {
            futureRunner.futureTest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
