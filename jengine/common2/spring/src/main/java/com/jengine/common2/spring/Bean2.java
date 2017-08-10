package com.jengine.common2.spring;

/**
 * content
 *
 * @author nouuid
 * @date 5/17/2017
 * @since 0.1.0
 */
public class Bean2 {
    private String name1;
    private Bean1 bean1;

    static {
        System.out.println("bean2 static");
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public Bean1 getBean1() {
        return bean1;
    }

    public void setBean1(Bean1 bean1) {
        this.bean1 = bean1;
    }
}
