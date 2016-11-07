package com.jengine.pattern.behavioral.command;

import org.junit.Test;

/**
 * Created by nouuid on 2015/5/14.
 */
public class CommandTest {

    @Test
    public void test() {
        Receiver receiver = new Receiver();
        Command command = new ConcreteCommand(receiver);

        //�ͻ���ֱ��ִ�о������ʽ���˷�ʽ����ͼ�����
        command.execute();

        //�ͻ���ͨ����������ִ������
        Invoker invoker = new Invoker();
        invoker.setCommand(command);
        invoker.action();

        invoker.setCommand(new ConcreteCommand2(receiver));
        invoker.action();
    }
}
