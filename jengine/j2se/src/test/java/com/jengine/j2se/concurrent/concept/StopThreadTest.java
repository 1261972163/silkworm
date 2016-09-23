package com.jengine.j2se.concurrent.concept;

import com.jengine.j2se.concurrent.ConcurrentTest;
import org.junit.Test;

/**
 * @author nouuid
 * @date 4/11/2016
 * @description
 */
public class StopThreadTest extends ConcurrentTest {

    @Test
    public void loopTest() throws InterruptedException {
        StopThreadRunner stopThreadRunner = new StopThreadRunner();
        stopThreadRunner.loopTest();
        Thread.sleep(10*1000);
    }

    @Test
    public void interruptTest() throws InterruptedException {
        StopThreadRunner stopThreadRunner = new StopThreadRunner();
        stopThreadRunner.interruptTest();
        Thread.sleep(10*1000);
    }

    @Test
    public void sleepInterruptTest() throws InterruptedException {
        StopThreadRunner stopThreadRunner = new StopThreadRunner();
        stopThreadRunner.sleepInterruptTest();
        Thread.sleep(60*1000);
    }

}
