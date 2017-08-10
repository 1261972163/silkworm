package com.jengine.common2.j2se.concurrent.collection;

/**
 * ConcurrentSkipListMap是TreeMap的线程安全版本，使用CAS算法实现线程安全，适用于多线程情况下对Map的键值进行排序。
 *
 * 对于键值排序需求，非多线程情况下，应当尽量使用TreeMap；
 * 对于并发性相对较低的并行程序，可以使用Collections.synchronizedSortedMap将TreeMap进行包装，也可以提供较好的效率。
 * 对于高并发程序，应当使用ConcurrentSkipListMap，能够提供更高的并发度。
 * 和ConcurrentHashMap相比，ConcurrentSkipListMap 支持更高的并发。
 * ConcurrentSkipListMap 的存取时间是log（N），和线程数几乎无关。
 * 也就是说在数据量一定的情况下，并发的线程越多，ConcurrentSkipListMap越能体现出他的优势。
 *
 * ConcurrentSkipListMap由跳表（Skip list）实现，默认是按照Key值升序的。
 *      内部主要由Node和Index组成。
 *      同ConcurrentHashMap的Node节点一样，key为final，是不可变的，value和next通过volatile修饰保证内存可见性。
 *      Index封装了跳表需要的结构，首先node包装了链表的节点，down指向下一层的节点（不是Node，而是Index），right指向同层右边的节点。
 *      node和down都是final的，说明跳表的节点一旦创建，其中的值以及所处的层就不会发生变化
 *      （因为down不会变化，所以其下层的down都不会变化，那他的层显然不会变化）。

 * 【Skip list】是一个"空间来换取时间"的算法：
 *      1. 最底层（level1）是已排序的完整链表结构;
 *      2. level1上元素以0-1随机数决定是否攀升到level2，同时level2上每个节点中增加了向前的指针；
 *      3. level2上元素继续进行随机攀升到level3，并且level3上每个节点中增加了向前的指针。
 *
 *  @author nouuid
 * @description
 * @date 5/11/17
 */
public class C14_ConcurrentSkipListMap {
}
