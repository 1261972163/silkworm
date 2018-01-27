package com.jengine.transport.cluster.api.loadbalance.weight;

import com.jengine.transport.cluster.api.loadbalance.AbstractLoadBalance;
import com.jengine.transport.cluster.api.loadbalance.LoadBalance;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 基于概率统计的理论，吞吐量越大，随机算法的效果越接近于轮询算法的效果。
 *
 * @author nouuid
 * @date 12/30/2016
 * @since 0.1.0
 */
public class WeightRandomLoadBalance<T> extends AbstractLoadBalance<T> implements LoadBalance<T> {

  protected List<T> rebuildNodes(List<T> nodes) {
    List<T> nodeListTmp = new LinkedList<T>();
    nodeListTmp.addAll(nodes);
    List<T> nodeList = new ArrayList<T>();
    for (T node : nodeListTmp) {
      for (int i = 0; i < ((Node)node).getWeight(); i++) {
        nodeList.add(node);
      }
    }
    return nodeList;
  }

  @Override
  public T getNode(List<T> nodes) {
    Random random = new Random();
    int randomPos = random.nextInt(nodes.size());
    return nodes.get(randomPos);
  }
}