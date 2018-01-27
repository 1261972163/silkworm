package com.jengine.common.pattern.behavioral.memento.general;

import org.junit.Test;

/**
 * Created by nouuid on 2015/5/18.
 */
public class MementoTest {
    @Test
    public void test() {
        Originator originator = new Originator();
        originator.setState("state1");
        System.out.println("init state: " + originator.getState());

        Caretaker caretaker = new Caretaker();
        caretaker.setMemento(originator.createMemento());

        originator.setState("state2");
        System.out.println("modified state: " + originator.getState());

        originator.restoreMemento(caretaker.getMemento());
        System.out.println("reseted state: " + originator.getState());
    }
}
