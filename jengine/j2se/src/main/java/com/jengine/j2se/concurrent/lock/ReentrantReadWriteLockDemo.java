package com.jengine.j2se.concurrent.lock;

import com.jengine.j2se.concurrent.ConcurrentTest;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author nouuid
 * @date 4/8/2016
 * @description
 */
public class ReentrantReadWriteLockDemo extends ConcurrentTest{

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


/**
 * @author nouuid
 * @date 4/8/2016
 * @description
 *
 * no mutex among read locks
 * mutex between read lock and write lock
 * mutex between write locks
 */
class ReentrantReadWriteLockRunner {
    public void amongReadLocksTest() {
        ReentrantReadWriteLockTask reentrantReadWriteLockTask = new ReentrantReadWriteLockTask();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                reentrantReadWriteLockTask.readLockService();
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

    public void amongWriteLocksTest() {
        ReentrantReadWriteLockTask reentrantReadWriteLockTask = new ReentrantReadWriteLockTask();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                reentrantReadWriteLockTask.writeLockService();
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

    public void amongReadWriteLocksTest() {
        ReentrantReadWriteLockTask reentrantReadWriteLockTask = new ReentrantReadWriteLockTask();
        Runnable readRunnable = new Runnable() {
            @Override
            public void run() {
                reentrantReadWriteLockTask.readLockService();
            }
        };

        Runnable writeRunnable = new Runnable() {
            @Override
            public void run() {
                reentrantReadWriteLockTask.writeLockService();
            }
        };

        Thread[] threads = new Thread[10];
        for (int i=0; i<10; i++) {
            if (i%2==0) {
                threads[i] = new Thread(readRunnable);
            } else{
                threads[i] = new Thread(writeRunnable);
            }
            threads[i].setName("T" + i);
        }

        for (int i=0; i<10; i++) {
            threads[i].start();
        }
    }

    public void amongReadWriteLocksTest2() {
        ReentrantReadWriteLockTask reentrantReadWriteLockTask = new ReentrantReadWriteLockTask();
        Runnable readRunnable = new Runnable() {
            @Override
            public void run() {
                reentrantReadWriteLockTask.readLockUnlockService();
            }
        };

        Runnable writeRunnable = new Runnable() {
            @Override
            public void run() {
                reentrantReadWriteLockTask.writeLockUnlockService();
            }
        };

        Thread[] threads = new Thread[10];
        for (int i=0; i<10; i++) {
            if (i%2==0) {
                threads[i] = new Thread(readRunnable);
            } else{
                threads[i] = new Thread(writeRunnable);
            }
            threads[i].setName("T" + i);
        }

        for (int i=0; i<10; i++) {
            threads[i].start();
        }
    }
}

class ReentrantReadWriteLockTask {
    private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    public void readLockService() {
        reentrantReadWriteLock.readLock().lock();
        System.out.println(Thread.currentThread().getName() + " read");
    }

    public void writeLockService() {
        reentrantReadWriteLock.writeLock().lock();
        System.out.println(Thread.currentThread().getName() + " write");
        try {
            Thread.sleep(1 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void readLockUnlockService() {
        try {
            reentrantReadWriteLock.readLock().lock();
            System.out.println(Thread.currentThread().getName() + " read");
            Thread.sleep(1 * 1000);
            reentrantReadWriteLock.readLock().unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void writeLockUnlockService() {
        try {
            reentrantReadWriteLock.writeLock().lock();
            System.out.println(Thread.currentThread().getName() + " write");
            Thread.sleep(1 * 1000);
            reentrantReadWriteLock.writeLock().unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}