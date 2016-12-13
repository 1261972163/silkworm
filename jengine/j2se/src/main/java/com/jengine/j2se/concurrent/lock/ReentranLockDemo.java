package com.jengine.j2se.concurrent.lock;

import com.jengine.j2se.concurrent.ConcurrentTest;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author nouuid
 * @date 4/6/2016
 * @description
 */
public class ReentranLockDemo extends ConcurrentTest {

    //--------------------------------------------------------------------------------------------------------
    // ReentrantLockRunner -----------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------

    @Test
    public void reentrantLockTest() throws InterruptedException {
        ReentrantLockRunner reentrantLockRunner = new ReentrantLockRunner();
        reentrantLockRunner.reentrantLockTest();
        Thread.sleep(30*1000);
    }

    //--------------------------------------------------------------------------------------------------------
    // ReentrantLockConditionRunner --------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------

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

    //--------------------------------------------------------------------------------------------------------
    // ProducerConsumerRunner --------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------

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

    //--------------------------------------------------------------------------------------------------------
    // FairNofairRunner --------------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------

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

    //--------------------------------------------------------------------------------------------------------
    // CountMethodOfLockRunner -------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------

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

    //--------------------------------------------------------------------------------------------------------
    // WaitingStatusMethodOfLockRunner -----------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------

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

    //--------------------------------------------------------------------------------------------------------
    // HoldStatusMethodOfLockRunner --------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------

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

    //--------------------------------------------------------------------------------------------------------
    // LockTypeOfLockRunner ----------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------

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

    //--------------------------------------------------------------------------------------------------------
    // AwaitExtendMethodRunner -------------------------------------------------------------------------------
    //--------------------------------------------------------------------------------------------------------

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

//========================================================================================================================

class ReentrantLockRunner {

    public void reentrantLockTest() {
        ReentrantLockTask reentrantLockTask = new ReentrantLockTask();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                reentrantLockTask.service();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                reentrantLockTask.service();
            }
        });

        t1.setName("A");
        t2.setName("B");

        t1.start();
        t2.start();

    }
}

class ReentrantLockTask {
    private Lock locker = new ReentrantLock();

    public void service() {
        try {
            locker.lock();
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " do service " + i);
                Thread.sleep(1000);
            }
            locker.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

//========================================================================================================================

class ReentrantLockConditionRunner {

    public void reentrantLockConditionAwaitTest() {
        ReentrantLockConditionTask reentrantLockConditionTask = new ReentrantLockConditionTask();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                reentrantLockConditionTask.service();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                reentrantLockConditionTask.service2();
            }
        });

        t1.setName("A");
        t2.setName("B");

        t1.start();
        t2.start();
    }

    public void reentrantLockConditionAwaitSingalTest() {
        ReentrantLockConditionTask reentrantLockConditionTask = new ReentrantLockConditionTask();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                reentrantLockConditionTask.service();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                reentrantLockConditionTask.service3();
            }
        });

        t1.setName("A");
        t2.setName("B");

        t1.start();
        t2.start();
    }

    public void reentrantLockMultiConditionAwaitSingalTest() throws InterruptedException {
        Lock locker = new ReentrantLock();
        Condition conditionA = locker.newCondition();
        Condition conditionB = locker.newCondition();
        Condition conditionC = locker.newCondition();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    locker.lock();
                    System.out.println("A start");
                    conditionA.await();
                    System.out.println("A end");
                    locker.unlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    locker.lock();
                    System.out.println("B start");
                    conditionB.await();
                    System.out.println("B end");
                    locker.unlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    locker.lock();
                    System.out.println("C start");
                    conditionA.signalAll();
                    System.out.println("C end");
                    locker.unlock();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        Thread.sleep(1000);
        t2.start();
        Thread.sleep(1000);
        t3.start();

    }
}

class ReentrantLockConditionTask {
    private Lock locker = new ReentrantLock();
    private Condition condition = locker.newCondition();

