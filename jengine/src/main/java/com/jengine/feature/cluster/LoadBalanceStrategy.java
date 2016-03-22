package com.jengine.feature.cluster;

import java.util.List;
import java.util.Locale;

/**
 * @author bl07637
 * @date 3/2/2016
 * @description
 */
public abstract class LoadBalanceStrategy {
    protected void ensureNodesAreAvailable(final List<String> nodes) {
        if (nodes.isEmpty()) {
            String message = String.format(Locale.ROOT, "None of the nodes are available: %s", nodes);
            throw new RuntimeException(message);
        }
    }

    public abstract String select(List<String> nodes);
}
