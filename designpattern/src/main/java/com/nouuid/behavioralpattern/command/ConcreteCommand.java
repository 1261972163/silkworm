package com.nouuid.behavioralpattern.command;

/**
 * Command类的实现类，对抽象类中声明的方法进行实现
 * Created by nouuid on 2015/5/14.
 */
public class ConcreteCommand extends Command {
    private Receiver receiver;

    public ConcreteCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute() {
        this.receiver.doSomething();
    }
}
