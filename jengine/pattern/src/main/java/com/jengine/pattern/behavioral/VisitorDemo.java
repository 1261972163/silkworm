package com.jengine.pattern.behavioral;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 访问者
 *
 * @author nouuid
 * @date 11/14/2016
 * @since 0.1.0
 */
public class VisitorDemo {
    public static void main(String[] args) {
        List<Element> list = ElementHolder.getList();
        for(Element e: list){
            e.accept(new VisitorHandlerImpl());
        }
    }
}

class ElementHolder {
    public static List<Element> getList() {
        List<Element> list = new ArrayList<Element>();
        Random ran = new Random();
        for (int i = 0; i < 10; i++) {
            int a = ran.nextInt(100);
            if (a > 50) {
                list.add(new ConcreteElement1());
            } else {
                list.add(new ConcreteElement2());
            }
        }
        return list;
    }
}

/**
 * visitor
 */
interface VisitorHandler {
    void visit(ConcreteElement1 el1);
    void visit(ConcreteElement2 el2);
}

class VisitorHandlerImpl implements VisitorHandler {

    public void visit(ConcreteElement1 el1) {
        el1.doSomething();
    }

    public void visit(ConcreteElement2 el2) {
        el2.doSomething();
    }
}

abstract class Element {
    public abstract void accept(VisitorHandler visitor);

    public abstract void doSomething();
}

class ConcreteElement1 extends Element {
    public void doSomething() {
        System.out.println("ConcreteElement1");
    }

    public void accept(VisitorHandler visitorHandler) {
        visitorHandler.visit(this);
    }
}

class ConcreteElement2 extends Element {
    public void doSomething() {
        System.out.println("ConcreteElement2");
    }

    public void accept(VisitorHandler visitorHandler) {
        visitorHandler.visit(this);
    }
}