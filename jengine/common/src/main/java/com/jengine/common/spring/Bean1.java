package com.jengine.common.spring;

/**
 * content
 *
 * @author bl07637
 * @date 5/17/2017
 * @since 0.1.0
 */
public class Bean1 {
    private String name1;
    private String name2;

    static {
        System.out.println("bean1 static");
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }
}