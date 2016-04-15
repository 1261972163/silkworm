package com.jengine.feature.concurrent.singletonPattern;

/**
 * @author bl07637
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
