package com.jengine.common2.j2se.concurrent.timer;

import org.junit.Test;

import java.util.*;

/**
 * 1. Timer用于定时执行任务，原理：
 *
 * 2. 任务需要继承TimerTask。
 *
 * @author nouuid
 * @date 4/8/2016
 * @description
 */
public class TimerDemo {

    // 1. Timer用于定时执行任务
    @Test
    public void scheduleDateTest() throws InterruptedException {
        Timer timer = new Timer();
        TimerRunnerTask timerRunnerTask = new TimerRunnerTask();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 5);
        System.out.println("before schedule. time = " + new Date());
        timer.schedule(timerRunnerTask, calendar.getTime());

        Thread.sleep(30*1000);
    }

    /**
     * Timer will be activated immediately if the scheduled time has passed
     * @throws InterruptedException
     */
    @Test
    public void scheduleDateTest2() throws InterruptedException {
        Timer timer = new Timer();
        TimerRunnerTask timerRunnerTask = new TimerRunnerTask();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 2);
        Thread.sleep(2*1000);
        System.out.println("before schedule. time = " + new Date());
        timer.schedule(timerRunnerTask, calendar.getTime());

        Thread.sleep(30*1000);
    }

    /**
     *  make Timer to be a daemon thread
     */
    @Test
    public void scheduleDateTest3() throws InterruptedException {
        Timer timer = new Timer(true);
        TimerRunnerTask timerRunnerTask = new TimerRunnerTask();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 5);
        System.out.println("before schedule. time = " + new Date());
        timer.schedule(timerRunnerTask, calendar.getTime());

        Thread.sleep(30*1000);
    }

    /**
     * Timer enable multi TimerTasks.
     * All TimerTasks are arranged in a queue based on excution time.
     * All TimerTasks are excuted one after another.
     * The latter TimerTask in the will be delayed by the former TimerTask.
     */
    @Test
    public void scheduleDateTest4() throws InterruptedException {
        Timer timer = new Timer();
        TimerRunnerTask2 timerRunnerTask2 = new TimerRunnerTask2();
        TimerRunnerTask3 timerRunnerTask3 = new TimerRunnerTask3();

        Calendar calendar2 = Calendar.getInstance();
        Calendar calendar3 = Calendar.getInstance();

        calendar2.add(Calendar.SECOND, 5);
        calendar3.add(Calendar.SECOND, 10);

        System.out.println("before schedule. time = " + new Date());
        timer.schedule(timerRunnerTask2, calendar2.getTime());
        timer.schedule(timerRunnerTask3, calendar3.getTime());

        Thread.sleep(30*1000);
    }

    @Test
    public void scheduleDateTest5() throws InterruptedException {
        Timer timer = new Timer();
        TimerRunnerTask2 timerRunnerTask2 = new TimerRunnerTask2();
        TimerRunnerTask3 timerRunnerTask3 = new TimerRunnerTask3();

        Calendar calendar2 = Calendar.getInstance();
        Calendar calendar3 = Calendar.getInstance();

        calendar2.add(Calendar.SECOND, 10);
        calendar3.add(Calendar.SECOND, 2);

        System.out.println("before schedule. time = " + new Date());
        timer.schedule(timerRunnerTask2, calendar2.getTime());
        timer.schedule(timerRunnerTask3, calendar3.getTime());

        Thread.sleep(30*1000);
    }

    /**
     * loop schedule
     */
    @Test
    public void loopScheduleDateTest1() throws InterruptedException {
        Timer timer = new Timer();
        TimerRunnerTask timerRunnerTask = new TimerRunnerTask();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 5);
        System.out.println("before schedule. time = " + new Date());
        timer.schedule(timerRunnerTask, calendar.getTime(), 5000);

        Thread.sleep(30*1000);
    }

    /**
     * cancel specified TimerTask
     */
    @Test
    public void timerTaskCancelTest() throws InterruptedException {
        Timer timer = new Timer();
        TimerRunnerTask timerRunnerTask = new TimerRunnerTask();
        TimerRunnerTask4 timerRunnerTask4 = new TimerRunnerTask4();

        Calendar calendar = Calendar.getInstance();
        Calendar calendar4 = Calendar.getInstance();

        calendar.add(Calendar.SECOND, 2);
        calendar4.add(Calendar.SECOND, 4);

        System.out.println("before schedule. time = " + new Date());
        timer.schedule(timerRunnerTask, calendar.getTime(), 2000);
        timer.schedule(timerRunnerTask4, calendar4.getTime(), 2000);

        Thread.sleep(30*1000);
    }

    /**
     * cancel all TimerTask releated to specified Timer
     * @throws InterruptedException
     */
    @Test
    public void timerCancelTest() throws InterruptedException {
        Timer timer = new Timer();
        TimerRunnerTask timerRunnerTask = new TimerRunnerTask();
        TimerRunnerTask4 timerRunnerTask4 = new TimerRunnerTask4();

        Calendar calendar = Calendar.getInstance();
        Calendar calendar4 = Calendar.getInstance();

        calendar.add(Calendar.SECOND, 2);
        calendar4.add(Calendar.SECOND, 4);

        System.out.println("before schedule. time = " + new Date());
        timer.schedule(timerRunnerTask, calendar.getTime(), 2000);
        timer.schedule(timerRunnerTask4, calendar4.getTime(), 2000);

        System.out.println("before cancel");
        Thread.sleep(10 * 1000);
        timer.cancel();
        System.out.println("after cancel");

        Thread.sleep(30*1000);
    }

    @Test
    public void scheduleLongTest() throws InterruptedException {
        Timer timer = new Timer();
        TimerRunnerTask timerRunnerTask = new TimerRunnerTask();
        System.out.println("before schedule. time = " + new Date());
        timer.schedule(timerRunnerTask, 5000);

        Thread.sleep(30*1000);
    }

    @Test
    public void loopScheduleLongTest() throws InterruptedException {
        Timer timer = new Timer();
        TimerRunnerTask timerRunnerTask = new TimerRunnerTask();
        System.out.println("before schedule. time = " + new Date());
        timer.schedule(timerRunnerTask, 5000, 1000);

        Thread.sleep(30*1000);
    }

    @Test
    public void scheduleTaskNoDelayTest() throws InterruptedException {
        Timer timer = new Timer();
        TimerRunnerTask5 timerRunnerTask5 = new TimerRunnerTask5();
        TimerRunnerTask6 timerRunnerTask6 = new TimerRunnerTask6();
        System.out.println("before schedule. time = " + new Date());
        Calendar calendar5 = Calendar.getInstance();
        calendar5.add(Calendar.SECOND, 2);
        Calendar calendar6 = Calendar.getInstance();
        calendar6.add(Calendar.SECOND, 5);
        timer.schedule(timerRunnerTask5, calendar5.getTime(), 1*1000);
        timer.schedule(timerRunnerTask6, calendar6.getTime(), 10*1000);

        Thread.sleep(30*1000);
    }

    @Test
    public void scheduleTaskDelayTest() throws InterruptedException {
        Timer timer = new Timer();
        TimerRunnerTask5 timerRunnerTask5 = new TimerRunnerTask5();
        TimerRunnerTask6 timerRunnerTask6 = new TimerRunnerTask6();
        System.out.println("before schedule. time = " + new Date());
        Calendar calendar5 = Calendar.getInstance();
        calendar5.add(Calendar.SECOND, 5);
        Calendar calendar6 = Calendar.getInstance();
        calendar6.add(Calendar.SECOND, 2);
        timer.schedule(timerRunnerTask5, calendar5.getTime(), 1*1000);
        timer.schedule(timerRunnerTask6, calendar6.getTime(), 10*1000);

        Thread.sleep(30*1000);
    }

    @Test
    public void scheduleAtFixedRateTaskNoDelayTest() throws InterruptedException {
        Timer timer = new Timer();
        TimerRunnerTask5 timerRunnerTask5 = new TimerRunnerTask5();
        TimerRunnerTask6 timerRunnerTask6 = new TimerRunnerTask6();
        System.out.println("before schedule. time = " + new Date());
        Calendar calendar5 = Calendar.getInstance();
        calendar5.add(Calendar.SECOND, 2);
        Calendar calendar6 = Calendar.getInstance();
        calendar6.add(Calendar.SECOND, 5);
        timer.scheduleAtFixedRate(timerRunnerTask5, calendar5.getTime(), 1*1000);
        timer.scheduleAtFixedRate(timerRunnerTask6, calendar6.getTime(), 10*1000);

        Thread.sleep(30*1000);
    }

    @Test
    public void scheduleAtFixedRateTaskDelayTest() throws InterruptedException {
        Timer timer = new Timer();
        TimerRunnerTask5 timerRunnerTask5 = new TimerRunnerTask5();
        TimerRunnerTask6 timerRunnerTask6 = new TimerRunnerTask6();
        System.out.println("before schedule. time = " + new Date());
        Calendar calendar5 = Calendar.getInstance();
        calendar5.add(Calendar.SECOND, 5);
        Calendar calendar6 = Calendar.getInstance();
        calendar6.add(Calendar.SECOND, 2);
        timer.scheduleAtFixedRate(timerRunnerTask5, calendar5.getTime(), 1*1000);
        timer.scheduleAtFixedRate(timerRunnerTask6, calendar6.getTime(), 10*1000);

        Thread.sleep(30*1000);
    }

    /**
     * timer的cancel不行停止正在运行的线程
     * @throws InterruptedException
     */
    @Test
    public void test1() throws InterruptedException {
        List<Thread> tmp = new ArrayList<Thread>();
        System.out.println("start:" + System.currentTimeMillis());
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("close1:" + System.currentTimeMillis());
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tmp.add(Thread.currentThread());
                System.out.println("close3:" + System.currentTimeMillis());
                this.cancel();
            }
        }, 5000);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("close2:" + System.currentTimeMillis());
                this.cancel();
            }
        }, 10000);
        Thread.sleep(10000);
        System.out.println("finish:" + System.currentTimeMillis());
        timer.cancel();
        tmp.get(0).stop();

        Thread.sleep(60*1000);
        System.out.println("end");
    }

    /**
     * timer cancel两次
     */
    @Test
    public void test2() throws InterruptedException {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("start1");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("end1");
            }
        }, 10);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("start2");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("end2");
            }
        }, 20);

        Thread.sleep(30);
        timer.cancel();
        System.out.println("cancel1");
        Thread.sleep(1000);
        timer.cancel();
        System.out.println("cancel2");

        Thread.sleep(6000);
    }
}

class TimerRunnerTask extends TimerTask {

    @Override
    public void run() {
        System.out.println("TimerRunnerTask, run, time = " + new Date());
    }
}

class TimerRunnerTask2 extends TimerTask {

    @Override
    public void run() {
        System.out.println("TimerRunnerTask2, run, time = " + new Date());
        try {
            Thread.sleep(10*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class TimerRunnerTask3 extends TimerTask {

    @Override
    public void run() {
        System.out.println("TimerRunnerTask3, run, time = " + new Date());
        try {
            Thread.sleep(10*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class TimerRunnerTask4 extends TimerTask {

    @Override
    public void run() {
        System.out.println("TimerRunnerTask4, run, time = " + new Date());
        this.cancel();
    }
}

class TimerRunnerTask5 extends TimerTask {

    @Override
    public void run() {
        System.out.println("5, start, time = " + new Date());
        try {
            Thread.sleep(1*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("5,   end, time = " + new Date());
    }
}

class TimerRunnerTask6 extends TimerTask {

    @Override
    public void run() {
        System.out.println("6, start, time = " + new Date());
        try {
            Thread.sleep(5*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("6,   end, time = " + new Date());
    }
}

