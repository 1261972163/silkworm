package com.jengine.feature.concurrent;

import org.junit.After;
import org.junit.Before;

/**
 * @author bl07637
 * @date 3/30/2016
 * @description
 */
public class ConcurrentTest {
    @Before
    public void before() {
        System.out.println("starting...");
    }

    @After
    public void after() {
        System.out.println("finished...");
    }
}
