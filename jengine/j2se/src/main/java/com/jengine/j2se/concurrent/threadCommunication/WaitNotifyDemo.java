package com.jengine.j2se.concurrent.threadCommunication;

import com.jengine.j2se.concurrent.ConcurrentTest;
import org.junit.Test;

/**
 * @wait 当前执行代码的线程阻塞
 *（1）必须放在同步块中执行
 *（2）执行wait方法后立即释放锁
 *
 * @notify 从wait状态的线程中随机挑选一个进行唤醒，让其向下执行
 *（1）必须放在同步块中执行
 *（2）执行notify方法后不立即释放锁，而是执行完同步块内容后释放锁
 *
 * @author bl07637
 * @date 1/18/2017
 * @since 0.1.0
 */
public class WaitNotifyDemo extends ConcurrentTest {
    Exception exception;

    @Test
    public void testWait() throws InterruptedException {
        String lock = "123";
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + " wait syn up");
                    synchronized (lock) {
                        System.out.println(Thread.currentThread().getName() + " wait syn first line");
                        lock.wait(); // release object lock immediately
                        System.out.println(Thread.currentThread().getName() + " wait syn line after wait");
                    }
                    System.out.println(Thread.currentThread().getName() + " wait syn down");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    exception = e;
                }
            }
        });
        thread.start();
        thread.join();
    }

    @Test
    public void waitNotify() throws InterruptedException {
        String lock = "123";
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("---------1");
                    synchronized (lock) {
                        System.out.println("---------2");
                        lock.wait(); // release object lock immediately
                        System.out.println("---------5");
                    }
                    System.out.println("---------6");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    exception = e;
                }
            }
        });
        thread1.start();
        Thread.sleep(5*1000);
        synchronized (lock) {
            System.out.println("---------3");
            lock.notify();
            Thread.sleep(3000);
            System.out.println("---------4");
        }
        Thread.sleep(5*1000);
        System.out.println("---------7");
    }
}
