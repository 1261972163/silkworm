package com.jengine.j2se.concurrent.concept;

import com.jengine.j2se.concurrent.ConcurrentTest;
import org.junit.Test;

/**
 * @author nouuid
 * @date 4/11/2016
 * @description
 */
public class ThreadMethodTest extends ConcurrentTest {

    @Test
    public void threadRunTest() throws InterruptedException {
        ThreadMethodRunner threadMethodRunner = new ThreadMethodRunner();
        threadMethodRunner.currentThreadMethodTest();
        Thread.sleep(10*1000);
    }

    @Test
    public void isAliveMethodTest() throws InterruptedException {
        ThreadMethodRunner threadMethodRunner = new ThreadMethodRunner();
        threadMethodRunner.isAliveMethodTest();
        Thread.sleep(10*1000);
    }

    @Test
    public void getIdMethodTest() throws InterruptedException {
        ThreadMethodRunner threadMethodRunner = new ThreadMethodRunner();
        threadMethodRunner.getIdMethodTest();
        Thread.sleep(10*1000);
    }

    @Test
    public void joinTest() throws InterruptedException {
        ThreadMethodRunner threadMethodRunner = new ThreadMethodRunner();
        threadMethodRunner.joinTest();
        Thread.sleep(30*1000);
    }

    @Test
    public void timedJoinTest() throws InterruptedException {
        ThreadMethodRunner threadMethodRunner = new ThreadMethodRunner();
        threadMethodRunner.timedJoinTest();
        Thread.sleep(30*1000);
    }

    @Test
    public void yieldTest() throws InterruptedException {
        ThreadMethodRunner threadMethodRunner = new ThreadMethodRunner();
        threadMethodRunner.yieldTest();
        Thread.sleep(30*1000);
    }

    @Test
    public void priorityTest() throws InterruptedException {
        ThreadMethodRunner threadMethodRunner = new ThreadMethodRunner();
        threadMethodRunner.priorityTest();
        Thread.sleep(30*1000);
    }

    /**
     * daemonTest
     */
    public static void main(String[] args) throws InterruptedException {
        ThreadMethodRunner threadMethodRunner = new ThreadMethodRunner();
        threadMethodRunner.daemonTest();
    }


}
