package com.nouuid.behavioralpattern.memento.multistate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nouuid on 2015/5/18.
 */
public class Caretaker {
    private Map<String, Memento> memMap = new HashMap<String, Memento>();
    public Memento getMemento(String index){
        return memMap.get(index);
    }

    public void setMemento(String index, Memento memento){
        this.memMap.put(index, memento);
    }
}
