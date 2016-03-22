package com.nouuid.behavioralpattern.command;

import org.junit.Test;

/**
 * Created by nouuid on 2015/5/14.
 */
public class CommandTest {

    @Test
    public void test() {
        Receiver receiver = new Receiver();
        Command command = new ConcreteCommand(receiver);

        //客户端直接执行具体命令方式（此方式与类图相符）
        command.execute();

        //客户端通过调用者来执行命令
        Invoker invoker = new Invoker();
        invoker.setCommand(command);
        invoker.action();

        invoker.setCommand(new ConcreteCommand2(receiver));
        invoker.action();
    }
}
