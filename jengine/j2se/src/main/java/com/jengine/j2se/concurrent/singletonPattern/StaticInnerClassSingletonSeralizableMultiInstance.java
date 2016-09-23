package com.jengine.j2se.concurrent.singletonPattern;

import java.io.Serializable;

/**
 * @author nouuid
 * @date 4/15/2016
 * @description
 * seralizable object has multi instance
 */
public class StaticInnerClassSingletonSeralizableMultiInstance implements Serializable {

    private static final long serialVersionUID = 888L;

    private static class LazyLoadStaticInnerClassSingletonHandler {
        private static StaticInnerClassSingletonSeralizableMultiInstance staticInnerClassSingleton = new StaticInnerClassSingletonSeralizableMultiInstance();
    }

    private StaticInnerClassSingletonSeralizableMultiInstance() {

    }

    public static StaticInnerClassSingletonSeralizableMultiInstance getInstance() {
        return LazyLoadStaticInnerClassSingletonHandler.staticInnerClassSingleton;
    }
}
