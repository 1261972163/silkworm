package com.nouuid.behavioralpattern.command;

/**
 * Created by nouuid on 2015/5/14.
 */
public class Invoker {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void action() {
        this.command.execute();
    }
}
