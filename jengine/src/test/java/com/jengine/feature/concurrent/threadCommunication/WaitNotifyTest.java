package com.jengine.feature.concurrent.threadCommunication;

import com.jengine.feature.concurrent.ConcurrentTest;
import com.jengine.feature.concurrent.threadCommunication.waitNotify.*;
import junit.framework.Assert;
import org.junit.Test;

/**
 * @author nouuid
 * @date 4/1/2016
 * @description
 */
public class WaitNotifyTest extends ConcurrentTest {
    WaitNotifyRunner waitNotifyRunner = new WaitNotifyRunner();

    @Test
    public void testWait() throws InterruptedException {
        waitNotifyRunner.testWait();
        Thread.sleep(30*60*1000);
    }

    @Test
    public void testWaitNotify() throws InterruptedException {
        waitNotifyRunner.testWaitNotify();
        Thread.sleep(10*1000);
    }

    @Test
    public void testWaitNotifyAll() throws Exception {
        waitNotifyRunner.testWaitNotifyAll();
        Thread.sleep(10*1000);
    }

    @Test
    public void testWaitNotifyAll2() throws Exception {
        waitNotifyRunner.testWaitNotifyAll2();
        Thread.sleep(30*60*1000);
    }

    @Test
    public void testWaitInterrupt() throws Exception {
        waitNotifyRunner.testWaitInterrupt();
        Thread.sleep(5*1000);
        Assert.assertEquals("java.lang.InterruptedException", WaitNotifyExceptionUtil.exception.getClass().getName());
        Thread.sleep(5*1000);
    }

    @Test
    public void testTimedWait() throws Exception {
        waitNotifyRunner.testTimedWait();
        Thread.sleep(10*1000);
    }

    @Test
    public void producerConsumerValueTest() throws Exception {
        ProducerConsumerRunner producerConsumerRunner = new ProducerConsumerRunner();
        producerConsumerRunner.valueTest();
        Thread.sleep(30*60*1000);
    }

    @Test
    public void protendedDeathTest() throws Exception {
        ProducerConsumerRunner producerConsumerRunner = new ProducerConsumerRunner();
        producerConsumerRunner.valueProtendedDeath();
        Thread.sleep(30*60*1000);
    }

    @Test
    public void producerConsumerStackTest() throws Exception {
        ProducerConsumerRunner producerConsumerRunner = new ProducerConsumerRunner();
        producerConsumerRunner.stackTest();
        Thread.sleep(30*60*1000);
    }

    @Test
    public void producerConsumerStackTest2() throws Exception {
        ProducerConsumerRunner producerConsumerRunner = new ProducerConsumerRunner();
        producerConsumerRunner.stackProtendedDeath();
        Thread.sleep(30*60*1000);
    }

    @Test
    public void PipeInputOutputRunnerByteStreamTest() throws Exception {
        PipeInputOutputRunner pipeInputOutputRunner = new PipeInputOutputRunner();
        pipeInputOutputRunner.pipeByteStreamTest();
        Thread.sleep(10*1000);
    }

    @Test
    public void PipeInputOutputRunnerCharacterStreamTest() throws Exception {
        PipeInputOutputRunner pipeInputOutputRunner = new PipeInputOutputRunner();
        pipeInputOutputRunner.pipeCharacterStreamTest();
        Thread.sleep(10*1000);
    }

    @Test
    public void crossPrintTest() throws Exception {
        CrossPrintRunner crossPrintRunner = new CrossPrintRunner();
        crossPrintRunner.test();
        Thread.sleep(10*1000);
    }
}
