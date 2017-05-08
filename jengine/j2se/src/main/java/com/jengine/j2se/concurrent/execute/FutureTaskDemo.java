package com.jengine.j2se.concurrent.execute;

import java.util.concurrent.*;

/**
 *
 * FutureTask是一个异步执行策略
 *
 * @author nouuid
 * @description
 * @date 5/6/17
 */
public class FutureTaskDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // 1. FutureTask是可执行的Future，传入Callable实现
        FutureTask futureTask = new FutureTask(new Callable() {
            @Override
            public Object call() throws Exception {
                System.out.println("---1");
                Thread.sleep(5000);
                System.out.println("---3");
                return "CALLBACK";
            }
        });
        // 2. 放入Thread执行
        Thread thread = new Thread(futureTask);
        thread.start();

        Thread.sleep(1000);
        System.out.println("---2");
        Thread.sleep(1000);
        // 3. FutureTask.get()是阻塞式的
        // 4. 读取的值是Object，需要强制转型
        long start = System.currentTimeMillis();
        String res = (String)(futureTask.get());
        long end = System.currentTimeMillis();
        System.out.println("cost: " + (end-start));
        System.out.println("res:" + res);
        Thread.sleep(10 * 1000);
        System.out.println("END");
    }
}
