package com.jengine.feature.concurrent.lock;

import com.jengine.feature.concurrent.ConcurrentTest;

import org.junit.Test;

/**
 * @author nouuid
 * @date 4/8/2016
 * @description
 */
public class ReentrantReadWriteLockRunnerTest extends ConcurrentTest{

    @Test
    public void amongReadLocksTest() throws InterruptedException {
        ReentrantReadWriteLockRunner reentrantReadWriteLockRunner = new ReentrantReadWriteLockRunner();
        reentrantReadWriteLockRunner.amongReadLocksTest();
        Thread.sleep(10*1000);
    }

    @Test
    public void amongWriteLocksTest() throws InterruptedException {
        ReentrantReadWriteLockRunner reentrantReadWriteLockRunner = new ReentrantReadWriteLockRunner();
        reentrantReadWriteLockRunner.amongWriteLocksTest();
        Thread.sleep(10*1000);
    }

    @Test
    public void amongReadWriteLocksTest() throws InterruptedException {
        ReentrantReadWriteLockRunner reentrantReadWriteLockRunner = new ReentrantReadWriteLockRunner();
        reentrantReadWriteLockRunner.amongReadWriteLocksTest();
        Thread.sleep(30*1000);
    }

    @Test
    public void amongReadWriteLocksTest2() throws InterruptedException {
        ReentrantReadWriteLockRunner reentrantReadWriteLockRunner = new ReentrantReadWriteLockRunner();
        reentrantReadWriteLockRunner.amongReadWriteLocksTest2();
        Thread.sleep(30*1000);
    }
}
