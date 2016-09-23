package com.jengine.j2se.concurrent.threadCommunication;

import com.jengine.j2se.concurrent.ConcurrentTest;
import com.jengine.j2se.concurrent.threadCommunication.threadLocal.InheritableThreadLocalRunner;
import com.jengine.j2se.concurrent.threadCommunication.threadLocal.ThreadLocalRunner;
import org.junit.Test;

/**
 * @author nouuid
 * @date 4/6/2016
 * @description
 */
public class ThreadLocalTest extends ConcurrentTest {

    @Test
    public void test() throws InterruptedException {
        ThreadLocalRunner threadLocalRunner = new ThreadLocalRunner();
        threadLocalRunner.test();
        Thread.sleep(10*1000);
    }

    @Test
    public void initThreadLocalTest() throws InterruptedException {
        ThreadLocalRunner threadLocalRunner = new ThreadLocalRunner();
        threadLocalRunner.initThreadLocalTest();
        Thread.sleep(10*1000);
    }

    @Test
    public void inheritableThreadLocalTest() throws InterruptedException {
        InheritableThreadLocalRunner inheritableThreadLocalRunner = new InheritableThreadLocalRunner();
        inheritableThreadLocalRunner.inheritableThreadLocalTest();
        Thread.sleep(10*1000);
    }

    @Test
    public void initInheritableThreadLocalTest() throws InterruptedException {
        InheritableThreadLocalRunner threadLocalRunner = new InheritableThreadLocalRunner();
        threadLocalRunner.initInheritableThreadLocalTest();
        Thread.sleep(10*1000);
    }

    @Test
    public void inheritableThreadLocalChangeTest() throws InterruptedException {
        InheritableThreadLocalRunner threadLocalRunner = new InheritableThreadLocalRunner();
        threadLocalRunner.inheritableThreadLocalChangeTest();
        Thread.sleep(10*1000);
    }
}
