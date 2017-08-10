package com.jengine.common2.j2se.concurrent;

import org.junit.Before;
import org.junit.After;

/**
 * @author nouuid
 * @date 12/7/2016
 */
public abstract class ConcurrentTest {
    @Before
    public void before() {
        System.out.println("start");
    }

    @After
    public void after() {
        System.out.println("end");
    }
}
