package com.jengine.common2.guava08;

/**
 * 超级强大的 Map 构造工具
 *
 * @author nouuid
 * @date 9/30/2016
 * @since 0.1.0
 */
public class MapMakerDemo {

    // r08 API
    public void demo1() {
//        //ConcurrentHashMap with concurrency level 8
//        ConcurrentMap<String, Object> map1 = new MapMaker()
//                .concurrencyLevel(8)
//                .makeMap();
//        //ConcurrentMap with soft reference key and weak reference value
//        ConcurrentMap<String, Object> map2 = new MapMaker()
//                .softKeys()
//                .weakValues()
//                .makeMap();
//        //Automatically removed entries from map after 30 seconds since they are created
//        ConcurrentMap<String, Object> map3 = new MapMaker()
//                .expireAfterWrite(30, TimeUnit.SECONDS)
//                .makeMap();
//        //Map size grows close to the 100, the map will evict
//        //entries that are less likely to be used again
//        ConcurrentMap<String, Object> map4 = new MapMaker()
//                .maximumSize(100)
//                .makeMap();
//        //Automatically removed entries from map after 30 seconds since they are created
//        ConcurrentMap<Long, String> map5 = new MapMaker()
//                .makeComputingMap(new Function<Long, String>() {
//                    public String apply(Long key) {
//                        return Long.toString(key);
//                    }
//                });
//        //最厉害的是 MapMaker 可以提供拥有以上所有特性的 Map
//        ConcurrentMap<Long, String> mapAll = new MapMaker()
//                .concurrencyLevel(8)
//                .softKeys()
//                .weakValues()
//                .expireAfterWrite(30, TimeUnit.SECONDS)
//                .maximumSize(100)
//                .makeComputingMap(
//                        new Function<Long, String>() {
//                            public String apply(Long key) {
//                                return createObject(key);
//                            }
//                        });

    }

    private String createObject(Long key) {
        return Long.toString(key);
    }


}
