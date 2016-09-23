package com.jengine.j2se.concurrent.timer;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author nouuid
 * @date 4/8/2016
 * @description
 */
public class TimerRunner {

    /**
     * Task will be excuted at specified time
     */
    public void scheduleDateTest() {
        Timer timer = new Timer();
        TimerRunnerTask timerRunnerTask = new TimerRunnerTask();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 5);
        System.out.println("before schedule. time = " + new Date());
        timer.schedule(timerRunnerTask, calendar.getTime());
    }

    /**
     * Timer will be activated immediately if the scheduled time has passed
     * @throws InterruptedException
     */
    public void scheduleDateTest2() throws InterruptedException {
        Timer timer = new Timer();
        TimerRunnerTask timerRunnerTask = new TimerRunnerTask();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 2);
        Thread.sleep(2*1000);
        System.out.println("before schedule. time = " + new Date());
        timer.schedule(timerRunnerTask, calendar.getTime());
    }

    /**
     *  make Timer to be a daemon thread
     */
    public void scheduleDateTest3() {
        Timer timer = new Timer(true);
        TimerRunnerTask timerRunnerTask = new TimerRunnerTask();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 5);
        System.out.println("before schedule. time = " + new Date());
        timer.schedule(timerRunnerTask, calendar.getTime());
    }

    /**
     * Timer enable multi TimerTasks.
     * All TimerTasks are arranged in a queue based on excution time.
     * All TimerTasks are excuted one after another.
     * The latter TimerTask in the will be delayed by the former TimerTask.
     */
    public void scheduleDateTest4() {
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
    }


    public void scheduleDateTest5() {
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
    }

    /**
     * loop schedule
     */
    public void loopScheduleDateTest1() {
        Timer timer = new Timer();
        TimerRunnerTask timerRunnerTask = new TimerRunnerTask();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 5);
        System.out.println("before schedule. time = " + new Date());
        timer.schedule(timerRunnerTask, calendar.getTime(), 5000);
    }

    /**
     * cancel specified TimerTask
     */
    public void timerTaskCancelTest() {
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
    }

    /**
     * cancel all TimerTask releated to specified Timer
     * @throws InterruptedException
     */
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
    }

    public void scheduleLongTest() {
        Timer timer = new Timer();
        TimerRunnerTask timerRunnerTask = new TimerRunnerTask();
        System.out.println("before schedule. time = " + new Date());
        timer.schedule(timerRunnerTask, 5000);
    }

    public void loopScheduleLongTest() {
        Timer timer = new Timer();
        TimerRunnerTask timerRunnerTask = new TimerRunnerTask();
        System.out.println("before schedule. time = " + new Date());
        timer.schedule(timerRunnerTask, 5000, 1000);
    }

    public void scheduleTaskNoDelayTest() {
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
    }

    public void scheduleTaskDelayTest() {
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
    }

    public void scheduleAtFixedRateTaskNoDelayTest() {
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
    }

    public void scheduleAtFixedRateTaskDelayTest() {
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