    public void service() {
        try {
            locker.lock();
            for (int i = 0; i < 10; i++) {
                if (i==5) {
                    condition.await();
                }
                System.out.println(Thread.currentThread().getName() + " do service " + i);
                Thread.sleep(1000);
            }
            locker.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void service2() {
        try {
            locker.lock();
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " do service " + i);
                Thread.sleep(1000);
            }
            locker.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void service3() {
        try {
            locker.lock();
            for (int i = 0; i < 10; i++) {
                if (i==5) {
                    condition.signal();
                }
                System.out.println(Thread.currentThread().getName() + " do service " + i);
                Thread.sleep(1000);
            }
            locker.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

//======================================================================================================================

class ProducerConsumerRunner {

    public void oneToOneTest() throws InterruptedException {
        ProducerConsumerRunnerProducerConsumer producerConsumerRunnerProducerConsumer = new ProducerConsumerRunnerProducerConsumer();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i<10; i++) {
                    producerConsumerRunnerProducerConsumer.produce();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i<10; i++) {
                    producerConsumerRunnerProducerConsumer.consume();
                }
            }
        });

        t2.start();
        Thread.sleep(1000);
        t1.start();
    }

    public void multiToMultiTest() throws InterruptedException {
        ProducerConsumerRunnerProducerConsumer2 producerConsumerRunnerProducerConsumer2 = new ProducerConsumerRunnerProducerConsumer2();

        Thread[] producerThreads = new Thread[10];
        Thread[] consumerThreads = new Thread[10];

        for (int i=0; i<10; i++) {
            producerThreads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    producerConsumerRunnerProducerConsumer2.produce();
                }
            });

            consumerThreads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    producerConsumerRunnerProducerConsumer2.consume();
                }
            });

            producerThreads[i].start();
            consumerThreads[i].start();
        }
    }

    public void multiToMultiTest2() throws InterruptedException {
        ProducerConsumerRunnerProducerConsumer3 producerConsumerRunnerProducerConsumer3 = new ProducerConsumerRunnerProducerConsumer3();

        Thread[] producerThreads = new Thread[10];
        Thread[] consumerThreads = new Thread[100];

        for (int i=0; i<10; i++) {
            producerThreads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    producerConsumerRunnerProducerConsumer3.produce();
                }
            });

            producerThreads[i].start();

        }

        for (int i=0; i<100; i++) {
            consumerThreads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    producerConsumerRunnerProducerConsumer3.consume();
                }
            });

            consumerThreads[i].start();
        }

    }
}

class ProducerConsumerRunnerProducerConsumer {

    private Lock locker = new ReentrantLock();
    private Condition condition = locker.newCondition();
    private boolean prevIsProducer = false;

