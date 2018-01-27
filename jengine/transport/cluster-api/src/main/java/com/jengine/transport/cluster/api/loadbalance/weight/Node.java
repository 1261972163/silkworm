package com.jengine.transport.cluster.api.loadbalance.weight;

/**
 * content
 *
 * @author nouuid
 * @date 12/30/2016
 * @since 0.1.0
 */
public class Node<T> {
  private T value;
  private int weight;

  public T getValue() {
    return value;
  }

  public void setValue(T value) {
    this.value = value;
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }
}
