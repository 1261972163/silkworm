package com.nouuid.behavioralpattern.memento.general;

import com.nouuid.behavioralpattern.memento.general.Caretaker;
import com.nouuid.behavioralpattern.memento.general.Originator;
import org.junit.Test;

/**
 * Created by nouuid on 2015/5/18.
 */
public class MementoTest {
    @Test
    public void test() {
        Originator originator = new Originator();
        originator.setState("×´Ì¬1");
        System.out.println("³õÊ¼×´Ì¬:" + originator.getState());

        Caretaker caretaker = new Caretaker();
        caretaker.setMemento(originator.createMemento());

        originator.setState("×´Ì¬2");
        System.out.println("¸Ä±äºó×´Ì¬:" + originator.getState());

        originator.restoreMemento(caretaker.getMemento());
        System.out.println("»Ö¸´ºó×´Ì¬:" + originator.getState());
    }
}
