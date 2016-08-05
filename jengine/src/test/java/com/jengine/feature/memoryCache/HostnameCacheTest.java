package com.jengine.feature.memoryCache;

/**
 * @author nouuid
 * @date 4/19/2016
 * @description
 */
public class HostnameCacheTest {

    @org.junit.Test
    public void test() throws InterruptedException {
        HostnameCache hostnameCache = HostnameCache.getInstance();

        Runnable addRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    int i=0;
                    while (true) {
                        hostnameCache.addHostname("host" + i);
                        i++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable addRunnable2 = new Runnable() {
            @Override
            public void run() {
                try {
                    int i=0;
                    while (true) {
                        hostnameCache.addHostname("host" + i);
                        i++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable removeRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    int i=0;
                    while (true) {
                        hostnameCache.removeHostname("host" + i);
                        i++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Thread addThread = new Thread(addRunnable);
        Thread addThread2 = new Thread(addRunnable2);
        Thread removeThread = new Thread(removeRunnable);

        addThread.start();
        addThread2.start();
        removeThread.start();

        Thread.sleep(60*1000);
    }
}
