package com.jengine.common2.j2se.concurrent.collection;

/**
 * 【TransferQueue】源自jdk1.7，是一种BlockingQueue，增加了transfer相关的方法。
 *      transfer的语义是，生产者会一直阻塞直到transfer到队列的元素被某一个消费者所消费（不仅仅是添加到队列里就完事）。
 *      使用put时不等待消费者消费。
 *
 * LinkedTransferQueue
 * 1. 采用的一种预占模式。意思就是消费者线程取元素时，如果队列为空，那就生成一个节点（节点元素为null）入队，
 *      然后消费者线程park住，后面生产者线程入队时发现有一个元素为null的节点，生产者线程就不入队了，
 *      直接就将元素填充到该节点，唤醒该节点上park住线程，被唤醒的消费者线程拿货走人。
 * 2. 使用链表实现TransferQueue接口。
 *
 * @author nouuid
 * @description
 * @date 5/11/17
 */
public class C10_LinkedTransferQueue {
}
