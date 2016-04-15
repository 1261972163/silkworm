package com.jengine.feature.concurrent.singletonPattern;

/**
 * @author bl07637
 * @date 4/15/2016
 * @description
 * thread safe
 */
public class HungryLoadSingleton {
    private static HungryLoadSingleton hungryLoadSingleton = new HungryLoadSingleton();

    private HungryLoadSingleton() {

    }

    public static HungryLoadSingleton getInstance() {
        return hungryLoadSingleton;
    }
}
