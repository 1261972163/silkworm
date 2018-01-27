/**
 * Copyright (C) 2017 The BEST Authors
 */

package com.jengine.transport.cluster.api.loadbalance;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author nouuid
 */
public class RandomLoadBalance<T> extends AbstractLoadBalance<T> implements LoadBalance<T> {

  @Override
  protected List<T> rebuildNodes(List<T> nodes) {
    List<T> nodeListTmp = new LinkedList<T>();
    nodeListTmp.addAll(nodes);
    return nodeListTmp;
  }

  @Override
  public T getNode(List<T> nodes) {
    Random random = new Random();
    int randomPos = random.nextInt(nodes.size());
    return nodes.get(randomPos);
  }
}
