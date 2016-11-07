package com.jengine.pattern.behavioral.command;

/**
 * Command���ʵ���࣬�Գ������������ķ�������ʵ��
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