    public void produce() {
        try {
            locker.lock();
            if (prevIsProducer) {
                condition.await();
            }
            System.out.println("produce");
            Thread.sleep(1000);
            prevIsProducer = true;
            condition.signalAll();
            locker.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void consume() {
        try {
            locker.lock();
            if (!prevIsProducer) {
                condition.await();
            }
            System.out.println("          consume");
            Thread.sleep(1000);
            prevIsProducer = false;
            condition.signalAll();
            locker.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ProducerConsumerRunnerProducerConsumer2 {

    private Lock locker = new ReentrantLock();
    private Condition conditionP = locker.newCondition();
    private Condition conditionC = locker.newCondition();
    private boolean prevIsProducer = false;

    public void produce() {
        try {
            locker.lock();
            if (prevIsProducer) {
                conditionP.await();
            }
            System.out.println("produce");
            Thread.sleep(1000);
            prevIsProducer = true;
            conditionC.signal();
            locker.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void consume() {
        try {
            locker.lock();
            if (!prevIsProducer) {
                conditionC.await();
            }
            System.out.println("          consume");
            Thread.sleep(1000);
            prevIsProducer = false;
            conditionP.signal();
            locker.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ProducerConsumerRunnerProducerConsumer3 {

    private Lock locker = new ReentrantLock();
    private Condition condition = locker.newCondition();
    private boolean prevIsProducer = false;

    public void produce() {
        try {
            locker.lock();
            if (prevIsProducer) {
                condition.await();
            }
            System.out.println("produce");
            Thread.sleep(1000);
            prevIsProducer = true;
            condition.signal();
            locker.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void consume() {
        try {
            locker.lock();
            if (!prevIsProducer) {
                condition.await();
            }
            System.out.println("          consume");
            Thread.sleep(1000);
            prevIsProducer = false;
            condition.signal();
            locker.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

//======================================================================================================================

/**
 * 公平锁和非公平锁
 */
class FairNofairRunner {

    public void fairTest() {
        FairNofairTask fairNofairTask = new FairNofairTask(true);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " start");
                fairNofairTask.service();
            }
        };

        Thread[] threads = new Thread[10];
        for (int i=0; i<10; i++) {
            threads[i] = new Thread(runnable);
            threads[i].setName("T" + i);
        }
        for (int i=0; i<10; i++) {
            threads[i].start();
        }
    }

    public void nofairTest() {
        FairNofairTask fairNofairTask = new FairNofairTask(false);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " start");
                fairNofairTask.service();
            }
        };

        Thread[] threads = new Thread[10];
        for (int i=0; i<10; i++) {
            threads[i] = new Thread(runnable);
            threads[i].setName("T" + i);
        }
        for (int i=0; i<10; i++) {
            threads[i].start();
        }
    }
}

class FairNofairTask {
    private Lock locker;

    public FairNofairTask(boolean isFair) {
        locker = new ReentrantLock(isFair);
    }

    public void service() {
        locker.lock();
        System.out.println("                     " + Thread.currentThread().getName() + " do service");
        locker.unlock();
    }
}

//======================================================================================================================

/**
 * @author nouuid
 * @date 4/6/2016
 * @description
 *
 * lock.getHoldCount(): count number of threads which hold this lock
 * lock.getQueueLength(): count number of threads which is waiting for holding this lock
 * lock.getWaitQueueLength(condition): count number of threads which is waiting for condition related to this lock
 */
class CountMethodOfLockRunner {

    public void getHoldCountTest() {
        GetHoldCountService getHoldCountService = new GetHoldCountService();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getHoldCountService.service1();
            }
        });
        thread.start();
    }

    public void getQueueLengthTest() {
        GetQueueLengthService getQueueLengthService = new GetQueueLengthService();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getQueueLengthService.service1();
            }
        };

        Thread[] threads = new Thread[10];
        for (int i=0; i<10; i++) {
            threads[i] = new Thread(runnable);
            threads[i].setName("T" + i);
        }

        for (int i=0; i<10; i++) {
            threads[i].start();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("getQueueLength=" + getQueueLengthService.getLocker().getQueueLength());
    }

    public void getWaitQueueLengthServiceTest() {
        GetWaitQueueLengthService getWaitQueueLengthService = new GetWaitQueueLengthService();
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                getWaitQueueLengthService.service1();
            }
        };

        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                getWaitQueueLengthService.service2();
            }
        };

        Thread[] threads1 = new Thread[10];
        for (int i=0; i<10; i++) {
            threads1[i] = new Thread(runnable1);
            threads1[i].setName("AT" + i);
        }

        Thread[] threads2 = new Thread[10];
        for (int i=0; i<10; i++) {
            threads2[i] = new Thread(runnable2);
            threads2[i].setName("BT" + i);
        }

        for (int i=0; i<10; i++) {
            threads1[i].start();
            threads2[i].start();
        }
    }
}

class GetHoldCountService {

    private ReentrantLock locker = new ReentrantLock();

    public void service1() {
        locker.lock();
        System.out.println("service1 getHoldCount=" + locker.getHoldCount());
        service2();
        locker.unlock();
    }

    public void service2() {
        locker.lock();
        System.out.println("service2 getHoldCount=" + locker.getHoldCount());
        locker.unlock();
    }
}

class GetQueueLengthService {
    private ReentrantLock locker = new ReentrantLock();

    public ReentrantLock getLocker() {
        return locker;
    }

    public void setLocker(ReentrantLock locker) {
        this.locker = locker;
    }

