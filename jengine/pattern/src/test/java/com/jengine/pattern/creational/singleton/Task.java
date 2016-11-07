package com.jengine.pattern.creational.singleton;

/**
 * Created by nouuid on 2015/5/12.
 */
public class Task implements Runnable {

    private String name;
    private Singleton singleton;
    private boolean flag = true;

    public Task(String name) {
        this.name = name;
        singleton = Singleton.getInstance();
    }

    public Singleton getSingleton() {
        return singleton;
    }

    @Override
    public void run() {
        int i = 0;
        while(flag) {
            try {
                Thread.sleep(1000);
                singleton.getInfo().put(name+"-"+i, name+"-"+i);
            } catch(Exception e) {
                e.printStackTrace();
            }
            i++;
        }
    }

    public void stop() throws Exception {
        flag = false;
    }
}
