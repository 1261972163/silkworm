package com.jengine.feature.concurrent.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author nouuid
 * @date 4/6/2016
 * @description
 */
public class ProducerConsumerRunner {

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