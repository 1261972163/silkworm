package com.jengine.transport.rpc.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * @author nouuid
 * @description
 * @date 3/11/17
 */
public class RmiServer {
    public static void main(String args[])
            throws java.rmi.AlreadyBoundException {

        try {
            // 创建一个远程对象RemoteObj，实质上隐含了是生成stub和skeleton,并返回stub代理引用
            HelloService helloService = new HelloServiceImpl();

            // 本地创建并启动RMI Service，被创建的Registry服务将在指定的端口,侦听请求
            // Java默认端口是1099，缺少注册表创建，则无法绑定对象到远程注册表上
            LocateRegistry.createRegistry(8891);

            // 把远程对象注册到RMI注册服务器上，并命名为hello（名字可自定义，客户端要对应）
            // 绑定的URL标准格式为：rmi://host:port/name(其中协议名可以省略，下面两种写法都是正确的）
            Naming.rebind("rmi://localhost:8891/hello", helloService);// 将stub代理绑定到Registry服务的URL上
            // Naming.bind("//localhost:8880/hello",helloService);

            System.out.println(">>>>>INFO:remote IHello is successfully binded.");
        } catch (RemoteException e) {
            System.out.println("Exception is threw in creating remote object.");
            e.printStackTrace();
        } catch (MalformedURLException e) {
            System.out.println("URL error");
            e.printStackTrace();
        }
    }
}
