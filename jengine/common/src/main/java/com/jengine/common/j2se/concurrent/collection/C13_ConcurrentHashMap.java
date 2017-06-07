package com.jengine.common.j2se.concurrent.collection;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 在jdk5之前，线程安全的Map内置实现只有Hashtable和Properties（注：不考虑Collections.synchronizedMap）。
 * Properties基于Hashtable实现，前面已经讨论过Hashtable，已经过时，现在基本不再使用。
 * jdk5开始，新增加了2个线程安全的Map：ConcurrentHashMap和ConcurrentSkipListMap。
 *
 * 【ConcurrentHashMap】是HashMap的线程安全版本。
 *  jdk8之前的实现：
 *      1. ConcurrentHashMap使用锁分段技术，不仅保证了线程安全性，同时提高了并发访问效率。
 *      2. 锁分段的原理是：首先将数据分成一段一段的存储，然后给每一段数据配一把锁，当一个线程占用锁访问其中一个段数据的时候，
 *                      其他段的数据也能被其他线程访问。
 *      3. ConcurrentHashMap实现时，由一个Segment数组和多个HashEntry数组组成。
 *          Segment是一种可重入锁ReentrantLock，扮演锁的角色，结构和HashMap类似，是一种数组和链表结构。
 *          一个Segment里包含一个HashEntry数组，HashEntry则用于存储键值对数据，每个HashEntry是一个链表结构的元素。
 *          每个Segment守护者一个HashEntry数组里的元素，当对HashEntry数组的数据进行修改时，必须首先获得它对应的Segment锁。
 * jdk8之后的实现：
 *      1. ConcurrentHashMap实现线程安全的思想完全改变，摒弃了Segment（锁段）的概念，启用CAS算法实现。
 *          它沿用了与它同时期的HashMap版本的思想，底层依然由“数组+链表+红黑树”的方式思想，
 *          但是为了做到并发，又增加了很多辅助的类，例如TreeBin、Traverser等内部类。
 *      2. ConcurrentHashMap实现时，内部维护着一个table，里面存放着Node<K, V>，所有数据都在Node里面。
 *          Node是Map.Entry，和HashMap中Node的区别是，
 *              对value和next属性设置了volatile同步锁，
 *              不允许调用setValue方法直接改变Node的value域，
 *              增加了find方法辅助map.get()方法。
 *          casTabAt方法利用CAS算法设置i位置上的Node节点
 *              // U.compareAndSwapObject(tab, 内存位置, 期望值, 新值)
 *              U.compareAndSwapObject(tab, ((long)i << ASHIFT) + ABASE, c, v);
 *          tabAt方法利用volatile获得在i位置上的Node节点
 *              (Node<K,V>)U.getObjectVolatile(tab, ((long)i << ASHIFT) + ABASE);
 *          setTabAt方法利用volatile方法设置节点位置的值
 *              U.putObjectVolatile(tab, ((long)i << ASHIFT) + ABASE, v);
 *
 *          put操作时，
 *              根据Key计算hash值，选择table中相应的Node，
 *              然后对Node加synchronized锁，将数据封装到Node中，插入到链表头部。
 *              如果该链表长度超过TREEIFY_THRESHOLD，将该链表上所有Node转换成TreeNode，
 *              并将该链表转换成TreeBin，由TreeBin完成对红黑树的包装，加入到table中。
 *              也就是说在实际的ConcurrentHashMap“数组”中，此位置存放的是TreeBin对象，而不是TreeNode对象，
 *              这是与HashMap的区别。

 *
 * @author nouuid
 * @description
 * @date 5/11/17
 */
public class C13_ConcurrentHashMap {

    public void test() {
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<String, String>();
        concurrentHashMap.put("", "");
        concurrentHashMap.get("");
    }
}
