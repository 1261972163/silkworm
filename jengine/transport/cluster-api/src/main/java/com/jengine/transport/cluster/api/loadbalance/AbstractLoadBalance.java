package com.jengine.transport.cluster.api.loadbalance;

import java.util.List;
import java.util.Locale;

/**
 * @author nouuid
 * @date 3/2/2016
 * @description
 */
public abstract class AbstractLoadBalance<T> implements LoadBalance<T> {
  protected void ensureNodesAreAvailable(final List<T> nodes) {
    if (nodes.isEmpty()) {
      String message = String.format(Locale.ROOT, "None of the nodes are available: %s", nodes);
      throw new RuntimeException(message);
    }
  }

  @Override
  public T select(List<T> nodes) {
    ensureNodesAreAvailable(nodes);
    List<T> nodeList = rebuildNodes(nodes);
    return getNode(nodeList);
  }

  protected abstract List<T> rebuildNodes(List<T> nodes);

  protected abstract T getNode(List<T> nodes);

}
