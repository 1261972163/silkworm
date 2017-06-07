package com.jengine.common.j2se.concurrent.collection;

/**
 * 1. 利用链表实现的有界阻塞队列，默认和最大长度为Integer.MAX_VALUE。
 * 2. 生产和消费使用不同的锁（ReentrantLock takeLock和ReentrantLock putLock），
 *      对于put和offer采用一把锁，对于take和poll则采用另外一把锁，
 *      避免了读写时互相竞争锁的情况，分离了读写线程安全，
 *      因此LinkedBlockingQueue在高并发读写操作都多的情况下，性能会较ArrayBlockingQueue好很多，
 *      在遍历以及删除元素则要两把锁都锁住。
 * 3. 阻塞由两个Condition（notEmpty和notFull）控制。队列元素位置计数由变量（AtomicInteger count）控制。
 *      put操作，在putLock锁内，若队列满，则阻塞notFull.await()，该阻塞在队列不满时由notFull.signal()唤醒。
 *      take操作，在takeLock锁内，若队列空，则阻塞notEmpty.await()，该阻塞在队列非空时由notEmpty.signal()唤醒。
 *      offer是无阻塞的enqueue或时间范围内阻塞enqueue，poll是无阻塞的dequeue或时间范围内阻塞dequeue。
 *
 * @author nouuid
 * @description
 * @date 5/11/17
 */
public class C03_LinkedBlockingQueue {
}
