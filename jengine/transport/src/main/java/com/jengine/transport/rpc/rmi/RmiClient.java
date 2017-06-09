package com.jengine.transport.rpc.rmi;

import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * @author nouuid
 * @description
 * @date 3/11/17
 */
public class RmiClient {

    public static void main(String args[]) {
        try {
            Context namingContext = new InitialContext();// 初始化命名内容
            HelloService helloService = (HelloService) namingContext
                    .lookup("rmi://localhost:8891/hello");//获得远程对象的Stub对象
            String res1 = helloService.say("nouuid");//通过远程对象，调用say方法
            int res2 = helloService.say(90, 2);
            System.out.println(res1);
            System.out.println("server say 90+2=：" + res2);
        } catch (Exception e) {
        }
        System.out.println("finished.");
    }
}
