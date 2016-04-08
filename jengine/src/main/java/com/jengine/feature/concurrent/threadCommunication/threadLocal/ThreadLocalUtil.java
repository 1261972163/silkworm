package com.jengine.feature.concurrent.threadCommunication.threadLocal;

import java.util.Date;

/**
 * @author bl07637
 * @date 4/5/2016
 * @description
 */
public class ThreadLocalUtil {
    public static ThreadLocal threadLocal = new ThreadLocal();

    public static ThreadLocalExt threadLocalExt = new ThreadLocalExt();

    public static InheritableThreadLocal inheritableThreadLocal = new InheritableThreadLocal();

    public static InheritableThreadLocalExt inheritableThreadLocalExt = new InheritableThreadLocalExt();
}

class ThreadLocalExt extends ThreadLocal {
    @Override
    protected Object initialValue() {
        return new Date().getTime();
    }
}

class InheritableThreadLocalExt extends InheritableThreadLocal {
    @Override
    protected Object initialValue() {
        return new Date().getTime();
    }

    @Override
    protected Object childValue(Object parentValue) {
        return parentValue + " add";
    }
}
