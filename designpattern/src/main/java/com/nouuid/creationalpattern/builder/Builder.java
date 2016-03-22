package com.nouuid.creationalpattern.builder;

/**
 * Created by nouuid on 2015/5/13.
 */
public abstract class Builder {
    public abstract void setPart(String arg1, String arg2);
    public abstract Product getProduct();
}
