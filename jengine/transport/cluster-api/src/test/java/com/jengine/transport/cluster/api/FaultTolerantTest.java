package com.jengine.transport.cluster.api;

import com.jengine.transport.cluster.api.fault.Failfast;
import com.jengine.transport.cluster.api.fault.Failover;
import com.jengine.transport.cluster.api.fault.InvokeInfo;
import com.jengine.transport.cluster.api.loadbalance.LoadBalance;
import com.jengine.transport.cluster.api.loadbalance.RandomLoadBalance;
import com.jengine.transport.cluster.api.transport.Message;
import com.jengine.transport.cluster.api.transport.TransportConnection;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by nouuid on 13/01/2018.
 */
public class FaultTolerantTest {

  @Test
  public void failfast() throws Exception {
    Lock connectionsUpdateLock = new ReentrantLock();
    List<TransportConnection> transportConnections =
        new ArrayList<TransportConnection>();
    DemoConnection transportConnection = new DemoConnection();
    transportConnections.add(transportConnection);
    transportConnection.start();
    LoadBalance<TransportConnection> loadBalance =
        new RandomLoadBalance<TransportConnection>();
    Message message = new MessageDemo();
    List<Message> messages = null;
    boolean bulk = false;
    InvokeInfo invokeInfo = new InvokeInfo(connectionsUpdateLock,
        transportConnections, loadBalance, message, messages, bulk);

    Failfast failfast = new Failfast();
    try {
      failfast.invoke(invokeInfo);
    } catch (Exception e) {
      System.out.println("====" + e.getStackTrace());
    }
    try {
      transportConnection.setThrowException(true);
      failfast.invoke(invokeInfo);
    } catch (Exception e){
      Assert.assertTrue(true);
      return;
    }
    Assert.assertTrue(false);
  }

  @Test
  public void failover() throws Exception {
    Lock connectionsUpdateLock = new ReentrantLock();
    List<TransportConnection> transportConnections =
        new ArrayList<TransportConnection>();
    DemoConnection transportConnection = new DemoConnection();
    transportConnections.add(transportConnection);
    transportConnection.start();
    LoadBalance<TransportConnection> loadBalance =
        new RandomLoadBalance<TransportConnection>();
    Message message = new MessageDemo();
    List<Message> messages = null;
    boolean bulk = false;
    InvokeInfo invokeInfo = new InvokeInfo(connectionsUpdateLock,
        transportConnections, loadBalance, message, messages, bulk);

    Failover failover = new Failover();
    try {
      failover.invoke(invokeInfo);
    } catch (Exception e) {
      System.out.println("====" + e.getStackTrace());
    }
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          Thread.sleep(5*1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        transportConnection.setThrowException(false);
      }
    });
    thread.start();
    try {
      transportConnection.setThrowException(true);
      failover.invoke(invokeInfo);
    } catch (Exception e){
      Assert.assertTrue(false);
      return;
    }
    Assert.assertTrue(true);
  }

}
