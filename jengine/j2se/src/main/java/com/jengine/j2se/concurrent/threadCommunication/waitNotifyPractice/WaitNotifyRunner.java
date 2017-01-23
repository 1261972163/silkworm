package com.jengine.j2se.concurrent.threadCommunication.waitNotifyPractice;

/**
 * @author nouuid
 * @date 4/1/2016
 * @description
 *
 * 'wait' let thread stop running
 * 'notify' let thread continue to run
 */
public class WaitNotifyRunner {

    public void testWait() {
        WaitNotifyTask waitNotifyTask = new WaitNotifyTask();
        String lock = "123";
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                waitNotifyTask.wait(lock);
            }
        });
        thread.start();
    }

    public void testWaitNotify() {
        WaitNotifyTask waitNotifyTask = new WaitNotifyTask();
        String lock = "123";
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                waitNotifyTask.wait(lock);
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                waitNotifyTask.notify(lock);
            }
        });

        thread1.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread2.start();
    }

    public void testWaitNotifyAll() throws InterruptedException {
        WaitNotifyTask waitNotifyTask = new WaitNotifyTask();
        String lock = "123";
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                waitNotifyTask.wait(lock);
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                waitNotifyTask.wait(lock);
            }
        });
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                waitNotifyTask.notifyAll(lock);
            }
        });

        thread1.start();
        Thread.sleep(1000);
        thread2.start();
        Thread.sleep(1000);
        thread3.start();
    }

    public void testWaitNotifyAll2() throws InterruptedException {
        WaitNotifyTask waitNotifyTask = new WaitNotifyTask();
        String lock = "123";
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                waitNotifyTask.wait(lock);
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                waitNotifyTask.wait(lock);
            }
        });
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                waitNotifyTask.notify(lock);
            }
        });

        thread1.start();
        Thread.sleep(1000);
        thread2.start();
        Thread.sleep(1000);
        thread3.start();
    }

    public void testWaitInterrupt() throws InterruptedException {
        WaitNotifyTask waitNotifyTask = new WaitNotifyTask();
        String lock = "123";
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                waitNotifyTask.wait(lock);
            }
        });
        thread.start();

        Thread.sleep(1000);

        thread.interrupt();
    }

    public void testTimedWait() throws Exception {
        WaitNotifyTask waitNotifyTask = new WaitNotifyTask();
        String lock = "123";
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                waitNotifyTask.timedWait(lock);
            }
        });
        thread.start();
    }

    public void testHurryNotify() throws Exception {

    }


}

class WaitNotifyTask {

    public void wait(String lock) {
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
            WaitNotifyExceptionUtil.exception = e;
        }
    }

    public void timedWait(String lock) {
        try {
            System.out.println(Thread.currentThread().getName() + " wait syn up");
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " wait syn first line");
                lock.wait(5000);
                System.out.println(Thread.currentThread().getName() + " wait syn line after wait");
            }
            System.out.println(Thread.currentThread().getName() + " wait syn down");
        } catch (InterruptedException e) {
            e.printStackTrace();
            WaitNotifyExceptionUtil.exception = e;
        }
    }

    public void notify(String lock) {
        System.out.println(Thread.currentThread().getName() + " notify syn up");
        synchronized (lock) {
            System.out.println(Thread.currentThread().getName() + " notify syn first line");
            lock.notify(); // 1> don't release object lock immediately 2> notify only one waited thead
            System.out.println(Thread.currentThread().getName() + " notify syn line after notify");
        }
        System.out.println(Thread.currentThread().getName() + " service2 syn down");
    }

    public void notifyAll(String lock) {
        System.out.println(Thread.currentThread().getName() + " notifyAll syn up");
        synchronized (lock) {
            System.out.println(Thread.currentThread().getName() + " notifyAll syn first line");
            lock.notifyAll(); // don't release object lock immediately 2> notify all waited threads
            System.out.println(Thread.currentThread().getName() + " notifyAll syn line after notify");
        }
        System.out.println(Thread.currentThread().getName() + " notifyAll syn down");
    }


}
