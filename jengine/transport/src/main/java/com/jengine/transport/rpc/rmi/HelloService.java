package com.jengine.transport.rpc.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author nouuid
 * @description
 * @date 3/11/17
 */
public interface HelloService extends Remote {

    String say(String name) throws RemoteException;

    int say(int num1, int num2) throws RemoteException;
}
