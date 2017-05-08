package com.jengine.j2se.concurrent.execute;

import junit.framework.Assert;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * 1. ThreadPoolExecutor 线程池
 * 2. shutdown后，不接受新任务，已有的任务结束后，才变为终止状态
 * 3. shutdownNow后，不接受新任务，清除所有未执行的任务，并且在运行线程上调用interrupt()。
 * 4. ExecutorService executorService = Executors.newSingleThreadExecutor()  单线程
 * 5. ExecutorService executorService = Executors.newFixedThreadPool(n)      n线程并行
 * 6. ExecutorService executorService = Executors.newCachedThreadPool();     根据需要创建新线程的线程池
 * 7. ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(n); n线程定时执行线程池
 *
 * @author nouuid
 * @description
 * @date 5/7/17
 */
public class ThreadPoolExecutorDemo {

    // 1. ThreadPoolExecutor 线程池
    @Test
    public void threadPoolExecutor() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
        int i = 1;
        while (i<=5) {
            int out = i++;
            threadPoolExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    int j = 0;
                    while (j < 10) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(out);
                        j++;
                    }

                }
            });
        }
        Thread.sleep(10000);
    }

    // 2. shutdown要等全部计划任务执行完后才完全退出
    // shutdown变为关闭状态，拒绝接受新的任务
    // 如果还有任务，shutdown后并没有进入终止状态
    // 任务全部执行结束后，shutdown变为终止状态
    @Test
    public void shutdown() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        int i = 1;
        while (i<=5) {
            int out = i++;
            threadPoolExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(out);
                }
            });
        }
        Thread.sleep(500);
        threadPoolExecutor.shutdown();
        try {
            threadPoolExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("6");
                }
            });
        } catch (Exception e) {
            if (e instanceof RejectedExecutionException) {
                Assert.assertTrue(true);
            }
        }
        Assert.assertEquals(true, threadPoolExecutor.isShutdown());
        Assert.assertEquals(false, threadPoolExecutor.isTerminated());
        Thread.sleep(3000);
        Assert.assertEquals(true, threadPoolExecutor.isShutdown());
        Assert.assertEquals(true, threadPoolExecutor.isTerminated());
    }

    // 3. shutdownNow后，不接受新任务，清除所有未执行的任务，并且在运行线程上调用interrupt()。
    @Test
    public void shutdownNow() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
        int i = 1;
        while (i<=5) {
            int out = i++;
            threadPoolExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(out);
                }
            });
        }
        Thread.sleep(500);
        threadPoolExecutor.shutdownNow();
        try {
            threadPoolExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("6");
                }
            });
        } catch (Exception e) {
            if (e instanceof RejectedExecutionException) {
                Assert.assertTrue(true);
            }
        }
        Assert.assertEquals(true, threadPoolExecutor.isShutdown());
        Assert.assertEquals(true, threadPoolExecutor.isTerminated());
        Thread.sleep(3000);
    }

    // 4. Executors.newSingleThreadExecutor()单线程
    @Test
    public void newSingleThreadExecutor() throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                int i = 1;
                while (i<10) {
                    System.out.println("1");
                    i++;
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Thread.sleep(100);
        executorService.shutdown();
        Thread.sleep(3000);
    }

    // 5. Executors.newFixedThreadPool(n) n线程并行
    // 新加入的任务先占用线程，占用完线程后，后加入的线程要等前面的任务完成后，才能获取线程
    @Test
    public void newFixedThreadPool() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        int i = 1;
        while (i<=5) {
            int out = i++;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    int j = 0;
                    while (j<10) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(out);
                        j++;
                    }

                }
            });
        }
        Thread.sleep(10000);
    }

    // 6. Executors.newCachedThreadPool() 根据需要创建新线程的线程池
    @Test
    public void newCachedThreadPool() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        int i = 1;
        while (i<=5) {
            int out = i++;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    int j = 0;
                    while (j<10) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(out);
                        j++;
                    }

                }
            });
        }
        Thread.sleep(10000);
    }


}
