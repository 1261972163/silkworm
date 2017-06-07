package com.jengine.common.j2se.concurrent.execute;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * 用于并行执行任务的框架， 是一个把大任务分割成若干个小任务，最终汇总每个小任务结果后得到大任务结果的框架。
 * fork将大任务分割成子任务
 * join将子任务执行完的结果都统一放在一个队列里，启动一个线程从队列里拿数据，然后合并这些数据。
 *
 * @author bl07637
 * @date 11/23/2016
 * @since 0.1.0
 */
public class ForkJoinDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 计算 1 + 2 + ... + 20000
        int data[] = new int[20000];
        for(int i=0;i<data.length;i++){
            data[i]= i + 1;
        }

        // 1. 任务ForkJoinTask
        ForkJoinTask forkJoinTask = new CountTask(data, 0, data.length);
        // 5. 将ForkJoinTask放入ForkJoinPool执行，返回Future
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        Future<Long> future = forkJoinPool.submit(forkJoinTask);
        // 6. 从Future获取结果
        long result = future.get();
        System.out.println("result=" + result);
    }

    // 2. ForkJoinTask提供在任务中执行fork()和join()操作的机制
    // 3. RecursiveTask用于有返回结果的任务。
    // 4. RecursiveAction用于没有返回结果的任务.
    static class CountTask extends RecursiveTask<Long> {

        private static final int THRESHOLD = 100;
        private int[] data;
        private int start;
        private int end;

        public CountTask(int[] data, int start, int end) {
            this.data = data;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            Long sumResult = 0L;
            if ((end - start) <= THRESHOLD) {
                for (int index = start; index < end; index++) {
                    sumResult += data[index];
                }
            } else {
                int step = (end - start) / THRESHOLD;
                if (((end - start) % THRESHOLD) > 0)
                    step += 1;
                ArrayList<CountTask> tasks = new ArrayList<>();
                int pos = start;
                int lastposition;
                for (int i = 0; i < step; i++) {
                    lastposition = pos + THRESHOLD;
                    if (lastposition > end)
                        lastposition = end;
                    CountTask onetask = new CountTask(data, pos, lastposition);
                    pos = lastposition;
                    tasks.add(onetask);
                    // 分解
                    onetask.fork();
                }
                for (CountTask mtask : tasks) {
                    // 合并
                    sumResult += mtask.join();
                }
            }
            return sumResult;
        }
    }
}


