package com.jengine.pattern.creational.singleton;

/**
 * @author nouuid
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
