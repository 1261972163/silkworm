package com.jengine.common.pattern.creational.singleton;

/**
 * @author nouuid
 * @date 4/15/2016
 * @description
 * thread safe
 */
public class StaticInnerClassSingleton {
    private static class LazyLoadStaticInnerClassSingletonHandler {
        private static StaticInnerClassSingleton staticInnerClassSingleton = new StaticInnerClassSingleton();
    }

    private StaticInnerClassSingleton() {

    }

    public static StaticInnerClassSingleton getInstance() {
        return LazyLoadStaticInnerClassSingletonHandler.staticInnerClassSingleton;
    }
}
