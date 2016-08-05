package com.jengine.feature.cluster;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author nouuid
 * @date 3/2/2016
 * @description Round-Robin, do loop from 1 to N
 */
public class RoundRobinLoadBalance extends LoadBalanceStrategy {
    private final AtomicInteger sequences = new AtomicInteger();

    @Override
    public String select(List<String> nodes) {
        ensureNodesAreAvailable(nodes);
        int index = getNodeNumber();
        return nodes.get((index) % nodes.size());
    }

    private int getNodeNumber() {
        int index = sequences.incrementAndGet();
        if (index < 0) {
            index = 0;
            sequences.set(0);
        }
        return index;
    }

}
