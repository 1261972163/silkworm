package com.jengine.java.util;

import com.jengine.java.util.concurrent.synchronoustools.BarrierRunner;
import com.jengine.java.util.concurrent.synchronoustools.LatchRunner;
import com.jengine.java.util.concurrent.synchronoustools.SemaphoreRunner;

/**
 * @author nouuid
 * @date 4/29/2016
 * @description
 */
public class SynchronoustoolsTest {

    @org.junit.Test
    public void barrierTest() throws InterruptedException {
        BarrierRunner barrierRunner = new BarrierRunner();
        barrierRunner.test(10);

        Thread.sleep(30*1000);
    }

    @org.junit.Test
    public void latchTest() throws InterruptedException {
        LatchRunner barrierRunner = new LatchRunner();
        barrierRunner.test(10);

        Thread.sleep(30*1000);
    }

    @org.junit.Test
    public void semaphoreTest() throws InterruptedException {
        SemaphoreRunner semaphoreRunner = new SemaphoreRunner();
        semaphoreRunner.test();

        Thread.sleep(30*1000);
    }


}
