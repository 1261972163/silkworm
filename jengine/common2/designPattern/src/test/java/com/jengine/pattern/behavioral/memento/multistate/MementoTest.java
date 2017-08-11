package com.jengine.pattern.behavioral.memento.multistate;

import org.junit.Test;

/**
 * Created by nouuid on 2015/5/18.
 */
public class MementoTest {
    @Test
    public void test() {
        Originator ori = new Originator();
        Caretaker caretaker = new Caretaker();
        ori.setState1("�й�");
        ori.setState2("ǿʢ");
        ori.setState3("����");
        System.out.println("===��ʼ��״̬===\n"+ori);

        caretaker.setMemento("001",ori.createMemento());
        ori.setState1("���");
        ori.setState2("�ܹ�");
        ori.setState3("����");
        System.out.println("===�޸ĺ�״̬===\n"+ori);

        ori.restoreMemento(caretaker.getMemento("001"));
        System.out.println("===�ָ���״̬===\n"+ori);
    }
}
