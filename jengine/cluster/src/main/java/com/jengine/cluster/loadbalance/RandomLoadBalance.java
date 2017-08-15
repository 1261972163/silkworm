package com.jengine.cluster.loadbalance;

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
public class RandomLoadBalance extends LoadBalanceStrategy {

  @Override
  protected List<Node> rebuildNodes(List<Node> nodes) {
    List<Node> nodeListTmp = new LinkedList<Node>();
    nodeListTmp.addAll(nodes);
    return nodeListTmp;
  }

  @Override
  protected Node getNode(List<Node> nodeList) {
    Random random = new Random();
    int randomPos = random.nextInt(nodeList.size());
    return nodeList.get(randomPos);
  }
}
