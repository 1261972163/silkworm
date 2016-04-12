package com.jengine.feature.concurrent.concept;

import com.jengine.feature.concurrent.ConcurrentTest;
import org.junit.Test;

/**
 * @author bl07637
 * @date 4/11/2016
 * @description
 */
public class ThreadConceptTest extends ConcurrentTest {

    @Test
    public void threadRunTest() throws InterruptedException {
        ThreadConceptRunner threadConceptRunner = new ThreadConceptRunner();
        threadConceptRunner.threadRunTest();
        Thread.sleep(10*1000);
    }

    @Test
    public void threadTaskRunTest() throws InterruptedException {
        ThreadConceptRunner threadConceptRunner = new ThreadConceptRunner();
        threadConceptRunner.threadTaskRunTest();
        Thread.sleep(10*1000);
    }

    @Test
    public void threadAnonymityTest() throws InterruptedException {
        ThreadConceptRunner threadConceptRunner = new ThreadConceptRunner();
        threadConceptRunner.threadAnonymityTest();
        Thread.sleep(10*1000);
    }
}
