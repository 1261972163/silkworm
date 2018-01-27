package com.jengine.common.j2se.concurrent.lock;

import com.jengine.common.j2se.concurrent.ConcurrentTest;
import junit.framework.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
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
 *      ReentrantLock.isFair() 判断是否为公平锁
 *      ReentrantLock.isLocked() 锁是否被持有
 *      ReentrantLock.isHeldByCurrentThread() 当前线程是否持有锁
 * 7. 状态管理
 *      lock.getHoldCount(): 当前线程持有该锁的次数
 *      lock.getQueueLength()： 等待持有该锁的线程数
 *      lock.getWaitQueueLength(condition)： 等待锁的Condition的线程数
 *      lock.hasWaiters(condition)：锁的Condition是否有等待的线程
 *      lock.hasQueuedThreads()： 是否有等待锁的线程
 *      lock.hasQueuedThread(thread)： thread线程是否在等待锁
 * 8. ReentrantLock可实现中断
 *      ReentrantLock.lock()    不可中断
 *      ReentrantLock.lockInterruptibly() 可中断
 * 9. ReentrantLock 尝试加锁，等待可中断
 *      lock.tryLock()   锁没被其他线程持有时，加锁
 *      lock.tryLock(timeout, timeunit)  在一段时间内，锁没被其他线程持有时，加锁；否则放弃
 * 10. condition.awaitUninterruptibly()不可被中断
 * 11. condition.awaitUntil(time)等待一段时间，时间过后不等待了
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

    volatile boolean flag = true;
    volatile boolean res = false;

    // 8. ReentrantLock可实现中断
    //      ReentrantLock.lock()    不可中断
    //      ReentrantLock.lockInterruptibly() 可中断
    @Test
    public void lockType() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 不可中断
                reentrantLock.lock();
                try {
                    while (flag) {
                        Math.random();
                    }
                } finally {
                    res = true;
                    reentrantLock.unlock();
                }
            }
        });
        thread.start();
        thread.interrupt();
        Thread.sleep(3*1000);
        // 因为不可中断，res不会变为true
        Assert.assertEquals(false, res);
        flag = false;
        Thread.sleep(1*1000);
        Assert.assertEquals(true, res);

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 可中断
                    reentrantLock.lockInterruptibly();
                    while (flag) {
                        Math.random();
                    }
                } catch (InterruptedException e) {
                    if (e instanceof InterruptedException) {
                        Assert.assertTrue(true);
                    }
                } finally {
                    res = true;
                    try {
                        reentrantLock.unlock();
                    } catch (IllegalMonitorStateException e2) {
                        // 中断后，自动解锁，再次解锁会抛错
                    }
                }
            }
        });
        thread2.start();
        thread2.interrupt();
        Thread.sleep(3*1000);
        // 因为可中断，res变为true
        Assert.assertEquals(true, res);

        Thread.sleep(5000);
    }

    // 9. ReentrantLock 尝试加锁，等待可中断
    //      lock.tryLock()   锁没被其他线程持有时，加锁
    //      lock.tryLock(timeout, timeunit)  在一段时间内，锁没被其他线程持有时，加锁；否则放弃
    @Test
    public void tryLock() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        // lock.tryLock()   锁没被其他线程持有时，加锁
        for (int i = 1; i <= 3; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (reentrantLock.tryLock()) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            if (reentrantLock.isHeldByCurrentThread()) {
                                reentrantLock.unlock();
                            }
                        }
                    }
                }
            }, "t" + i);
            if (i == 1) {
                // t1启动前，锁没有被获取
                Assert.assertFalse(reentrantLock.isLocked());
            } else if (i == 2) {
                // t2启动前，t1仍在运行，未释放锁
                Assert.assertTrue(reentrantLock.isLocked());
            } else if (i == 3) {
                // t3启动前，t1运行结束，释放锁
                Assert.assertFalse(reentrantLock.isLocked());
            }
            t.start();
            if (i == 1) {
                Thread.sleep(50);
                // t1启动后，t1获取锁
                Assert.assertTrue(reentrantLock.isLocked());
                Thread.sleep(1000);
            } else if (i == 2) {
                Thread.sleep(50);
                // t2启动时，t1仍然持有锁运行
                Assert.assertTrue(reentrantLock.isLocked());
                // t2不等待锁
                Assert.assertFalse(reentrantLock.hasQueuedThread(t));
                Thread.sleep(2000);
            } else if (i == 3) {
                Thread.sleep(50);
                // t3启动时，t1运行结束，释放锁，t3获取到锁
                Assert.assertTrue(reentrantLock.isLocked());
            }
        }
    }

    @Test
    public void tryLockTimeout() throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        // lock.tryLock(timeout, timeunit)  在一段时间内，锁没被其他线程持有时，加锁；否则放弃
        for (int i=1; i<=3; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (reentrantLock.tryLock(1, TimeUnit.SECONDS)) {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } finally {
                                if (reentrantLock.isHeldByCurrentThread()) {
                                    reentrantLock.unlock();
                                }
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "t"+i);
            if (i==1) {
                // t1启动前，锁没有被获取
                Assert.assertFalse(reentrantLock.isLocked());
            } else if (i==2) {
                // t2启动前，t1仍在运行，未释放锁
                Assert.assertTrue(reentrantLock.isLocked());
            } else if (i==3) {
                // t3启动前，t1运行结束，释放锁
                Assert.assertFalse(reentrantLock.isLocked());
            }
            t.start();
            if (i==1) {
                Thread.sleep(50);
                // t1启动后，t1获取锁
                Assert.assertTrue(reentrantLock.isLocked());
                Thread.sleep(1000);
            } else if (i==2) {
                Thread.sleep(50);
                // t2启动时，t1仍然持有锁运行
                Assert.assertTrue(reentrantLock.isLocked());
                // t2不等待锁
                Assert.assertTrue(reentrantLock.hasQueuedThread(t));
                Thread.sleep(1000);
                // t1运行结束，释放锁，t2获得锁
                Assert.assertTrue(reentrantLock.isLocked());
                Assert.assertFalse(reentrantLock.hasQueuedThread(t));
                Thread.sleep(5000);
            } else if (i==3) {
                Thread.sleep(50);
                // t3启动时，t1运行结束，释放锁，t3获取到锁
                Assert.assertTrue(reentrantLock.isLocked());
            }
        }
    }

    // 10. condition.awaitUninterruptibly()不可被中断
    @Test
    public void awaitUninterruptibly() throws InterruptedException {
        res = true;
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();

        // condition.awaitUninterruptibly()不可被中断
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                reentrantLock.lock();
                try {
                    condition.awaitUninterruptibly();
                } finally {
                    res = false;
                    reentrantLock.unlock();
                }
            }
        });
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
        Thread.sleep(1000);
        Assert.assertTrue(res);

        // condition.await()可被中断
        res = true;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                reentrantLock.lock();
                try {
                    condition.await();
                } catch (Exception e) {

                } finally {
                    res = false;
                    reentrantLock.unlock();
                }
            }
        });
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
        Thread.sleep(1000);
        Assert.assertFalse(res);
    }

    // 11. condition.awaitUntil(time)等待一段时间，时间过后不等待了
    @Test
    public void awaitUntil() throws InterruptedException {
        res = true;
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                reentrantLock.lock();
                try {
                    condition.await(2, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    res = false;
                    reentrantLock.unlock();
                }
            }
        });
        thread.start();
        Thread.sleep(1000);
        Assert.assertTrue(res);
        Thread.sleep(2000);
        Assert.assertFalse(res);

    }

}