    public void service1() {
        try {
            locker.lock();
            System.out.println(Thread.currentThread().getName() + " do service1");
            Thread.sleep(5*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            locker.unlock();
        }
    }
}

class GetWaitQueueLengthService {
    private ReentrantLock locker = new ReentrantLock();
    private Condition conditionA = locker.newCondition();
    private Condition conditionB = locker.newCondition();

    public Condition getConditionA() {
        return conditionA;
    }

    public void setConditionA(Condition conditionA) {
        this.conditionA = conditionA;
    }

    public ReentrantLock getLocker() {
        return locker;
    }

    public void setLocker(ReentrantLock locker) {
        this.locker = locker;
    }

    public void service1() {
        try {
            locker.lock();
            System.out.println("A getWaitQueueLength=" + locker.getWaitQueueLength(conditionA));
            conditionA.await();
            System.out.println(Thread.currentThread().getName() + " do service1");
            Thread.sleep(2*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            locker.unlock();
        }
    }

    public void service2() {
        try {
            locker.lock();
            System.out.println("                               B getWaitQueueLength=" + locker.getWaitQueueLength(conditionB));
            conditionA.signal();
            conditionB.await();
            System.out.println("                               "  + Thread.currentThread().getName() + " do service2");
            Thread.sleep(2*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            locker.unlock();
        }
    }
}

//======================================================================================================================

/**
 * @author nouuid
 * @date 4/6/2016
 * @description
 *
 * lock.hasQueuedThread(thread): is the specified thread waiting for holding this lock
 * lock.hasQueuedThreads(): is there any thread waiting for holding this lock
 * lock.hasWaiters(): is there any thread waiting for the condition related to this lock
 */
class WaitingStatusMethodOfLockRunner {

    public void hasQueuedThreadTest() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();

        WaitingStatusMethodOfLockService waitingStatusMethodOfLockService = new WaitingStatusMethodOfLockService(reentrantLock);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                waitingStatusMethodOfLockService.service1();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                waitingStatusMethodOfLockService.service2();
            }
        });

        t1.setName("t1");
        t2.setName("t2");

        t1.start();
        System.out.println("t2, before start, hasQueuedThread=" + reentrantLock.hasQueuedThread(t2));
        Assert.assertEquals(false, reentrantLock.hasQueuedThread(t2));
        Thread.sleep(1000);
        t2.start();
        System.out.println("t2, started, before lock, hasQueuedThread=" + reentrantLock.hasQueuedThread(t2));
        Assert.assertEquals(false, reentrantLock.hasQueuedThread(t2));
        Thread.sleep(3*1000);
        System.out.println("t2, started, lock, hasQueuedThread=" + reentrantLock.hasQueuedThread(t2));
        Assert.assertEquals(true, reentrantLock.hasQueuedThread(t2));
        Thread.sleep(10*1000);
        System.out.println("t2, ended, unlock, hasQueuedThread=" + reentrantLock.hasQueuedThread(t2));
        Assert.assertEquals(false, reentrantLock.hasQueuedThread(t2));
    }

    public void hasQueuedThreadsTest() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();

        WaitingStatusMethodOfLockService waitingStatusMethodOfLockService = new WaitingStatusMethodOfLockService(reentrantLock);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                waitingStatusMethodOfLockService.service1();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                waitingStatusMethodOfLockService.service2();
            }
        });

        t1.setName("t1");
        t2.setName("t2");

        t1.start();
        System.out.println("t2, before start, hasQueuedThreads=" + reentrantLock.hasQueuedThreads());
        Assert.assertEquals(false, reentrantLock.hasQueuedThreads());
        Thread.sleep(1000);
        t2.start();
        System.out.println("t2, started, before lock, hasQueuedThreads=" + reentrantLock.hasQueuedThreads());
        Assert.assertEquals(false, reentrantLock.hasQueuedThreads());
        Thread.sleep(3*1000);
        System.out.println("t2, started, lock, hasQueuedThreads=" + reentrantLock.hasQueuedThreads());
        Assert.assertEquals(true, reentrantLock.hasQueuedThreads());
        Thread.sleep(10*1000);
        System.out.println("t2, ended, unlock, hasQueuedThreads=" + reentrantLock.hasQueuedThreads());
        Assert.assertEquals(false, reentrantLock.hasQueuedThreads());
    }

    public void hasWaitersTest() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();

        WaitingStatusMethodOfLockService statusMethodOfLockService = new WaitingStatusMethodOfLockService(reentrantLock);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                statusMethodOfLockService.service3();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                statusMethodOfLockService.service4();
            }
        });

        t1.setName("t1");
        t2.setName("t2");

        t1.start();
        Thread.sleep(1000);
        t2.start();
    }
}

