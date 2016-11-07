package com.jengine.pattern.behavioral.visitor;

import org.junit.Test;

import java.util.List;

/**
 * Created by nouuid on 2015/5/22.
 */
public class VisitorTest {

    @Test
    public void test() {
        List<Element> list = ObjectStruture.getList();
        for(Element e: list){
            e.accept(new VisitorImpl());
        }
    }
}
