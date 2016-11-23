package com.jengine.pattern.behavioral;

/**
 * 命令模式
 * 请求方和接收方分离
 * 请求方不必知道接收方的接口
 * 不必知道请求是怎么被接收，以及操作是否被执行、何时被执行，以及是怎么被执行的。
 *
 * @author bl07637
 * @date 11/14/2016
 * @since 0.1.0
 */
public class CommandDemo {

    public static void main(String[] args) {
        Receiver receiver = new Receiver();
        Command command = new ConcreteCommand(receiver);
        command.execute();

        Invoker invoker = new Invoker();
        invoker.setCommand(command);
        invoker.action();

        invoker.setCommand(new ConcreteCommand2(receiver));
        invoker.action();
    }
}

/**
 * 接收方
 */
class Receiver {
    public void doSomething() {
        System.out.println("doSomething()");
    }

    public void doSomething2() {
        System.out.println("doSomething2()");
    }
}

/**
 * 请求方不必知道接收方的接口，因为接收方被封装在Command中
 */
class Invoker {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void action() {
        this.command.execute();
    }
}


/**
 * 命令，用于封装接收方
 */
abstract class Command {
    public abstract void execute();
}

class ConcreteCommand extends Command {
    private Receiver receiver;

    public ConcreteCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute() {
        this.receiver.doSomething();
    }
}

class ConcreteCommand2 extends Command {
    private Receiver receiver;

    public ConcreteCommand2(Receiver receiver) {
        this.receiver = receiver;
    }

    public void execute() {
        this.receiver.doSomething2();
    }
}

