package com.jengine.common.j2se.util.spi;

import java.util.ServiceLoader;

/**
 * 简介：
 *
 * SPI 全称为 (Service Provider Interface) ,是JDK内置的一种服务提供发现机制。
 * 目前有不少框架用它来做服务的扩展发现，
 * 简单来说，它就是一种动态替换发现的机制，
 * 举个例子来说， 有个接口，想运行时动态的给它添加实现，你只需要添加一个实现，
 * 而后，把新加的实现，描述给JDK知道就行啦（通过改一个文本文件即可） 公司内部，
 * 目前Dubbo框架就基于SPI机制提供扩展功能。
 *
 * 使用要点：
 *
 *  1. 文件目录META-INF/services放置在Classpath路径下；
 *  2. 目录下放置配置文件，文件名为要实现接口的全名；
 *  3. 文件内容为接口实现类的全名，按行划分，一行一个实现类
 *
 * @author nouuid
 * @description
 * @date 11/10/16
 */
public class SPIDemo {
    public static void main(String[] args) {

        // 4. 载入
        ServiceLoader<HelloInterface> loaders = ServiceLoader.load(HelloInterface.class);

        // 5. 调用
        for (HelloInterface in : loaders) {
            in.sayHello();
        }
    }
}