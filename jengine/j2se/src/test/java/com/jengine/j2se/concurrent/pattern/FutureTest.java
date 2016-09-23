package com.jengine.j2se.concurrent.pattern;

/**
 * @author nouuid
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
