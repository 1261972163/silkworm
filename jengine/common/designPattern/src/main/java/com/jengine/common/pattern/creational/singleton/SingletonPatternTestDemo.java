package com.jengine.common.pattern.creational.singleton;

import org.junit.Test;

/**
 * Created by nouuid on 4/9/16.
 */
public class SingletonPatternTestDemo {
    SingletonRunner lazyLoadRunner = new SingletonRunner();

    @Test
    public void hungryLoadSingletonTest() throws InterruptedException {
        lazyLoadRunner.hungryLoadSingletonTest();
        Thread.sleep(10*1000);
    }

    @Test
    public void LazyLoadSingletonTest() throws InterruptedException {
        lazyLoadRunner.LazyLoadSingletonTest();
        Thread.sleep(10*1000);
    }

    @Test
    public void lazyLoadDCLSingletonTest() throws InterruptedException {
        lazyLoadRunner.lazyLoadDCLSingletonTest();
        Thread.sleep(10*1000);
    }

    @Test
    public void staticInnerClassSingletonSeralizableMultiInstanceTest() throws InterruptedException {
        lazyLoadRunner.staticInnerClassSingletonSeralizableMultiInstanceTest();
        Thread.sleep(30*1000);
    }

    @Test
    public void staticInnerClassSingletonSeralizableOneInstanceTest() throws InterruptedException {
        lazyLoadRunner.staticInnerClassSingletonSeralizableOneInstanceTest();
        Thread.sleep(30*1000);
    }

    @Test
    public void staticBlockSingletonTest() throws InterruptedException {
        lazyLoadRunner.staticBlockSingletonTest();
        Thread.sleep(30 * 1000);
    }

    @Test
    public void enumSingletonTest() throws InterruptedException {
        lazyLoadRunner.enumSingletonTest();
        Thread.sleep(30 * 1000);
    }

}
