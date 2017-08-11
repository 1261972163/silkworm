package com.jengine.pattern.behavioral.interpreter;

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
            //�����﷨�жϣ��ݹ����
        }
        Expression exp = stack.pop();
        exp.interpreter(ctx);
    }
}
