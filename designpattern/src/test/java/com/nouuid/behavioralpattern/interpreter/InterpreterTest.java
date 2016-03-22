package com.nouuid.behavioralpattern.interpreter;

import org.junit.Test;

import java.util.Stack;

/**
 * Created by nouuid on 2015/5/15.
 */
public class InterpreterTest {
    @Test
    public void test() {
        String expression = "";
        char[] charArray = expression.toCharArray();
        Context ctx = new Context();
        Stack<Expression> stack = new Stack<Expression>();
        for(int i=0;i<charArray.length;i++){
            //进行语法判断，递归调用
        }
        Expression exp = stack.pop();
        exp.interpreter(ctx);
    }
}
