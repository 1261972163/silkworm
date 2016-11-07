package com.jengine.pattern.structural.proxy;

import com.jengine.pattern.structural.proxy.statical.Business;
import com.jengine.pattern.structural.proxy.statical.BusinessImplProxy;
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
