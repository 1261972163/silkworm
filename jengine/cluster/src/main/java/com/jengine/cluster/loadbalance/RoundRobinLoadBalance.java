package com.jengine.cluster.loadbalance;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 轮询法的优点在于：试图做到请求转移的绝对均衡。
 *
 * 轮询法的缺点在于：为了做到请求转移的绝对均衡，引入AtomicInteger保证修改的互斥性，对并发吞吐量有一些影响。
 *
 * @author nouuid
 * @date 3/2/2016
 * @description Round-Robin, do loop from 1 to N
 */
public class RoundRobinLoadBalance extends LoadBalanceStrategy {
    private final AtomicLong sequences = new AtomicLong();

    @Override
    protected List<Node> rebuildNodes(List<Node> nodes) {
        List<Node> nodeListTmp = new LinkedList<Node>();
        nodeListTmp.addAll(nodes);
        return nodeListTmp;
    }

    @Override
    protected Node getNode(List<Node> nodeList) {
        long index = getNodeNumber();
        return nodeList.get((int)((index) % nodeList.size()));
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
