package com.jengine.common.pattern.creational.prototype;

import java.io.Serializable;

/**
 * Created by nouuid on 2015/5/14.
 */
public class Component implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
