package com.jengine.cluster.distributedlock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.EnsurePath;
import org.apache.zookeeper.CreateMode;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * content
 *
 * @author bl07637
 * @date 5/19/2017
 * @since 0.1.0
 */
public class ZkDisLock {

    private CuratorFramework client;

    @Before
    public void before() throws Exception {
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);

        client = CuratorFrameworkFactory.newClient("10.45.11.84", retryPolicy);
        client.start();

        EnsurePath ensurePath = client.newNamespaceAwareEnsurePath("/create/test");
        ensurePath.ensure(client.getZookeeperClient());
    }

    @Test
    public void test1() throws Exception {
        System.out.println("-------------");
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/create/test");

        List<String> childPathList = client.getChildren().forPath("/create");
        for (String child : childPathList) {
            System.out.println(child);
        }
    }
}
