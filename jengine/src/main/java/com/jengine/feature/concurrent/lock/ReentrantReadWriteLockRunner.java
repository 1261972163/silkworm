package com.jengine.feature.concurrent.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author nouuid
 * @date 4/8/2016
 * @description
 *
 * no mutex among read locks
 * mutex between read lock and write lock
 * mutex between write locks
 */
public class ReentrantReadWriteLockRunner {
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
            Thread.sleep(1*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void readLockUnlockService() {
        try {
            reentrantReadWriteLock.readLock().lock();
            System.out.println(Thread.currentThread().getName() + " read");
            Thread.sleep(1*1000);
            reentrantReadWriteLock.readLock().unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void writeLockUnlockService() {
        try {
            reentrantReadWriteLock.writeLock().lock();
            System.out.println(Thread.currentThread().getName() + " write");
            Thread.sleep(1*1000);
            reentrantReadWriteLock.writeLock().unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}