package com.jengine.common2.j2se.concurrent.collection;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 1. BlockingQueue 源自jdk1.5，在Queue的基础上增加了2个操作：
 *      put操作，队列满时，存储元素的线程会阻塞，等待队列可用。
 *      take操作，队列为空时，获取元素的线程会阻塞，等待队列变为非空。
 * 2. ArrayBlockingQueue：一个用数组实现的有界阻塞队列。
 * 3. ArrayBlockingQueue原理：
 *      内部有一个ReentrantLock是生产和消费公用的，保证线程安全。
 *      阻塞由两个Condition（notEmpty和notFull）控制。
 *      取数据时，队列空，则notEmpty.await();取出数据后，notFull.signal();；
 *      添加数据时，队列满，则notFull.await()。添加数据后，notEmpty.signal()。
 *      队列元素位置计数由变量takeIndex、putIndex和count控制。
 * 4. ArrayBlockingQueue默认情况下不保证访问者公平的访问队列。
 * 5. 【公平访问队列】 指阻塞的所有生产者线程或消费者线程，当队列可用时，可以按照阻塞的先后顺序访问队列，
 *      即先阻塞的生产者线程，可以先往队列里插入元素，先阻塞的消费者线程，可以先从队列里获取元素。
 * 6. 通常情况下为了保证公平性会降低吞吐量。我们可以使用以下代码创建一个公平的阻塞队列：
 *      ArrayBlockingQueue fairQueue = new  ArrayBlockingQueue(1000,true);
 *
 * @author nouuid
 * @date 1/22/2017
 * @since 0.1.0
 */
public class C02_ArrayBlockingQueue {

    @Test
    public void putTake() throws InterruptedException {
        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(1);
        ThreadGroup group = new ThreadGroup("group");
        for (int i=1; i<=10; i++) {
            Thread thread = new Thread(group, new Runnable() {
                @Override
                public void run() {
                    try {
                        // 1. put操作，队列满时，存储元素的线程会阻塞，等待队列可用。
                        arrayBlockingQueue.put(Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName());
                }
            }, "p"+i);
            thread.start();
        }
        Thread.sleep(1000);
        // 其他9个线程都被阻塞了
        System.out.println(arrayBlockingQueue.size() + ", " + group.activeCount());
        System.out.println(group.activeCount());

        ThreadGroup group2 = new ThreadGroup("group2");
        for (int i=1; i<=15; i++) {
            Thread thread = new Thread(group2, new Runnable() {
                @Override
                public void run() {
                    try {
                        // 1. take操作，队列为空时，获取元素的线程会阻塞，等待队列变为非空。
                        // t11-t15的线程都被堵塞了，不打印
                        System.out.println("________" + arrayBlockingQueue.take());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "t"+i);
            thread.start();
        }
        Thread.sleep(1000);
        // 5个还存活
        System.out.println("________" + arrayBlockingQueue.size() + ", " + group2.activeCount());
    }
}
