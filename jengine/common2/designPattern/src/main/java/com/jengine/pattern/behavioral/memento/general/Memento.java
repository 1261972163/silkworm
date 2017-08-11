package com.jengine.pattern.behavioral.memento.general;

/**
 * Created by nouuid on 2015/5/18.
 */
public class Memento {
    private String state = "";
    public Memento(String state){
        this.state = state;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
}