class WaitingStatusMethodOfLockService {

    private ReentrantLock reentrantLock;

    private Condition condition;

    public WaitingStatusMethodOfLockService(ReentrantLock reentrantLock) {
        this.reentrantLock = reentrantLock;
        condition = reentrantLock.newCondition();
    }

    public void service1() {
        try {
            reentrantLock.lock();
            System.out.print("                                                    ");
            System.out.println(Thread.currentThread().getName() + " do service1");
            Thread.sleep(10*1000);
            reentrantLock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void service2() {
        try {
            Thread.sleep(2*1000);
            reentrantLock.lock();
            System.out.print("                                                    ");
            System.out.println(Thread.currentThread().getName() + " do service1");
            reentrantLock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void service3() {
        try {
            reentrantLock.lock();
            System.out.println(Thread.currentThread().getName() + " is waiting");
            condition.await();
            System.out.print("                                                    ");
            System.out.println(Thread.currentThread().getName() + " do service3");
            System.out.println("hasWaiters=" + reentrantLock.hasWaiters(condition));
            reentrantLock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void service4() {
        try {
            reentrantLock.lock();
            System.out.print("                                                    ");
            System.out.println(Thread.currentThread().getName() + " do service4");
            System.out.println("hasWaiters=" + reentrantLock.hasWaiters(condition));
            Thread.sleep(10*1000);
            condition.signal();
            reentrantLock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

//======================================================================================================================

/**
 * @author nouuid
 * @date 4/7/2016
 * @description
 * lock.isFair(): is this lock a fair lock
 * lock.isHeldByCurrentThread(): is this lock locked by current thread
 * lock.isLocked(): is this lock locked by any threads
 */
class HoldStatusMethodOfLockRunner {

    public void isFairTest() {
        HoldStatusMethodOfLockService holdStatusMethodOfLockService = new HoldStatusMethodOfLockService();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                holdStatusMethodOfLockService.service1();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                holdStatusMethodOfLockService.service2();
            }
        });

        t1.setName("t1");
        t2.setName("t2");

        t1.start();
        t2.start();
    }

    public void isHeldByCurrentThreadTest() {
        HoldStatusMethodOfLockService holdStatusMethodOfLockService = new HoldStatusMethodOfLockService();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                holdStatusMethodOfLockService.service3();
            }
        });

        t1.setName("t1");
        t1.start();
    }

    public void isLockedTest() throws InterruptedException {
        HoldStatusMethodOfLockService holdStatusMethodOfLockService = new HoldStatusMethodOfLockService();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                holdStatusMethodOfLockService.service4();
            }
        });

        t1.setName("t1");
        System.out.println("t1, before start, lock isLocked=" + holdStatusMethodOfLockService.getFairReentrantLock().isLocked());
        t1.start();
        Thread.sleep(1000);
        System.out.println("t1, start, lock, lock isLocked=" + holdStatusMethodOfLockService.getFairReentrantLock().isLocked());
        Thread.sleep(10000);
        System.out.println("t1, unlock, lock isLocked=" + holdStatusMethodOfLockService.getFairReentrantLock().isLocked());
    }
}

class HoldStatusMethodOfLockService {
    private ReentrantLock fairReentrantLock = new ReentrantLock(true);
    private ReentrantLock nofairReentrantLock = new ReentrantLock(false);

    public ReentrantLock getFairReentrantLock() {
        return fairReentrantLock;
    }

    public void service1() {
        System.out.println(Thread.currentThread().getName() + ", fairReentrantLock=" + fairReentrantLock.isFair());
        Assert.assertEquals(true, fairReentrantLock.isFair());
    }

