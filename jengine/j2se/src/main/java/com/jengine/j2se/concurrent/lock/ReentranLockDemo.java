package com.jengine.j2se.concurrent.lock;

import com.jengine.j2se.concurrent.ConcurrentTest;
import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1. ReentrantLock可实现线程同步
 * 2. await/signal实现等待/通知模式。await时释放锁，signal通知唤醒一个该Condition的await。
 * 3. signalAll唤醒所有该Condition的await
 * 4. 使用多个Condition实现通知部分线程
 * 5. ReentrantLock默认是公平锁，多个线程在等待同一个锁时，必须按照申请锁的时间顺序来依次获得锁。
 * 6. ReentrantLock可以构造非公平锁，在锁释放时，任何一个等待锁的线程都有机会获得锁。
 * 7. lock.getHoldCount(): 当前线程持有该锁的次数
 * @author nouuid
 * @date 4/6/2016
 * @description
 */
public class ReentranLockDemo extends ConcurrentTest {

    // 1. ReentrantLock可实现线程同步
    @Test
    public void lock() throws InterruptedException {
        class Counter {
            private Lock locker = new ReentrantLock();
            private int count = 0;

            public void add1() {
                locker.lock();
                try {
                    count += 1;
                } finally {
                    locker.unlock();
                }
            }

            public void add2() {
                locker.lock();
                try {
                    count += 2;
                } finally {
                    locker.unlock();
                }
            }

            public int get() {
                locker.lock();
                try {
                    return count;
                } finally {
                    locker.unlock();
                }
            }
        }

        Counter counter = new Counter();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    counter.add1();
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    counter.add2();
                }
            }
        });
        t1.start();
        t2.start();
        Thread.sleep(5000);
        System.out.println(counter.get());
    }

    // 2. await/notify实现等待/通知模式。await时释放锁，signal通知被await阻塞的线程重新获取锁。
    @Test
    public void awaitSignal() throws InterruptedException {
        Lock locker = new ReentrantLock();
        Condition condition = locker.newCondition();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                locker.lock();
                try {
                    // await释放锁
                    condition.await();
                    System.out.println(Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    locker.unlock();
                }
            }
        }, "t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                locker.lock();
                try {
                    // await释放锁
                    condition.await();
                    System.out.println(Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    locker.unlock();
                }
            }
        }, "t2");
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                locker.lock();
                try {
                    System.out.println(Thread.currentThread().getName());
                    // signal通知被await阻塞的线程去获取锁
                    condition.signal();
                } finally {
                    // 释放锁后，被await阻塞的线程获得锁
                    locker.unlock();
                }
            }
        }, "t3");
        t1.start();
        Thread.sleep(500);
        t2.start();
        Thread.sleep(500);
        t3.start();
        Thread.sleep(15*1000);
    }

    // 3. signalAll唤醒所有该Condition的await
    @Test
    public void awaitSignalAll() throws InterruptedException {
        Lock locker = new ReentrantLock();
        Condition condition = locker.newCondition();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                locker.lock();
                try {
                    // await释放锁
                    condition.await();
                    System.out.println(Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    locker.unlock();
                }
            }
        }, "t1");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                locker.lock();
                try {
                    // await释放锁
                    condition.await();
                    System.out.println(Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    locker.unlock();
                }
            }
        }, "t2");
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                locker.lock();
                try {
                    System.out.println(Thread.currentThread().getName());
                    // signal通知被await阻塞的线程去获取锁
                    condition.signalAll();
                } finally {
                    // 释放锁后，被await阻塞的线程获得锁
                    locker.unlock();
                }
            }
        }, "t3");
        t1.start();
        Thread.sleep(500);
        t2.start();
        Thread.sleep(500);
        t3.start();
        Thread.sleep(15*1000);
    }

    // 4. 使用多个Condition实现通知部分线程
    // t3发通知，通知t1，不通知t2
    @Test
    public void multiConditionAwaitSingal() throws InterruptedException {
        Lock locker = new ReentrantLock();
        Condition conditionA = locker.newCondition();
        Condition conditionB = locker.newCondition();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                locker.lock();
                try {
                    conditionA.await();
                    System.out.println(Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    locker.unlock();
                }
            }
        }, "t1");

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                locker.lock();
                try {
                    conditionB.await();
                    System.out.println(Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    locker.unlock();
                }
            }
        }, "t2");

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                locker.lock();
                try {
                    conditionA.signalAll();
                    System.out.println(Thread.currentThread().getName());
                } finally {
                    locker.unlock();
                }
            }
        }, "t3");

        t1.start();
        Thread.sleep(1000);
        t2.start();
        Thread.sleep(1000);
        t3.start();

        Thread.sleep(30*1000);
    }

    // 5. ReentrantLock默认是公平锁，多个线程在等待同一个锁时，必须按照申请锁的时间顺序来依次获得锁。
    @Test
    public void fairLock() throws InterruptedException {
        Lock lock = new ReentrantLock();
        ArrayList<Thread> threadArrayList = new ArrayList<Thread>();
        for (int i=1; i<=10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    lock.lock();
                    try {
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            }, "t"+i);
            threadArrayList.add(thread);
        }
        for (int i=1; i<=10; i++) {
            threadArrayList.get(i-1).start();
        }
        Thread.sleep(15*1000);
    }

    // 6. ReentrantLock可以构造非公平锁，在锁释放时，任何一个等待锁的线程都有机会获得锁。
    // ReentrantLock.isFair() 判断是否为公平锁
    // ReentrantLock.isLocked() 锁是否被持有
    // ReentrantLock.isHeldByCurrentThread() 当前线程是否持有锁
    @Test
    public void unfairLock() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock(false);
        // 判断是否为公平锁
        Assert.assertEquals(false, lock.isFair());
        // 锁是否被持有
        Assert.assertEquals(false, lock.isLocked());
        ArrayList<Thread> threadArrayList = new ArrayList<Thread>();
        for (int i=1; i<=10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    lock.lock();
                    Assert.assertEquals(true, lock.isLocked());
                    try {
                        Thread.sleep(1000);
                        // 当前线程是否持有锁
                        Assert.assertEquals(true, lock.isHeldByCurrentThread());
                        System.out.println(Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            }, "t"+i);
            threadArrayList.add(thread);
        }
        for (int i=1; i<=10; i++) {
            threadArrayList.get(i-1).start();
        }
        Thread.sleep(15*1000);
    }

    // 7. 状态管理
    // lock.getHoldCount(): 当前线程持有该锁的次数
    // lock.getQueueLength()： 等待持有该锁的线程数
    // lock.getWaitQueueLength(condition)： 等待锁的Condition的线程数
    // lock.hasWaiters(condition)：锁的Condition是否有等待的线程
    // lock.hasQueuedThreads()： 是否有等待锁的线程
    // lock.hasQueuedThread(thread)： thread线程是否在等待锁
    @Test
    public void status() throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Thread t0 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    // 当前线程持有该锁的次数
                    Assert.assertEquals(1, lock.getHoldCount());
                    lock.lock();
                    try {
                        Assert.assertEquals(2, lock.getHoldCount());
                    } finally {
                        lock.unlock();
                    }
                } finally {
                    lock.unlock();
                }
            }
        });
        t0.start();
        Thread.sleep(3000);

        Condition condition = lock.newCondition();
        List<Thread> threads = new ArrayList<Thread>();
        for (int i=1; i<=10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    lock.lock();
                    try {
                        condition.await();
                        Thread.sleep(50);
                        // 等待持有该锁的线程数
                        int length = lock.getQueueLength();
                        System.out.println(Thread.currentThread().getName() + " getQueueLength=" + length);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            }, "t"+i);
            threads.add(thread);
        }
        for (int i=1; i<=10; i++) {
            threads.get(i-1).start();
        }
        Thread.sleep(1000);
        lock.lock();
        try {
            // 等待锁的Condition的线程数
            Assert.assertEquals(10, lock.getWaitQueueLength(condition));
            // 锁的Condition是否有等待的线程
            Assert.assertEquals(true, lock.hasWaiters(condition));
            // 通知所有的await
            condition.signalAll();
            // 是否有等待锁的线程
            Assert.assertEquals(true, lock.hasQueuedThreads());
            // t0线程是否在等待锁
            Assert.assertEquals(true, lock.hasQueuedThread(threads.get(0)));
        } finally {
            lock.unlock();
        }
        Thread.sleep(3000);

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
