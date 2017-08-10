package com.jengine.transport.rpc.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author nouuid
 * @description
 * @date 3/11/17
 */
public class HelloServiceImpl extends UnicastRemoteObject implements HelloService {
    public HelloServiceImpl() throws RemoteException {
    }

    public String say(String name) throws RemoteException {
        String res = "Hello, " + name;
        System.out.println(res);
        return res;
    }

    public int say(int num1, int num2) throws RemoteException {
        int res = (num1 + num2);
        System.out.println(res);
        return res;
    }
}