package com.nouuid.dubbo.demo.consumer;

import com.nouuid.dubbo.demo.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author nouuid
 * @description
 * @date 2/27/17
 */
public class Consumer {
    public static void main(String[] args) throws Exception {
        String desc = Consumer.class.getResource("/") + "demo/consumer.xml";;
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {desc});
        context.start();
        System.out.println("consumer start.");

        DemoService demoService = (DemoService)context.getBean("demoService"); // 获取远程服务代理
        String hello = demoService.sayHello("world"); // 执行远程方法

        System.out.println( hello ); // 显示调用结果
    }

}
