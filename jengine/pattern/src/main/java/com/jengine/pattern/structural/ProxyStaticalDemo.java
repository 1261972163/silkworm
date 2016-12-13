package com.jengine.pattern.structural;

/**
 * content
 *
 * @author bl07637
 * @date 11/14/2016
 * @since 0.1.0
 */
public class ProxyStaticalDemo {

    public static void main(String[] args) {
        StaticalProxyInterface staticalProxy = new StaticalProxy();
        staticalProxy.doAction();
    }
}

class StaticalProxy implements StaticalProxyInterface {
    private StaticalProxyInterface bi;

    @Override
    public void doAction() {
        if (bi == null) {
            bi = new StaticalProxyService();
        }
        doBefore();
        bi.doAction();
        doAfter();
    }

    public void doBefore() {
        System.out.println("before doAction()");
    }

    public void doAfter() {
        System.out.println("after doAction()");
    }
}

interface StaticalProxyInterface {
    void doAction();
}

class StaticalProxyService implements StaticalProxyInterface {

    @Override
    public void doAction() {
        System.out.println("do real action");
    }
}
