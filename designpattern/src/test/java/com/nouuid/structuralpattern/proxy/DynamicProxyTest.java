package com.nouuid.structuralpattern.proxy;

import com.nouuid.structuralpattern.proxy.dynamic.Business;
import com.nouuid.structuralpattern.proxy.dynamic.BusinessImpl;
import com.nouuid.structuralpattern.proxy.dynamic.BusinessImplProxy;
import org.junit.Test;

/**
 * Created by nouuid on 2015/5/12.
 */
public class DynamicProxyTest {

    @Test
    public void test() {
        BusinessImpl bfoo = new BusinessImpl();
        Business bf = (Business) BusinessImplProxy.factory(bfoo);
        bf.foo();
        bf.bar("hello world!");
        System.out.println();
    }
}
