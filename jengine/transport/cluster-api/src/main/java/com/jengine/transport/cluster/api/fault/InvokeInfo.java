package com.jengine.transport.cluster.api.fault;


import com.jengine.transport.cluster.api.loadbalance.LoadBalance;
import com.jengine.transport.cluster.api.transport.Message;
import com.jengine.transport.cluster.api.transport.TransportConnection;

import java.util.List;
import java.util.concurrent.locks.Lock;

/**
 * Created by nouuid on 13/01/2018.
 */
public class InvokeInfo {
  private Lock connectionsUpdateLock;
  private List<TransportConnection> transportConnections;
  private LoadBalance<TransportConnection> loadBalance;
  private Message message;
  private List<Message> messages;
  private boolean bulk;
  private int retryMaxNumber = 3;
  private int retryInterMillis = 500;


  public InvokeInfo(Lock connectionsUpdateLock,
                    List<TransportConnection> transportConnections,
                    LoadBalance<TransportConnection> loadBalance,
                    Message message,
                    List<Message> messages,
                    boolean bulk) {
   this.connectionsUpdateLock = connectionsUpdateLock;
   this.transportConnections = transportConnections;
   this.loadBalance = loadBalance;
   this.message = message;
   this.messages = messages;
   this.bulk = bulk;
  }

  public Lock getConnectionsUpdateLock() {
    return connectionsUpdateLock;
  }

  public List<TransportConnection> getTransportConnections() {
    return transportConnections;
  }

  public LoadBalance<TransportConnection> getLoadBalance() {
    return loadBalance;
  }

  public Message getMessage() {
    return message;
  }

  public List<Message> getMessages() {
    return messages;
  }

  public boolean isBulk() {
    return bulk;
  }

  public int getRetryMaxNumber() {
    return retryMaxNumber;
  }

  public int getRetryInterMillis() {
    return retryInterMillis;
  }
}
