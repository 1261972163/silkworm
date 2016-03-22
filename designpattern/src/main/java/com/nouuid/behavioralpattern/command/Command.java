package com.nouuid.behavioralpattern.command;

/**
 * 对需要执行的命令进行声明，一般来说要对外公布一个execute方法用来执行命令
 * Created by nouuid on 2015/5/14.
 */
public abstract class Command {
    public abstract void execute();
}
