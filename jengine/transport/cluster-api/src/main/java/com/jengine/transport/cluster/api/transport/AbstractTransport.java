/**
 * Copyright (C) 2017 The BEST Authors
 */

package com.jengine.transport.cluster.api.transport;


import com.jengine.transport.cluster.api.fault.FaultTolerant;
import com.jengine.transport.cluster.api.fault.InvokeInfo;
import com.jengine.transport.cluster.api.loadbalance.LoadBalance;
import com.jengine.transport.serialize.json.JsonUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author nouuid
 */
public abstract class AbstractTransport implements Transport {
  private static final Logger logger = LoggerFactory.getLogger(AbstractTransport.class);

  protected List<TransportConnection> transportConnections;
  private LoadBalance<TransportConnection> loadBalance;
  private FaultTolerant faultTolerant;

  protected Lock connectionsUpdateLock = new ReentrantLock();

  public AbstractTransport(LoadBalance<TransportConnection> loadBalance,
                           FaultTolerant faultTolerant) {
    this.loadBalance = loadBalance;
    this.faultTolerant = faultTolerant;
    transportConnections = new ArrayList<TransportConnection>();
  }

  protected void addConnection(TransportConnection transportConnection) throws Exception {
    if (transportConnection == null) {
      throw new Exception("transportConnection is null");
    }
    connectionsUpdateLock.lock();
    try {
      transportConnections.add(transportConnection);
    } finally {
      connectionsUpdateLock.unlock();
    }
  }

  @Override
  public void start() throws Exception {
    connectionsUpdateLock.lock();
    try {
      checkConnectionsEmpty();
      for (TransportConnection transportConnection : transportConnections) {
        if (!transportConnection.isActive()) {
          try {
            transportConnection.start();
          } catch (Exception e) {
            logger.error("", e);
          }
        }
      }
    } finally {
      connectionsUpdateLock.unlock();
    }
  }

  private void checkConnectionsEmpty() throws Exception {
    if (transportConnections == null || transportConnections.isEmpty()) {
      throw new Exception("TransportConnections is null or empty.");
    }
  }

  @Override
  public void close() throws Exception {
    connectionsUpdateLock.lock();
    try {
      checkConnectionsEmpty();
      for (TransportConnection transportConnection : transportConnections) {
        if (transportConnection != null) {
          try {
            transportConnection.close();
          } catch (Exception e) {
            logger.error("", e);
          }
        }
      }
    } finally {
      connectionsUpdateLock.unlock();
    }
  }

  @Override
  public void send(Message message) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("transportConnectionsSize=%d, loadBalance=%s, message=%s",
          transportConnections.size(),
          loadBalance.getClass().getName(),
          JsonUtil.toJson(message));
    }
    InvokeInfo invokeInfo = new InvokeInfo(connectionsUpdateLock,
        transportConnections, loadBalance, message, null, false);
    faultTolerant.invoke(invokeInfo);
  }

  @Override
  public void send(List<Message> messages) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("transportConnectionsSize=%d, loadBalance=%s, message=%s",
          transportConnections.size(),
          loadBalance.getClass().getName(),
          JsonUtil.toJson(messages));
    }
    InvokeInfo invokeInfo = new InvokeInfo(connectionsUpdateLock,
        transportConnections, loadBalance, null, messages, true);
    faultTolerant.invoke(invokeInfo);
  }

}
