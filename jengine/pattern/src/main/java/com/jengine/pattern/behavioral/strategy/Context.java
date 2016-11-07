package com.jengine.pattern.behavioral.strategy;

/**
 * Created by nouuid on 2015/5/22.
 */
public class Context {
    private Strategy strategy;

    public Context(Strategy strategy){
        this.strategy = strategy;
    }

    public void execute(){
        strategy.doSomething();
    }
}
