package com.jengine.feature.webservice;

import javax.xml.ws.Endpoint;

/**
 * content
 *
 * @author bl07637
 * @date 9/12/2016
 * @since 0.1.0
 */
public class MyServer {

    public void start() {
        String address = "http://localhost:8888/ns";
        //第二个参数：要发布这个接口的哪一个实现类
        Endpoint.publish(address, new MyServiceImpl());
        //SEI Service Endpoint Interface 服务提供的接口：MyService
        //SIB Service Implements Bean 服务实现的Bean：MyServiceImpl
    }
}
