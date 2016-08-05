package com.jengine.feature.concurrent.lock;

import com.jengine.feature.concurrent.ConcurrentTest;
import org.junit.Test;

/**
 * @author nouuid
 * @date 4/6/2016
 * @description
 */
public class ReentranLockTest extends ConcurrentTest {

    @Test
    public void reentrantLockTest() throws InterruptedException {
        ReentrantLockRunner reentrantLockRunner = new ReentrantLockRunner();
        reentrantLockRunner.reentrantLockTest();
        Thread.sleep(30*1000);
    }

    @Test
    public void reentrantLockConditionAwaitTest() throws InterruptedException {
        ReentrantLockConditionRunner reentrantLockConditionRunner = new ReentrantLockConditionRunner();
        reentrantLockConditionRunner.reentrantLockConditionAwaitTest();
        Thread.sleep(30*1000);
    }

    @Test
    public void reentrantLockConditionAwaitSingalTest() throws InterruptedException {
        ReentrantLockConditionRunner reentrantLockConditionRunner = new ReentrantLockConditionRunner();
        reentrantLockConditionRunner.reentrantLockConditionAwaitSingalTest();
        Thread.sleep(30*1000);
    }

    @Test
    public void reentrantLockMultiConditionAwaitSingalTest() throws InterruptedException {
        ReentrantLockConditionRunner reentrantLockConditionRunner = new ReentrantLockConditionRunner();
        reentrantLockConditionRunner.reentrantLockMultiConditionAwaitSingalTest();
        Thread.sleep(30*1000);
    }

    @Test
    public void producerConsumerOneToOneTest() throws InterruptedException {
        ProducerConsumerRunner producerConsumerRunner = new ProducerConsumerRunner();
        producerConsumerRunner.oneToOneTest();
        Thread.sleep(30*1000);
    }

    @Test
    public void producerConsumerMultiToMultiTest() throws InterruptedException {
        ProducerConsumerRunner producerConsumerRunner = new ProducerConsumerRunner();
        producerConsumerRunner.multiToMultiTest();
        Thread.sleep(30*1000);
    }

    @Test
    public void producerConsumerMultiToMultiTest2() throws InterruptedException {
        ProducerConsumerRunner producerConsumerRunner = new ProducerConsumerRunner();
        producerConsumerRunner.multiToMultiTest2();
        Thread.sleep(30*1000);
    }

    @Test
    public void fairTest() throws InterruptedException {
        FairNofairRunner fairNofairRunner = new FairNofairRunner();
        fairNofairRunner.fairTest();
        Thread.sleep(30*1000);
    }

    @Test
    public void nofairTest() throws InterruptedException {
        FairNofairRunner fairNofairRunner = new FairNofairRunner();
        fairNofairRunner.nofairTest();
        Thread.sleep(30*1000);
    }

    @Test
    public void getHoldCountTest() throws InterruptedException {
        CountMethodOfLockRunner countMethodOfLockRunner = new CountMethodOfLockRunner();
        countMethodOfLockRunner.getHoldCountTest();
        Thread.sleep(10*1000);
    }

    @Test
    public void getQueueLengthTest() throws InterruptedException {
        CountMethodOfLockRunner countMethodOfLockRunner = new CountMethodOfLockRunner();
        countMethodOfLockRunner.getQueueLengthTest();
        Thread.sleep(10*1000);
    }

    @Test
    public void getWaitQueueLengthServiceTest() throws InterruptedException {
        CountMethodOfLockRunner countMethodOfLockRunner = new CountMethodOfLockRunner();
        countMethodOfLockRunner.getWaitQueueLengthServiceTest();
        Thread.sleep(10*1000);
    }

    @Test
    public void hasQueuedThreadTest() throws InterruptedException {
        WaitingStatusMethodOfLockRunner statusMethodOfLockRunner = new WaitingStatusMethodOfLockRunner();
        statusMethodOfLockRunner.hasQueuedThreadTest();
        Thread.sleep(30*1000);
    }

