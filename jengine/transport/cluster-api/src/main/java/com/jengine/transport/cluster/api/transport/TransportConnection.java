/**
 * Copyright (C) 2017 The BEST Authors
 */

package com.jengine.transport.cluster.api.transport;

import java.util.List;

/**
 * @author nouuid
 */
public interface TransportConnection {

  boolean isActive();

  boolean setActive(boolean active);

  void start() throws Exception;

  void close() throws Exception;

  void invoke(Message message) throws Exception;

  void invoke(List<Message> messages) throws Exception;

}
