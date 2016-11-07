package com.jengine.pattern.behavioral.memento.general;

/**
 * Created by nouuid on 2015/5/18.
 */
public class Caretaker {
    private Memento memento;
    public Memento getMemento(){
        return memento;
    }
    public void setMemento(Memento memento){
        this.memento = memento;
    }
}
