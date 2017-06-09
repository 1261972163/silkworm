package com.nouuid.dubbo.demo.provider;

import com.nouuid.dubbo.demo.DemoService;

/**
 * @author nouuid
 * @description
 * @date 2/27/17
 */
public class DemoServiceImpl implements DemoService {

    public String sayHello(String name) {
        System.out.println("receive: " + name);
        return "Hello " + name;
    }

}
