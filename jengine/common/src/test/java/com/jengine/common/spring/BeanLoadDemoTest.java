package com.jengine.common.spring;

import org.junit.Test;

/**
 * @author nouuid
 * @date 8/19/2016
 * @description
 */
public class BeanLoadDemoTest {

    private Spring_2_container beanLoadDemo = new Spring_2_container();

    @Test
    public void load() throws InterruptedException {
        beanLoadDemo.load();
        Thread.sleep(30 * 1000);
    }
}
