package com.jengine.j2se.concurrent.collection;

/**
 * @BlockingQueue 源自jdk1.5，在Queue的基础上增加了2个操作：
 *
 * put操作，队列满时，存储元素的线程会阻塞，等待队列可用。
 * take操作，队列为空时，获取元素的线程会阻塞，等待队列变为非空。
 *
 * @ArrayBlockingQueue定义 一个用数组实现的有界阻塞队列。
 *
 * @ArrayBlockingQueue原理
 * 内部有一个ReentrantLock是生产和消费公用的，保证线程安全。
 * 阻塞由两个Condition（notEmpty和notFull）控制。
 * 取数据时，队列空，则notEmpty.await();取出数据后，notFull.signal();；
 * 添加数据时，队列满，则notFull.await()。添加数据后，notEmpty.signal()。
 * 队列元素位置计数由变量takeIndex、putIndex和count控制。
 *
 * ArrayBlockingQueue默认情况下不保证访问者公平的访问队列。
 *
 * @公平访问队列 指阻塞的所有生产者线程或消费者线程，当队列可用时，可以按照阻塞的先后顺序访问队列，即先阻塞的生产者线程，可以先往队列里插入元素，先阻塞的消费者线程，可以先从队列里获取元素。
 * 通常情况下为了保证公平性会降低吞吐量。我们可以使用以下代码创建一个公平的阻塞队列：
 *      ArrayBlockingQueue fairQueue = new  ArrayBlockingQueue(1000,true);
 *
 * @author bl07637
 * @date 1/22/2017
 * @since 0.1.0
 */
public class ArrayBlockingQueueDemo {
}
