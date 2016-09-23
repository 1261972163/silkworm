package com.jengine.j2se.concurrent.pattern;

import java.util.concurrent.*;

/**
 * @author nouuid
 * @date 6/28/2016
 * @description
 */
public class FutureRunner {

    public void futureTest() throws Exception {
        FutureTask futureTask = new FutureTask(new CallableTask("test"));
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.submit(futureTask);
        System.out.println("------------- Request over!");
        Thread.sleep(2*1000);
        System.out.println("------------- Process over!");

        Thread.sleep(10*1000);
    }

    class CallableTask implements Callable<String> {

        private String name;

        public CallableTask(String name) {
            this.name = name;
        }

        @Override
        public String call() throws Exception {
            System.out.println("---------------- real processor");
            name = name + "123";
            System.out.println("---------------- real processor sleep");
            Thread.sleep(5*1000);
            System.out.println("---------------- real processor sleep over");
            return name;
        }
    }
}
