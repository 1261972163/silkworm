package com.jengine.transport.cluster.api.loadbalance;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 轮询法的优点在于：试图做到请求转移的绝对均衡。
 * <p>
 * 轮询法的缺点在于：为了做到请求转移的绝对均衡，引入AtomicInteger保证修改的互斥性，对并发吞吐量有一些影响。
 *
 * @author nouuid
 * @date 3/2/2016
 * @description Round-Robin, do loop from 1 to N
 */
public class RoundRobinLoadBalance<T> extends AbstractLoadBalance<T> implements LoadBalance<T> {
  private final AtomicLong sequences = new AtomicLong();

  @Override
  protected List rebuildNodes(List nodes) {
    List<T> nodeListTmp = new LinkedList<T>();
    nodeListTmp.addAll(nodes);
    return nodeListTmp;
  }


  @Override
  protected T getNode(List<T> nodes) {
    long count = getSequenceNumber();
    T node = nodes.get((int) ((count) % nodes.size()));
    return node;
  }

  private long getSequenceNumber() {
    long index = sequences.incrementAndGet()-1;
    if (index < 0) {
      index = 0;
      sequences.set(0);
    }
    return index;
  }
}
