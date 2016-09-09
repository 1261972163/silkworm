package com.jengine.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author bl07637
 * @date 8/19/2016
 * @description
 */
public class BeanLoadDemo {

    public void load() {
        System.out.println("before appContext.");
        ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/applicationContext1.xml");
        System.out.println("after appContext.");
    }
}

class Bean1 {
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

class Bean2 {
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
