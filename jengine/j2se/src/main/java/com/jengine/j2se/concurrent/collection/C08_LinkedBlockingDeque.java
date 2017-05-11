package com.jengine.j2se.concurrent.collection;

/**
 * BlockingDeque源自jdk1.6，是一种阻塞式并发双向队列，同时支持FIFO和FILO两种操作方式。
 *      所谓双向是指可以从队列的头和尾同时操作，并发只是线程安全的实现，阻塞允许在入队出队不满足条件时挂起线程，
 *      这里说的队列是指支持FIFO/FILO实现的链表。
 *
 * LinkedBlockingDeque**源自jdk1.6，
 * 1. 使用链表实现双向并发阻塞队列，根据构造传入的容量大小决定有界还是无界，默认不传的话，大小Integer.Max。
 * 2. 要想支持阻塞功能，队列的容量一定是固定的，否则无法在入队的时候挂起线程。也就是capacity是final类型的。
 * 3. 既然是双向链表，每一个结点就需要前后两个引用，这样才能将所有元素串联起来，支持双向遍历。也即需要prev/next两个引用。
 * 4. 双向链表需要头尾同时操作，所以需要first/last两个节点，当然可以参考LinkedList那样采用一个节点的双向来完成，
 *      那样实现起来就稍微麻烦点。
 * 5. 既然要支持阻塞功能，就需要锁和条件变量来挂起线程。这里使用一个锁两个条件变量来完成此功能。
 * 6. 由于采用一个独占锁，因此实现起来也比较简单。所有对队列的操作都加锁就可以完成。
 *      同时独占锁也能够很好的支持双向阻塞的特性。
 *      但由于独占锁，所以不能同时进行两个操作，这样性能上就大打折扣。
 *      从性能的角度讲LinkedBlockingDeque要比LinkedBlockingQueue要低很多，比CocurrentLinkedQueue就低更多了，
 *      这在高并发情况下就比较明显了。
 * @author nouuid
 * @description
 * @date 5/11/17
 */
public class C08_LinkedBlockingDeque {
}
