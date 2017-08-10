package com.jengine.common2.j2se.concurrent.threadCommunication;

import com.jengine.common2.j2se.concurrent.ConcurrentTest;
import org.junit.Test;

/**
 * 1. Object.wait()必须放在同步块中执行，使当前线程阻塞
 * 2. Object.notify()必须放在同步块中执行，从wait状态的线程中随机挑选一个进行唤醒，让其向下执行
 * 3. wait()后立即释放锁，notify()后不立即释放锁，而是执行完同步块内容后释放锁
 * 4. notifyAll()唤醒所有wait状态线程
 * 5. timedWait()使当前线程阻塞一段时间
 *
 * @author nouuid
 * @date 1/18/2017
 * @since 0.1.0
 */
public class WaitNotifyDemo extends ConcurrentTest {
    // 1. wait()使当前线程阻塞，notify()从wait状态的线程中随机挑选一个进行唤醒，让其向下执行
    @Test
    public void waitNotify() throws InterruptedException {
        Object object = new Object();
        for (int i=1; i<=2; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    // 1. wait()放在同步块中执行
                    synchronized (object) {
                        try {
                            // 3. wait()后立即释放锁
                            object.wait();
                            System.out.println(Thread.currentThread().getName());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, "t"+i);
            thread.start();
        }
        Thread.sleep(2000);
        // 2. notify()放在同步块中执行
        synchronized (object) {
            // notify只能唤醒一个wait的线程
            object.notify();
            Thread.sleep(1000);
            // 3. notify()后不立即释放锁，而是执行完同步块内容后释放锁
            System.out.println(Thread.currentThread().getName());
        }
        Thread.sleep(3000);
        synchronized (object) {
            // 4. notifyAll()唤醒所有wait状态线程
            object.notifyAll();
            Thread.sleep(1000);
            // 不立即释放锁，执行完同步块内容后释放锁
            System.out.println(Thread.currentThread().getName());
        }
    }

    // 5. timedWait()使当前线程阻塞一段时间
    @Test
    public void timedWait() throws InterruptedException {
        Object object = new Object();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 放在同步块中执行
                synchronized (object) {
                    try {
                        // wait()阻塞，并立即释放锁
                        object.wait(1000);
                        // 阻塞时间过后，线程向下执行
                        System.out.println(Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "t1");
        thread.start();
        Thread.sleep(2000);
    }

}
