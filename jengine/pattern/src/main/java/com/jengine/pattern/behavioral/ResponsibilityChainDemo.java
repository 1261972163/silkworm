package com.jengine.pattern.behavioral;

/**
 * chain of responsibility
 *
 * @author bl07637
 * @date 11/14/2016
 * @since 0.1.0
 */
public class ResponsibilityChainDemo {
    public static void main(String[] args) {
        ResponsibilityChainConcrete1 responsibilityChainConcrete1 = new ResponsibilityChainConcrete1();
        ResponsibilityChainConcrete2 responsibilityChainConcrete2 = new ResponsibilityChainConcrete2();
        responsibilityChainConcrete1.setSuccessor(responsibilityChainConcrete2);

        StringBuilder s = new StringBuilder("request");
        responsibilityChainConcrete1.handle(s);
        System.out.println(s);
    }
}

abstract class ResponsibilityChainHandler {
    private ResponsibilityChainHandler successor; // next

    public ResponsibilityChainHandler getSuccessor() {
        return successor;
    }

    public void setSuccessor(ResponsibilityChainHandler successor) {
        this.successor = successor;
    }

    public abstract void handle(StringBuilder s);
}

class ResponsibilityChainConcrete1 extends ResponsibilityChainHandler {

    @Override
    public void handle(StringBuilder s) {
        if(getSuccessor() != null) {
            append1(s);
            getSuccessor().handle(s);
        } else {
            append2(s);
        }
    }

    private void append1(StringBuilder s) {
        s.append("1");
    }

    private void append2(StringBuilder s) {
        s.append("2");
    }

}

class ResponsibilityChainConcrete2 extends ResponsibilityChainHandler {

    @Override
    public void handle(StringBuilder s) {
        if(getSuccessor() != null) {
            append1(s);
            getSuccessor().handle(s);
        } else {
            append2(s);
        }
    }

    private void append1(StringBuilder s) {
        s.append("a");
    }

    private void append2(StringBuilder s) {
        s.append("b");
    }

}



