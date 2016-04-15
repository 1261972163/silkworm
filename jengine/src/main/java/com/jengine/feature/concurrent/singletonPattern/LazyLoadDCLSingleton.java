package com.jengine.feature.concurrent.singletonPattern;

/**
 * @author bl07637
 * @date 4/15/2016
 * @description
 * thread safe
 * DCL: check-lock-check-act
 */
public class LazyLoadDCLSingleton {
    private static LazyLoadDCLSingleton lazyLoadDCLSingleton;

    private LazyLoadDCLSingleton() {

    }

    public static LazyLoadDCLSingleton getInstance() {
        try {
            if (lazyLoadDCLSingleton ==null) {
                Thread.sleep(1000);
                synchronized (LazyLoadDCLSingleton.class) {
                    if (lazyLoadDCLSingleton ==null) {
                        lazyLoadDCLSingleton = new LazyLoadDCLSingleton();
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return lazyLoadDCLSingleton;
    }
}
