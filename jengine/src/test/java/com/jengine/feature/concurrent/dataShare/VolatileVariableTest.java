package com.jengine.feature.concurrent.dataShare;

import com.jengine.feature.concurrent.ConcurrentTest;
import com.jengine.feature.concurrent.dataShare.volatileVariable.*;
import org.junit.Test;
/**
 * @author nouuid
 * @date 4/1/2016
 * @description
 */
public class VolatileVariableTest extends ConcurrentTest {

    @Test
    public void deadLoop() throws InterruptedException {
        DeadLoopRunner deadLoopRunner = new DeadLoopRunner();
        deadLoopRunner.test();
        Thread.sleep(10*1000);
    }

    @Test
    public void deadLoopRunnable() throws InterruptedException {
        DeadLoopRunnableRunner deadLoopRunnableRunner = new DeadLoopRunnableRunner();
        deadLoopRunnableRunner.test();
        Thread.sleep(10*1000);
    }

    @Test
    public void deadLoopRunnable2() throws InterruptedException {
        DeadLoopRunnable2Runner deadLoopRunnable2Runner = new DeadLoopRunnable2Runner();
        deadLoopRunnable2Runner.test();
        Thread.sleep(10*1000);
    }

    @Test
    public void volatileAtomicity() throws InterruptedException {
        VolatileAtomicityRunner volatileAtomicityRunner = new VolatileAtomicityRunner();
        volatileAtomicityRunner.test();
        Thread.sleep(10*1000);
    }

    @Test
    public void volatileAtomicityRunner2() throws InterruptedException {
        VolatileAtomicityRunner volatileAtomicityRunner = new VolatileAtomicityRunner();
        volatileAtomicityRunner.test2();
        Thread.sleep(10*1000);
    }

    @Test
    public void VolatileAtomicity2Runner() throws InterruptedException {
        VolatileAtomicity2Runner volatileAtomicity2Runner = new VolatileAtomicity2Runner();
        volatileAtomicity2Runner.test();
        Thread.sleep(10*1000);
    }

    @Test
    public void VolatileAtomicity2Runner2() throws InterruptedException {
        VolatileAtomicity2Runner volatileAtomicity2Runner = new VolatileAtomicity2Runner();
        volatileAtomicity2Runner.test2();
        Thread.sleep(10*1000);
    }

    @Test
    public void atomicInteger() throws InterruptedException {
        AtomicIntegerRunner atomicIntegerRunner = new AtomicIntegerRunner();
        atomicIntegerRunner.test();
        Thread.sleep(10*1000);
    }
}
