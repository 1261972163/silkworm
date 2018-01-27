package com.jengine.transport.cluster.api;

import com.jengine.transport.cluster.api.transport.Header;
import com.jengine.transport.cluster.api.transport.Message;

/**
 * Created by nouuid on 13/01/2018.
 */
public class MessageDemo implements Message {

  @Override
  public Header getHeader() {
    return null;
  }

  @Override
  public String getBody() {
    return null;
  }
}