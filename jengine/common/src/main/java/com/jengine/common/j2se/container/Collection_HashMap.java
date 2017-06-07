package com.jengine.common.j2se.container;

import java.util.HashMap;

/**
 * [Java 8系列之重新认识HashMap](http://tech.meituan.com/java-hashmap.html)
 * [Java HashMap死循环分析](http://yangbolin.cn/2014/05/02/hash-map-infinite-loop/)
 *
 * @author bl07637
 * @date 5/19/2017
 * @since 0.1.0
 */
public class Collection_HashMap {
    public void hashmap() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("","");
    }
}
