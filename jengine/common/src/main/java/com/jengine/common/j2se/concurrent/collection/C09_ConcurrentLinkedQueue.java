package com.jengine.common.j2se.concurrent.collection;

/**
 * ConcurrentLinkedQueue**源自jdk1.5，是一种非阻塞式并发链表。
 *
 * 1. 采用先进先出的规则对节点进行排序，当我们添加一个元素的时候，它会添加到队列的尾部，
 *      当我们获取一个元素时，它会返回队列头部的元素。
 * 2. 由head节点和tair节点组成，每个节点（Node）由节点元素（item）和指向下一个节点的引用(next)组成，
 *      节点与节点之间就是通过这个next关联起来，从而组成一张链表结构的队列。默认情况下head节点存储的元素为空，tair节点等于head节点。
 * 3. 使用的wait-free算法解决并发问题。
 * @author nouuid
 * @description
 * @date 5/11/17
 */
public class C09_ConcurrentLinkedQueue {
}
