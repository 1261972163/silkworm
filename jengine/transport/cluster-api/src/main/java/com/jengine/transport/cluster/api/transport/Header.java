/**
 * Copyright (C) 2017 The BEST Authors
 */

package com.jengine.transport.cluster.api.transport;

/**
 * AbstractEntry.
 *
 * @author nouuid
 */
public class Header {

  /**
   * version of radar message.
   */
  private String version;

  /**
   * enviroment.
   */
  private String env;

  /**
   * appid.
   */
  private String appid;

  /**
   * tag of appid.
   */
  private String apptag;

  /**
   * host name.
   */
  private String hostname;

  /**
   * host ip.
   */
  private String hostip;

  /**
   * message type.
   */
  private String type;

  /**
   * message area.
   */
  private String area;

  /**
   * sub type of message type.
   */
  private String subArea;

  /**
   * start time.
   */
  private long startTime;

  /**
   * end time.
   */
  private long endTime;

  /**
   * thread name.
   */
  private String threadName;

  /**
   * class name.
   */
  private String className;

  /**
   * method name.
   */
  private String method;

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getEnv() {
    return env;
  }

  public void setEnv(String env) {
    this.env = env;
  }

  public String getAppid() {
    return appid;
  }

  public void setAppid(String appid) {
    this.appid = appid;
  }

  public String getApptag() {
    return apptag;
  }

  public void setApptag(String apptag) {
    this.apptag = apptag;
  }

  public String getHostname() {
    return hostname;
  }

  public void setHostname(String hostname) {
    this.hostname = hostname;
  }

  public String getHostip() {
    return hostip;
  }

  public void setHostip(String hostip) {
    this.hostip = hostip;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  public String getSubArea() {
    return subArea;
  }

  public void setSubArea(String subArea) {
    this.subArea = subArea;
  }

  public long getStartTime() {
    return startTime;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

  public long getEndTime() {
    return endTime;
  }

  public void setEndTime(long endTime) {
    this.endTime = endTime;
  }

  public String getThreadName() {
    return threadName;
  }

  public void setThreadName(String threadName) {
    this.threadName = threadName;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }
}
