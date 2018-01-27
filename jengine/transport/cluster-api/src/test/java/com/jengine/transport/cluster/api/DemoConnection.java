package com.jengine.transport.cluster.api;


import com.jengine.common.javacommon.utils.ObjectMapUtil;
import com.jengine.transport.cluster.api.transport.AbstractTransportConnection;
import com.jengine.transport.cluster.api.transport.Message;
import com.jengine.transport.cluster.api.transport.TransportConnection;
import com.jengine.transport.serialize.json.JsonUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nouuid on 13/01/2018.
 */
public class DemoConnection extends AbstractTransportConnection implements TransportConnection {
  private static final Logger logger = LoggerFactory.getLogger(DemoConnection.class);

  public static String result = null;

  private boolean throwException = false;

  public boolean isThrowException() {
    return throwException;
  }

  public void setThrowException(boolean throwException) {
    this.throwException = throwException;
  }

  @Override
  protected void instanceConnect() {

  }

  @Override
  protected void instanceClose() {

  }

  @Override
  protected void instanceInvoke(Message message) throws Exception {
    if (throwException) {
      throw new Exception();
    }

    Map<String, Object> resMap = new HashMap<String, Object>();

    Map<String, Object> headerMap = null;
    try {
      headerMap = ObjectMapUtil.objectToMap(message.getHeader());
    } catch (Exception e) {
      e.printStackTrace();
    }
    resMap.putAll(headerMap);
    resMap.put("body", message.getBody());
    resMap.remove("startTime");
    resMap.remove("endTime");
    resMap.remove("xngParentId");
    resMap.remove("xngGlobalId");
    resMap.remove("xngUuid");
    result = JsonUtil.toJson(resMap);
    logger.info(">>> " + JsonUtil.toJson(message));
  }

  @Override
  protected void instanceInvoke(List<Message> messages) throws Exception {
    if (throwException) {
      throw new Exception();
    }
    List<Map<String, Object>> resList = new ArrayList<Map<String, Object>>();
    for (Message message : messages) {
      Map<String, Object> resMap = new HashMap<String, Object>();
      Map<String, Object> headerMap = null;
      try {
        headerMap = ObjectMapUtil.objectToMap(message.getHeader());
      } catch (Exception e) {
        e.printStackTrace();
      }
      resMap.putAll(headerMap);
      resMap.put("body", message.getBody());
      resMap.remove("startTime");
      resMap.remove("endTime");
      resMap.remove("xngParentId");
      resMap.remove("xngGlobalId");
      resMap.remove("xngUuid");
      resList.add(resMap);
      logger.info(">>> " + JsonUtil.toJson(message));
    }
    result = JsonUtil.toJson(resList);
  }

}
