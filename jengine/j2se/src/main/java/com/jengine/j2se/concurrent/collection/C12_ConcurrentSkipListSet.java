package com.jengine.j2se.concurrent.collection;

import java.util.concurrent.ConcurrentSkipListSet;

/**
 * ConcurrentSkipListSet源自jdk1.6，
 * 1. 内部持有ConcurrentSkipListMap。
 * 2. Set的数据value都被封装成< value, Boolean.TRUE>放入ConcurrentSkipListMap。
 * 3. 所有操作都是基于对ConcurrentSkipListMap的操作。
 * 4. 需要注意的是value必须是Comparable类型的。
 * @author nouuid
 * @description
 * @date 5/11/17
 */
public class C12_ConcurrentSkipListSet {

    public void test() {
        ConcurrentSkipListSet concurrentSkipListSet = new ConcurrentSkipListSet();
    }
}
