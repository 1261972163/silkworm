package com.jengine.feature.concurrent.timer;

import com.jengine.feature.concurrent.ConcurrentTest;
import org.junit.Test;

/**
 * @author bl07637
 * @date 4/8/2016
 * @description
 */
public class TimerTest extends ConcurrentTest {

    @Test
    public void scheduleTest() throws InterruptedException {
        TimerRunner timerRunner = new TimerRunner();
        timerRunner.scheduleDateTest();
        Thread.sleep(30*1000);
    }

    @Test
    public void scheduleTest2() throws InterruptedException {
        TimerRunner timerRunner = new TimerRunner();
        timerRunner.scheduleDateTest2();
        Thread.sleep(30*1000);
    }

    @Test
    public void scheduleTest3() throws InterruptedException {
        TimerRunner timerRunner = new TimerRunner();
        timerRunner.scheduleDateTest3();
        Thread.sleep(30*1000);
    }

    @Test
    public void scheduleTest4() throws InterruptedException {
        TimerRunner timerRunner = new TimerRunner();
        timerRunner.scheduleDateTest4();
        Thread.sleep(60*1000);
    }

    @Test
    public void scheduleTest5() throws InterruptedException {
        TimerRunner timerRunner = new TimerRunner();
        timerRunner.scheduleDateTest5();
        Thread.sleep(60*1000);
    }

    @Test
    public void loopScheduleTest1() throws InterruptedException {
        TimerRunner timerRunner = new TimerRunner();
        timerRunner.loopScheduleDateTest1();
        Thread.sleep(60*1000);
    }

    @Test
    public void timerTaskCancelTest() throws InterruptedException {
        TimerRunner timerRunner = new TimerRunner();
        timerRunner.timerTaskCancelTest();
        Thread.sleep(60*1000);
    }

    @Test
    public void timerCancelTest() throws InterruptedException {
        TimerRunner timerRunner = new TimerRunner();
        timerRunner.timerCancelTest();
        Thread.sleep(60*1000);
    }

    @Test
    public void scheduleLongTest() throws InterruptedException {
        TimerRunner timerRunner = new TimerRunner();
        timerRunner.scheduleLongTest();
        Thread.sleep(10*1000);
    }

    @Test
    public void loopScheduleLongTest() throws InterruptedException {
        TimerRunner timerRunner = new TimerRunner();
        timerRunner.loopScheduleLongTest();
        Thread.sleep(30*1000);
    }

    @Test
    public void scheduleTaskNoDelayTest() throws InterruptedException {
        TimerRunner timerRunner = new TimerRunner();
        timerRunner.scheduleTaskNoDelayTest();
        Thread.sleep(30*1000);
    }

    @Test
    public void scheduleTaskDelayTest() throws InterruptedException {
        TimerRunner timerRunner = new TimerRunner();
        timerRunner.scheduleTaskDelayTest();
        Thread.sleep(30*1000);
    }

    @Test
    public void scheduleAtFixedRateTaskNoDelayTest() throws InterruptedException {
        TimerRunner timerRunner = new TimerRunner();
        timerRunner.scheduleAtFixedRateTaskNoDelayTest();
        Thread.sleep(30*1000);
    }

    @Test
    public void scheduleAtFixedRateTaskDelayTest() throws InterruptedException {
        TimerRunner timerRunner = new TimerRunner();
        timerRunner.scheduleAtFixedRateTaskDelayTest();
        Thread.sleep(30*1000);
    }
}

