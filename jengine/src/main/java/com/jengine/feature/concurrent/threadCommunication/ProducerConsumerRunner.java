package com.jengine.feature.concurrent.threadCommunication;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bl07637
 * @date 4/1/2016
 * @description
 */
public class ProducerConsumerRunner {

    public static int num = 1;

    public void valueTest() throws Exception {
        num = 1;
        valueProcess();
    }

    public void valueProtendedDeath() throws Exception {
        num = 10;
        valueProcess();
    }

    public void stackTest() throws Exception {
        num = 1;
        stackProcess();
    }

    public void stackProtendedDeath() throws Exception {
        num = 10;
        stackProcess();
    }

    public void valueProcess() throws InterruptedException {
        String lock = "lock";
        ProducerConsumerValueProducer producerConsumerValueProducer = new ProducerConsumerValueProducer(lock);
        ProducerConsumerValueConsumer producerConsumerValueConsumer = new ProducerConsumerValueConsumer(lock);

        Thread[] producerThreads = new Thread[num];
        Thread[] consumerThreads = new Thread[num];
        for (int i=0; i<num; i++) {
            producerThreads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        producerConsumerValueProducer.produce();
                    }
                }
            });
            producerThreads[i].setName("p" + i);

            consumerThreads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        producerConsumerValueConsumer.consume();
                    }
                }
            });
            consumerThreads[i].setName("c" + i);
        }

        for (Thread thread : producerThreads) {
            thread.start();
        }

        Thread.sleep(1000);

        for (Thread thread : consumerThreads) {
            thread.start();
        }
    }

    public void stackProcess() throws Exception {
        ProducerConsumerMyStack producerConsumerMyStack = new ProducerConsumerMyStack();

        Thread[] producerThreads = new Thread[num];
        Thread[] consumerThreads = new Thread[num];

        for (int i=0; i<num; i++) {
            producerThreads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        producerConsumerMyStack.push();
                    }
                }
            });

            consumerThreads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        producerConsumerMyStack.pop();
                    }
                }
            });
        }

        for (Thread thread : producerThreads) {
            thread.start();
        }

        Thread.sleep(1000);

        for (Thread thread : consumerThreads) {
            thread.start();
        }

    }
}

class ProducerConsumerValueProducer {
    private String lock;

    public ProducerConsumerValueProducer(String lock) {
        this.lock = lock;
    }

    public void produce() {
        try {
            synchronized (lock) {
                if (!ProducerConsumerValueObject.value.equals("")) {
                    lock.wait();
                }
                ProducerConsumerValueObject.value = "123";
                System.out.println(Thread.currentThread().getName() + " in <- 123");
                lock.notify();
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

class ProducerConsumerValueConsumer {
    private String lock;

    public ProducerConsumerValueConsumer(String lock) {
        this.lock = lock;
    }

    public void consume() {
        try {
            synchronized (lock) {
                if (ProducerConsumerValueObject.value.equals("")) {
                    lock.wait();
                }
                ProducerConsumerValueObject.value = "";
                System.out.println("                      " + Thread.currentThread().getName() + " out -> 123");
                lock.notify();
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ProducerConsumerValueObject {
    public static String value = "";
}

class ProducerConsumerMyStack {

    private List list = new ArrayList();

    synchronized public void push() {
        try {
            if (list.size()==1) {
                this.wait();
            }
            list.add("123");
            System.out.println(Thread.currentThread().getName() + " in <- 123");
            this.notify();
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    synchronized public void pop() {
        try {
            if (list.size()==0) {
                this.wait();
            }
            list.clear();
            System.out.println("                      " + Thread.currentThread().getName() + " out -> 123");
            this.notify();
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}