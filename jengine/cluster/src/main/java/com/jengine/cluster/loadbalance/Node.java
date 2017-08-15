package com.jengine.cluster.loadbalance;

/**
 * content
 *
 * @author nouuid
 * @date 12/30/2016
 * @since 0.1.0
 */
public class Node {
  private String hostIp;
  private int weight;

  public String getHostIp() {
    return hostIp;
  }

  public void setHostIp(String hostIp) {
    this.hostIp = hostIp;
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }
}
