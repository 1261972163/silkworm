package com.jengine.j2se.concurrent.threadCommunication;

import com.jengine.j2se.concurrent.ConcurrentTest;
import com.jengine.j2se.concurrent.threadCommunication.join.CompareTimedJoinTimedSleepRunner;
import com.jengine.j2se.concurrent.threadCommunication.join.JoinRunner;
import com.jengine.j2se.concurrent.threadCommunication.join.TimedJoinRunner;
import org.junit.Test;

/**
 * @author nouuid
 * @date 4/5/2016
 * @description
 */
public class JoinTest extends ConcurrentTest {

    @Test
    public void test() throws Exception{
        JoinRunner joinRunner = new JoinRunner();
        joinRunner.test();
        Thread.sleep(10*1000);
    }

    @Test
    public void test2() throws Exception{
        JoinRunner joinRunner = new JoinRunner();
        joinRunner.test2();
//        Thread.sleep(30*60*1000);
    }

    @Test
    public void timedJoin() throws Exception {
        TimedJoinRunner timedJoinRunner = new TimedJoinRunner();
        timedJoinRunner.timedJoin();
    }

    @Test
    public void sleepTest() throws Exception {
        CompareTimedJoinTimedSleepRunner compareTimedJoinTimedSleepRunner = new CompareTimedJoinTimedSleepRunner();
        compareTimedJoinTimedSleepRunner.sleepTest();
        Thread.sleep(30*60*1000);
    }

    @Test
    public void joinTest() throws Exception {
        CompareTimedJoinTimedSleepRunner compareTimedJoinTimedSleepRunner = new CompareTimedJoinTimedSleepRunner();
        compareTimedJoinTimedSleepRunner.joinTest();
        Thread.sleep(30*60*1000);
    }
}