    @Test
    public void hasQueuedThreadsTest() throws InterruptedException {
        WaitingStatusMethodOfLockRunner waitingStatusMethodOfLockRunner = new WaitingStatusMethodOfLockRunner();
        waitingStatusMethodOfLockRunner.hasQueuedThreadsTest();
        Thread.sleep(30*1000);
    }

    @Test
    public void hasWaitersTest() throws InterruptedException {
        WaitingStatusMethodOfLockRunner waitingStatusMethodOfLockRunner = new WaitingStatusMethodOfLockRunner();
        waitingStatusMethodOfLockRunner.hasWaitersTest();
        Thread.sleep(30*1000);
    }

    @Test
    public void isFairTest() throws InterruptedException {
        HoldStatusMethodOfLockRunner holdStatusMethodOfLockRunner = new HoldStatusMethodOfLockRunner();
        holdStatusMethodOfLockRunner.isFairTest();
        Thread.sleep(10*1000);
    }

    @Test
    public void isHeldByCurrentThreadTest() throws InterruptedException {
        HoldStatusMethodOfLockRunner holdStatusMethodOfLockRunner = new HoldStatusMethodOfLockRunner();
        holdStatusMethodOfLockRunner.isHeldByCurrentThreadTest();
        Thread.sleep(10*1000);
    }

    @Test
    public void isLockedTest() throws InterruptedException {
        HoldStatusMethodOfLockRunner holdStatusMethodOfLockRunner = new HoldStatusMethodOfLockRunner();
        holdStatusMethodOfLockRunner.isLockedTest();
        Thread.sleep(30*1000);
    }

    @Test
    public void lockInterruptiblyTest1() throws InterruptedException {
        LockTypeOfLockRunner lockTypeOfLockRunner = new LockTypeOfLockRunner();
        lockTypeOfLockRunner.lockInterruptiblyTest1();
        Thread.sleep(30*1000);
    }

    @Test
    public void lockInterruptiblyTest2() throws InterruptedException {
        LockTypeOfLockRunner lockTypeOfLockRunner = new LockTypeOfLockRunner();
        lockTypeOfLockRunner.lockInterruptiblyTest2();
        Thread.sleep(30*1000);
    }

    @Test
    public void tryLockTest() throws InterruptedException {
        LockTypeOfLockRunner lockTypeOfLockRunner = new LockTypeOfLockRunner();
        lockTypeOfLockRunner.tryLockTest();
        Thread.sleep(30*1000);
    }

    @Test
    public void timedTryLockTest() throws InterruptedException {
        LockTypeOfLockRunner lockTypeOfLockRunner = new LockTypeOfLockRunner();
        lockTypeOfLockRunner.timedTryLockTest();
        Thread.sleep(30*1000);
    }

    @Test
    public void timedTryLockTest2() throws InterruptedException {
        LockTypeOfLockRunner lockTypeOfLockRunner = new LockTypeOfLockRunner();
        lockTypeOfLockRunner.timedTryLockTest2();
        Thread.sleep(30*1000);
    }

    @Test
    public void awaitUninterruptiblyTest() throws InterruptedException {
        AwaitExtendMethodRunner awaitExtendMethodRunner = new AwaitExtendMethodRunner();
        awaitExtendMethodRunner.awaitUninterruptiblyTest();
        Thread.sleep(10*1000);
    }

    @Test
    public void awaitUninterruptiblyTest2() throws InterruptedException {
        AwaitExtendMethodRunner awaitExtendMethodRunner = new AwaitExtendMethodRunner();
        awaitExtendMethodRunner.awaitUninterruptiblyTest2();
        Thread.sleep(10*1000);
    }

    @Test
    public void awaitUtilTest() throws InterruptedException {
        AwaitExtendMethodRunner awaitExtendMethodRunner = new AwaitExtendMethodRunner();
        awaitExtendMethodRunner.awaitUtilTest();
        Thread.sleep(30*1000);
    }

    @Test
    public void awaitUtilTest2() throws InterruptedException {
        AwaitExtendMethodRunner awaitExtendMethodRunner = new AwaitExtendMethodRunner();
        awaitExtendMethodRunner.awaitUtilTest2();
        Thread.sleep(30*1000);
    }


}
