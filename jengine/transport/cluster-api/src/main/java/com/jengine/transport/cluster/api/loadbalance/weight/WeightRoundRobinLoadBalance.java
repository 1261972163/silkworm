package com.jengine.transport.cluster.api.loadbalance.weight;

import com.jengine.transport.cluster.api.loadbalance.AbstractLoadBalance;
import com.jengine.transport.cluster.api.loadbalance.LoadBalance;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 不同的服务器可能机器配置和当前系统的负载并不相同，因此它们的抗压能力也不尽相同，给配置高、负载低的机器配置更高的权重， 让其处理更多的请求，而低配置、高负载的机器，则给其分配较低的权重，降低其系统负载。加权轮询法可以很好地处理这一问题，
 * 并将请求顺序按照权重分配到后端。
 *
 * 与轮询法类似，只是在获取服务器地址之前增加了一段权重计算的代码，根据权重的大小，将地址重复地增加到服务器地址列表中， 权重越大，该服务器每轮所获得的请求数量越多。
 *
 * @author nouuid
 * @date 12/30/2016
 * @since 0.1.0
 */
public class WeightRoundRobinLoadBalance<T> extends AbstractLoadBalance<T> implements
    LoadBalance<T> {
  private final AtomicLong sequences = new AtomicLong();

  @Override
  protected List<T> rebuildNodes(List<T> nodes) {
    List<T> nodeListTmp = new LinkedList<T>();
    nodeListTmp.addAll(nodes);
    List<T> nodeList = new ArrayList<T>();
    for (T node : nodeListTmp) {
      for (int i = 0; i < ((Node) node).getWeight(); i++) {
        nodeList.add(node);
      }
    }
    return nodeList;
  }

  @Override
  protected T getNode(List<T> nodes) {
    long index = getNodeNumber();
    return nodes.get((int) ((index) % nodes.size()));
  }

  private long getNodeNumber() {
    long index = sequences.incrementAndGet();
    if (index < 0) {
      index = 0;
      sequences.set(0);
    }
    return index;
  }
}
