package com.jengine.common2.j2se.concurrent.collection;

import org.junit.Test;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * DelayQueue源自jdk1.5，是一个支持延时获取元素的无界阻塞队列。
 * 1. 队列使用PriorityQueue来实现，优先队列的比较基准值是时间。
 * 2. 队列中的元素必须实现Delayed接口，Delayed扩展了Comparable接口，
 *      比较的基准为延时的时间值，Delayed接口的实现类getDelay的返回值应为固定值（final）。
 *      在创建元素时可以指定多久才能从队列中获取当前元素。
 *      只有在延迟期满时才能从队列中提取元素。
 * 3. 具体实现原理为：
 *      DelayQueue = BlockingQueue + PriorityQueue + Delayed
 *      当调用DelayQueue的offer方法时，把Delayed对象加入到优先队列中。
 *      DelayQueue的take方法，把优先队列的first拿出来（peek），如果没有达到延时阀值，则进行await处理。
 * 4. 我们可以将DelayQueue运用在以下应用场景：
 *      (1)缓存系统的设计：可以用DelayQueue保存缓存元素的有效期，使用一个线程循环查询DelayQueue，一旦能从DelayQueue中获取元素时，表示缓存有效期到了。
 *      (2)定时任务调度。使用DelayQueue保存当天将会执行的任务和执行时间，一旦从DelayQueue中获取到任务就开始执行，从比如TimerQueue就是使用DelayQueue实现的。
 * @author nouuid
 * @description
 * @date 5/11/17
 */
public class C04_DelayQueue {

    @Test
    public void test() throws InterruptedException {
        class DelayObject implements Delayed {
            private int delay;
            private long time;

            public DelayObject(int delay) {
                this.delay = delay;
                //计算延迟后的时间
                time = System.nanoTime() + TimeUnit.NANOSECONDS.convert(this.delay, TimeUnit.SECONDS);
            }

            public long getTime() {
                return time;
            }

            @Override
            public long getDelay(TimeUnit unit) {
                // 计算还剩余的延迟时间
                return unit.convert(time - System.nanoTime(), TimeUnit.NANOSECONDS);
            }

            @Override
            public int compareTo(Delayed o) {
                long result = ((DelayObject) o).getTime() - this.getTime();
                if (result > 0) {
                    return 1;
                }
                if (result < 0) {
                    return -1;
                }
                return 0;
            }
        }

        DelayQueue<DelayObject> delayQueue = new DelayQueue<DelayObject>();
        delayQueue.add(new DelayObject(3));
        long start = System.currentTimeMillis();
        // 没有到达延迟期满，获取不到
        // 3s后，延迟期满，获取到
        delayQueue.take();
        long end = System.currentTimeMillis();
        // 计算时间为3s
        System.out.println(end-start);
    }
}
