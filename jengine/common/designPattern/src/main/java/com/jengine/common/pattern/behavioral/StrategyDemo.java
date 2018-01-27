package com.jengine.common.pattern.behavioral;

/**
 * content
 *
 * @author nouuid
 * @date 11/14/2016
 * @since 0.1.0
 */
public class StrategyDemo {
    public static void main(String[] args) {
        StrategyHolder strategyHolder = new StrategyHolder();
        strategyHolder.setStrategy(new ConcreteStrategy1());
        strategyHolder.execute("a");
        strategyHolder.setStrategy(new ConcreteStrategy2());
        strategyHolder.execute("a");
    }
}

class StrategyHolder {
    private Strategy strategy;

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void execute(String s) {
        System.out.println(strategy.doSomething(s));
    }
}

interface Strategy {
    String doSomething(String s);
}

class ConcreteStrategy1 implements Strategy {
    @Override
    public String doSomething(String s) {
        return "+" + s;
    }
}

class ConcreteStrategy2 implements Strategy {
    @Override
    public String doSomething(String s) {
        return "-" + s;
    }
}
