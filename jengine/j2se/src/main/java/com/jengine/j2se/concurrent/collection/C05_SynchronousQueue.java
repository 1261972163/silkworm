package com.jengine.j2se.concurrent.collection;

import org.junit.Test;

import java.util.concurrent.SynchronousQueue;

/**
 * SynchronousQueue源自jdk1.5，是一个不存储元素的阻塞队列。
 * 1. 每一个put操作必须等待一个take操作，否则不能继续添加元素。
 *      SynchronousQueue可以看成是一个传球手，负责把生产者线程处理的数据直接传递给消费者线程。
 * 2. 可以认为SynchronousQueue是一个缓存值为1的阻塞队列，不能调用peek()方法来看队列中是否有数据元素，
 *      因为数据元素只有当你试着取走的时候才可能存在，不取走而只想偷窥一下是不行的，当然遍历这个队列的操作也是不允许的。
 *      isEmpty()方法永远返回是true，
 *      remainingCapacity() 方法永远返回是0，
 *      remove()和removeAll() 方法永远返回是false，
 *      iterator()方法永远返回空，
 *      peek()方法永远返回null。
 * 3. 队列本身并不存储任何元素，非常适合于传递性场景，比如在一个线程中使用的数据，传递给另外一个线程使用，
 *      SynchronousQueue的吞吐量高于LinkedBlockingQueue 和 ArrayBlockingQueue。
 * 4. SynchronousQueue的一个使用场景是在线程池里。
 *      Executors.newCachedThreadPool()就使用了SynchronousQueue，
 *      这个线程池根据需要（新任务到来时）创建新的线程，如果有空闲线程则会重复使用，线程空闲了60秒后会被回收。
 * @author nouuid
 * @description
 * @date 5/11/17
 */
public class C05_SynchronousQueue {

    @Test
    public void test() throws InterruptedException {
        SynchronousQueue<String> synchronousQueue = new SynchronousQueue<String>();
        ThreadGroup group = new ThreadGroup("group");
        for (int i=1; i<=1; i++) {
            Thread t = new Thread(group, new Runnable() {
                @Override
                public void run() {
                    try {
                        synchronousQueue.put("i");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "t"+i);
            t.start();
        }
        Thread.sleep(1000);
        // 1. 每一个put操作必须等待一个take操作，否则不能继续添加元素。
        // t1阻塞，未结束
        System.out.println(group.activeCount());
        // take取走后，t1运行结束
        synchronousQueue.take();
        Thread.sleep(1000);
        System.out.println(group.activeCount());


    }
}