    public void service2() {
        System.out.println(Thread.currentThread().getName() + ", nofairReentrantLock=" + nofairReentrantLock.isFair());
        Assert.assertEquals(false, nofairReentrantLock.isFair());
    }

    public void service3() {
        fairReentrantLock.lock();
        System.out.println(Thread.currentThread().getName() + ", fairReentrantLock isHeldByCurrentThread=" + fairReentrantLock.isHeldByCurrentThread());
        Assert.assertEquals(true, fairReentrantLock.isHeldByCurrentThread());

        System.out.println(Thread.currentThread().getName() + ", nofairReentrantLock isHeldByCurrentThread=" + nofairReentrantLock.isHeldByCurrentThread());
        Assert.assertEquals(false, nofairReentrantLock.isHeldByCurrentThread());
        fairReentrantLock.unlock();
    }

    public void service4() {
        try {
            fairReentrantLock.lock();
            System.out.print("                                                    ");
            System.out.println(Thread.currentThread().getName() + " do service4");
            Thread.sleep(5*1000);
            fairReentrantLock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

//======================================================================================================================

/**
 * @author nouuid
 * @date 4/7/2016
 * @description
 * lock.lockInterruptibly(): lock which can be interrupted
 * lock.tryLock(): lock occurs when the lock was not held by others
 * lock.tryLock(timeout, timeunit): lock occurs when the lock was not held by others in provided times
 */
class LockTypeOfLockRunner {

    public void lockInterruptiblyTest1() throws InterruptedException {
        LockTypeOfLockService lockTypeOfLockService = new LockTypeOfLockService();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                lockTypeOfLockService.service1();
            }
        });

        thread.setName("T1");
        thread.start();
        thread.interrupt();
    }

    public void lockInterruptiblyTest2() throws InterruptedException {
        LockTypeOfLockService lockTypeOfLockService = new LockTypeOfLockService();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                lockTypeOfLockService.service2();
            }
        });

        thread.setName("T1");
        thread.start();
        thread.interrupt();
    }

    public void tryLockTest() throws InterruptedException {
        LockTypeOfLockService lockTypeOfLockService = new LockTypeOfLockService();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                lockTypeOfLockService.service3();
            }
        };

        Thread[] threads = new Thread[10];
        for (int i=0; i<10; i++) {
            threads[i] = new Thread(runnable);
            threads[i].setName("T" + i);
        }

        for (int i=0; i<10; i++) {
            threads[i].start();
        }
    }

    public void timedTryLockTest() throws InterruptedException {
        LockTypeOfLockService lockTypeOfLockService = new LockTypeOfLockService();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                lockTypeOfLockService.service4();
            }
        };

        Thread threadA = new Thread(runnable);
        Thread threadB = new Thread(runnable);

        threadA.setName("TA");
        threadB.setName("TB");

        threadA.start();
        Thread.sleep(1 * 1000);
        threadB.start();
    }

    public void timedTryLockTest2() throws InterruptedException {
        LockTypeOfLockService lockTypeOfLockService = new LockTypeOfLockService();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                lockTypeOfLockService.service5();
            }
        };

        Thread threadA = new Thread(runnable);
        Thread threadB = new Thread(runnable);

        threadA.setName("TA");
        threadB.setName("TB");

        threadA.start();
        Thread.sleep(1 * 1000);
        threadB.start();
    }
}

class LockTypeOfLockService {

    private ReentrantLock reentrantLock = new ReentrantLock();

