package com.jengine.j2se.util.spi;

/**
 * @author nouuid
 * @description
 * @date 11/10/16
 */
public class TextHello implements HelloInterface {

    @Override
    public void sayHello() {
        System.out.println("Text Hello.");
    }

}