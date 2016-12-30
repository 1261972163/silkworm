package com.jengine.cluster.loadbalance;

import java.util.List;
import java.util.Locale;

/**
 * @author nouuid
 * @date 3/2/2016
 * @description
 */
public abstract class LoadBalanceStrategy {
    protected void ensureNodesAreAvailable(final List<Node> nodes) {
        if (nodes.isEmpty()) {
            String message = String.format(Locale.ROOT, "None of the nodes are available: %s", nodes);
            throw new RuntimeException(message);
        }
    }

    public Node select(List<Node> nodes) {
        ensureNodesAreAvailable(nodes);
        List<Node> nodeList = rebuildNodes(nodes);
        return getNode(nodeList);
    }

    protected abstract List<Node> rebuildNodes(List<Node> nodes);

    protected abstract Node getNode(List<Node> nodeList);

}
