package com.jengine.data.nosql.redis.normal;

/**
 * @author nouuid
 * @date 3/25/2016
 * @description
 */
public class RedisConfig {

  private String host;
  private int port;
  private String pwd;
  private int timeout = 2000;

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

  public int getTimeout() {
    return timeout;
  }

  public void setTimeout(int timeout) {
    this.timeout = timeout;
  }
}