    public void service1() {
        try {
            reentrantLock.lock();

            System.out.println(Thread.currentThread().getName() + " lock begin");
            for (int i=0; i<Integer.MAX_VALUE/10; i++) {
                Math.random();
            }
            System.out.println(Thread.currentThread().getName() + " lock end");

            reentrantLock.unlock();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void service2() {
        try {
            reentrantLock.lockInterruptibly();
            System.out.println(Thread.currentThread().getName() + " lock begin");
            for (int i=0; i<Integer.MAX_VALUE/10; i++) {
                Math.random();
            }
            System.out.println(Thread.currentThread().getName() + " lock end");

            reentrantLock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void service3() {
        try {
            if (reentrantLock.tryLock()) {
                System.out.println(Thread.currentThread().getName() + " get lock");
            } else {
                System.out.println(Thread.currentThread().getName() + " don't get lock");
            }
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void service4() {
        try {
            System.out.println(Thread.currentThread().getName() + " invoke time=" + System.currentTimeMillis());
            if (reentrantLock.tryLock(1, TimeUnit.SECONDS)) {
                System.out.println(Thread.currentThread().getName() + " get lock time=" + System.currentTimeMillis());
                Thread.sleep(2 * 1000);
            } else {
                System.out.println(Thread.currentThread().getName() + " don't get lock");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (reentrantLock.isHeldByCurrentThread()) {
                reentrantLock.unlock();
            }
        }
    }

    public void service5() {
        try {
            System.out.println(Thread.currentThread().getName() + " invoke time=" + System.currentTimeMillis());
            if (reentrantLock.tryLock(2, TimeUnit.SECONDS)) {
                System.out.println(Thread.currentThread().getName() + " get lock time=" + System.currentTimeMillis());
                Thread.sleep(2 * 1000);
            } else {
                System.out.println(Thread.currentThread().getName() + " don't get lock");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (reentrantLock.isHeldByCurrentThread()) {
                reentrantLock.unlock();
            }
        }
    }
}

//======================================================================================================================

/**
 * @author nouuid
 * @date 4/7/2016
 * @description
 * awaitUninterruptibly()
 * awaitUntil(date)
 */
class AwaitExtendMethodRunner {

    public void awaitUninterruptiblyTest() throws InterruptedException {
        AwaitExtendMethodService awaitExtendMethodService = new AwaitExtendMethodService();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                awaitExtendMethodService.service1();
            }
        };
        Thread t1 = new Thread(runnable);
        t1.start();

        Thread.sleep(2*1000);
        System.out.println("before interrupt");
        t1.interrupt();
        System.out.println("after interrupt");
    }

    public void awaitUninterruptiblyTest2() throws InterruptedException {
        AwaitExtendMethodService awaitExtendMethodService = new AwaitExtendMethodService();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                awaitExtendMethodService.service2();
            }
        };
        Thread t1 = new Thread(runnable);
        t1.start();

        Thread.sleep(2*1000);
        System.out.println("before interrupt");
        t1.interrupt();
    }

    public void awaitUtilTest() throws InterruptedException {
        AwaitExtendMethodService awaitExtendMethodService = new AwaitExtendMethodService();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                awaitExtendMethodService.service3();
            }
        };
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);

        t1.setName("T1");
        t2.setName("T2");

        t1.start();
        Thread.sleep(2*1000);
        t2.start();
    }

    public void awaitUtilTest2() throws InterruptedException {
        AwaitExtendMethodService awaitExtendMethodService = new AwaitExtendMethodService();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                awaitExtendMethodService.service3();
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                awaitExtendMethodService.service4();
            }
        });

        t1.setName("T1");
        t2.setName("T2");

        t1.start();
        Thread.sleep(3*1000);
        t2.start();
    }
}

class AwaitExtendMethodService {

    private ReentrantLock reentrantLock = new ReentrantLock();
    private Condition condition = reentrantLock.newCondition();

    public void service1() {
        try {
            reentrantLock.lock();
            condition.await();
            System.out.println(Thread.currentThread().getName() + " do service1");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    public void service2() {
        reentrantLock.lock();
        condition.awaitUninterruptibly();
        System.out.println(Thread.currentThread().getName() + " do service2");
        reentrantLock.unlock();
    }

    public void service3() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 10);
        try {
            reentrantLock.lock();
            long start = System.currentTimeMillis();
            condition.awaitUntil(calendar.getTime());
            long end = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + " cost " + (end-start)/1000);
            System.out.println(Thread.currentThread().getName() + " do service3");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
        }
    }

    public void service4() {
        try {
            reentrantLock.lock();
            condition.signalAll();
            System.out.println(Thread.currentThread().getName() + " do service4");
        } finally {
            reentrantLock.unlock();
        }
    }
}


//======================================================================================================================
