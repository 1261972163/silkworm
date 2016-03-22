package com.nouuid.structuralpattern.proxy;

import org.junit.Test;

/**
 * Created by nouuid on 2015/5/12.
 */
public class StaticalProxyTest {

    @Test
    public void test() {
        Business bi = new BusinessImplProxy();
        bi.doAction();
    }
}
