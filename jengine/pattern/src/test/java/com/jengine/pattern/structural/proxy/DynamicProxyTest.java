package com.jengine.pattern.structural.proxy;

import com.jengine.pattern.structural.proxy.dynamic.Business;
import com.jengine.pattern.structural.proxy.statical.BusinessImpl;
import org.junit.Test;

/**
 * Created by nouuid on 2015/5/12.
 */
public class DynamicProxyTest {

    @Test
    public void test() {
        BusinessImpl bfoo = new BusinessImpl();
        Business bf = (Business) com.jengine.pattern.structural.proxy.dynamic.BusinessImplProxy.factory(bfoo);
        bf.foo();
        bf.bar("hello world!");
        System.out.println();
    }
}
