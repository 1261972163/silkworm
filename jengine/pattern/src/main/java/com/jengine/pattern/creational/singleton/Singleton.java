package com.jengine.pattern.creational.singleton;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nouuid on 2015/5/12.
 */
public class Singleton {

    private Map<String, String> info = new HashMap<String, String>();

    public Map<String, String> getInfo() {
        return info;
    }

    public void setInfo(Map<String, String> info) {
        this.info = info;
    }

    private static class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }

    private Singleton() {
    }

    public static final Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
