package com.jengine.common.j2se.lang;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.nio.ByteBuffer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * content
 *
 * @author bl07637
 * @date 8/25/2016
 * @since 0.1.0
 */
public class DirectMemoryTest {
    private static ScheduledExecutorService memoryMonitor = Executors.newScheduledThreadPool(1);


    public static void main(String[] args) throws InterruptedException {


        long unit = 1024 * 1024; // 1M
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        memoryMonitor.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize() / unit;
                System.out.println(freePhysicalMemorySize);
                System.gc();
                freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize() / unit;
                System.out.println(freePhysicalMemorySize);
            }

        }, 1000, 1000, TimeUnit.MILLISECONDS);

        int i = 1;
        while (true) {
            ByteBuffer.allocateDirect(100 * 1024 * 1024);//100M
            Thread.sleep(1000);
            System.out.println("---------------> " + i++);
        }

    }
}
