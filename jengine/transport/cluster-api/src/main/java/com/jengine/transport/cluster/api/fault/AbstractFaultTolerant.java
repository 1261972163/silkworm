package com.jengine.transport.cluster.api.fault;

import com.jengine.transport.cluster.api.loadbalance.LoadBalance;
import com.jengine.transport.cluster.api.transport.Message;
import com.jengine.transport.cluster.api.transport.TransportConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

/**
 * Created by nouuid on 13/01/2018.
 */
public abstract class AbstractFaultTolerant implements FaultTolerant {

  private static final Logger logger = LoggerFactory.getLogger(AbstractFaultTolerant.class);

  protected TransportConnection loadbalanceConnection(Lock connectionsUpdateLock,
                                                    LoadBalance<TransportConnection> loadBalance,
                                                    List<TransportConnection> transportConnections) {
    TransportConnection actionTransportConnection = null;
    connectionsUpdateLock.lock();
    try {
      // 取出存活的连接
      List<TransportConnection> activeTransportConnections = new ArrayList<TransportConnection>();
      for (TransportConnection transportConnection : transportConnections) {
        if (transportConnection.isActive()) {
          activeTransportConnections.add(transportConnection);
        }
      }
      // 从存活连接中选择出一个有效连接
      actionTransportConnection = loadBalance.select(activeTransportConnections);
    } finally {
      connectionsUpdateLock.unlock();
    }
    return actionTransportConnection;
  }

  protected void onceInvoke(TransportConnection actionTransportConnection,
                            Message message, List<Message> messages, boolean bulk)
      throws Exception {
    if (bulk) {
      actionTransportConnection.invoke(messages);
    } else {
      actionTransportConnection.invoke(message);
    }
  }
}
