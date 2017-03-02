package com.nouuid.dubbo.demo.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author nouuid
 * @description
 * @date 2/27/17
 */
public class Provider {
    public static void main(String[] args) throws Exception {
        String desc = Provider.class.getResource("/") + "demo/provider.xml";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {desc});
        context.start();
        System.out.println("provider start.");
        System.in.read(); // 按任意键退出
    }
}
