package com.jengine.feature.concurrent.singletonPattern;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * @author nouuid
 * @date 4/15/2016
 * @description
 * seralizable object has one instance
 */
public class StaticInnerClassSingletonSeralizableOneInstance implements Serializable {

    private static final long serialVersionUID = 888L;

    private static class LazyLoadStaticInnerClassSingletonHandler {
        private static StaticInnerClassSingletonSeralizableOneInstance staticInnerClassSingleton = new StaticInnerClassSingletonSeralizableOneInstance();
    }

    private StaticInnerClassSingletonSeralizableOneInstance() {

    }

    public static StaticInnerClassSingletonSeralizableOneInstance getInstance() {
        return LazyLoadStaticInnerClassSingletonHandler.staticInnerClassSingleton;
    }

    protected Object readResolve() throws ObjectStreamException {
        return LazyLoadStaticInnerClassSingletonHandler.staticInnerClassSingleton;
    }
}
