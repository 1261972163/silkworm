package com.jengine.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by nouuid on 2015/6/25.
 */
public class NetworkUtil {

  /**
   * 获得一个InetAddress对象，该对象含有本地机的域名和IP地址。
   */
  public static InetAddress getInetAddress() {
    try {
      return InetAddress.getLocalHost();
    } catch (UnknownHostException e) {
      System.out.println("unknown host!");
    }
    return null;
  }

  /**
   * 获取InetAddress对象的IP地址
   */
  public static String getHostIp() {
    InetAddress netAddress = getInetAddress();
    if (null == netAddress) {
      return null;
    }
    String ip = netAddress.getHostAddress(); //get the ip address
    return ip;
  }

  /**
   * 获取InetAddress对象的域名
   */
  public static String getHostName() {
    InetAddress netAddress = getInetAddress();
    if (null == netAddress) {
      return null;
    }
    String name = netAddress.getHostName(); // get the host address
    return name;
  }


}
