/**
 * Copyright (C) 2017 The BEST Authors
 */

package com.jengine.transport.cluster.api.fault;

import com.jengine.transport.cluster.api.loadbalance.LoadBalance;
import com.jengine.transport.cluster.api.transport.Message;
import com.jengine.transport.cluster.api.transport.TransportConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.locks.Lock;

/**
 * @author nouuid
 */
public class Failover extends AbstractFaultTolerant implements FaultTolerant {
  private static final Logger logger = LoggerFactory.getLogger(Failover.class);

  public void invoke(InvokeInfo invokeInfo) throws Exception {
    TransportConnection transportConnection;
    Lock connectionsUpdateLock = invokeInfo.getConnectionsUpdateLock();
    while (true) {
      connectionsUpdateLock.lock();
      try {
        LoadBalance<TransportConnection> loadBalance = invokeInfo.getLoadBalance();
        List<TransportConnection> transportConnections = invokeInfo.getTransportConnections();
        transportConnection = loadbalanceConnection(connectionsUpdateLock,
            loadBalance, transportConnections);
        if (transportConnection != null) {
          try {
            Message message = invokeInfo.getMessage();
            List<Message> messages = invokeInfo.getMessages();
            boolean bulk = invokeInfo.isBulk();
            onceInvoke(transportConnection, message, messages, bulk);
            break;
          } catch (Exception e) {
            logger.error("transport invoke error.", e);
            try {
              Thread.sleep(invokeInfo.getRetryInterMillis());
            } catch (Exception e2) {
              // ignore
            }
          }
        } else {
          logger.error("### There is no avalibale transport nodes.");
          try {
            Thread.sleep(60 * 1000);
          } catch (Exception e) {
            // ignore
          }
        }
      } finally {
        connectionsUpdateLock.unlock();
      }
    }
  }


}
