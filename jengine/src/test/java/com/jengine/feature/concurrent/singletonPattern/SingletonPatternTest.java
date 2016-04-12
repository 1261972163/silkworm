package com.jengine.feature.concurrent.singletonPattern;

import com.jengine.feature.concurrent.ConcurrentTest;
import org.junit.Test;

/**
 * Created by nouuid on 4/9/16.
 */
public class SingletonPatternTest extends ConcurrentTest{

    @Test
    public void hungryLoadTest() throws InterruptedException {
        HungryLoadRunner hungryLoadRunner = new HungryLoadRunner();
        hungryLoadRunner.test();
        Thread.sleep(10*1000);
    }

    @Test
    public void lazyLoadTest() throws InterruptedException {
        LazyLoadRunner lazyLoadRunner = new LazyLoadRunner();
        lazyLoadRunner.test();
        Thread.sleep(10*1000);
    }

    @Test
    public void lazyLoadTest2() throws InterruptedException {
        LazyLoadRunner lazyLoadRunner = new LazyLoadRunner();
        lazyLoadRunner.test2();
        Thread.sleep(10*1000);
    }

}
