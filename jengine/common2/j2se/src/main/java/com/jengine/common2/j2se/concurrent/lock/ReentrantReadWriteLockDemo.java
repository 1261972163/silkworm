package com.jengine.common2.j2se.concurrent.lock;

import com.jengine.common2.j2se.concurrent.ConcurrentTest;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 1. ReentrantReadWriteLock有两个锁
 *      ReentrantReadWriteLock.readLock()，读相关的共享锁
 *      ReentrantReadWriteLock.writeLock()，写操作相关的排他锁
 *      读读不互斥，读写互斥，写写互斥
 * @author nouuid
 * @date 4/8/2016
 * @description
 */
public class ReentrantReadWriteLockDemo extends ConcurrentTest{

    // 1. ReentrantReadWriteLock有两个锁
    //      ReentrantReadWriteLock.readLock()，读相关的共享锁
    //      ReentrantReadWriteLock.writeLock()，写操作相关的排他锁
    //      读读不互斥，读写互斥，写写互斥
    @Test
    public void twoLock() throws InterruptedException {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        String[] spaces = {"", "       ", "             ", "                     "};
        for (int j=1; j<=4; j++) {
            final int index = j;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (index==1 || index==2) {
                        reentrantReadWriteLock.readLock().lock();
                    }
                    if (index==3 || index==4) {
                        reentrantReadWriteLock.writeLock().lock();
                    }
                    try {
                        for (int i = 1; i <= 100; i++) {
                            System.out.println(Thread.currentThread().getName() + "-" + i);
                            try {
                                Thread.sleep(30);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    } finally {
                        if (index==1 || index==2) {
                            reentrantReadWriteLock.readLock().unlock();
                        }
                        if (index==3 || index==4) {
                            reentrantReadWriteLock.writeLock().unlock();
                        }
                    }
                }
            }, spaces[j-1] + "t"+j);
            if (index==1) {
                // t1获取读锁
                thread.start();
                Thread.sleep(10);
            } else if (index==2) {
                // t2也获取读锁，没有被阻塞
                thread.start();
                Thread.sleep(10);
            } else if (index==3) {
                // t3要获取写锁，和t1/t2读锁互斥，t3被阻塞，等到t1和t2都释放读锁后，t3获取到写锁
                thread.start();
                Thread.sleep(3000);
            } else if (index==4) {
                // t4要获取写锁，和t3写锁4斥，t3被阻塞，等到t3是否写锁后，t4获取到写锁
                thread.start();
                Thread.sleep(1000*6);
            }
        }
    }

}