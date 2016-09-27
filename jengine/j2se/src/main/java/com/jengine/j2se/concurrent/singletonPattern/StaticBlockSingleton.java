package com.jengine.j2se.concurrent.singletonPattern;

/**
 * @author nouuid
 * @date 4/15/2016
 * @description
 * thread safe
 */
public class StaticBlockSingleton {
    private static StaticBlockSingleton staticInnerClassSingleton = null;

    private StaticBlockSingleton() {

    }

    static {
        staticInnerClassSingleton = new StaticBlockSingleton();
    }

    public static StaticBlockSingleton getInstance() {
        return staticInnerClassSingleton;
    }
}