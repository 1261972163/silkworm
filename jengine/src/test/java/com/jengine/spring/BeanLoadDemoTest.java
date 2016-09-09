package com.jengine.spring;

import org.junit.Test;

/**
 * @author bl07637
 * @date 8/19/2016
 * @description
 */
public class BeanLoadDemoTest {

    private BeanLoadDemo beanLoadDemo = new BeanLoadDemo();

    @Test
    public void load() throws InterruptedException {
        beanLoadDemo.load();
        Thread.sleep(30*1000);
    }
}
