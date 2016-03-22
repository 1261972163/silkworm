package com.nouuid.behavioralpattern.command;

/**
 * 接收者，负责接收命令并且执行命令
 * Created by nouuid on 2015/5/14.
 */
public class Receiver {
    public void doSomething() {
        System.out.println("doSomething()");
    }

    public void doSomething2() {
        System.out.println("doSomething2()");
    }
}
