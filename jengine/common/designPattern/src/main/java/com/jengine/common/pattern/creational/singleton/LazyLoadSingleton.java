package com.jengine.common.pattern.creational.singleton;

/**
 * @author nouuid
 * @date 4/15/2016
 * @description
 * thread nosafe
 */
public class LazyLoadSingleton {
    private static LazyLoadSingleton lazyLoadSingleton;

    private LazyLoadSingleton() {

    }

    public static LazyLoadSingleton getInstance() {
        try {
            if (lazyLoadSingleton ==null) {
                Thread.sleep(1000);
                lazyLoadSingleton = new LazyLoadSingleton();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return lazyLoadSingleton;
    }
}