package com.jengine.data.nosql.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.utils.EnsurePath;
import org.junit.Before;
import org.junit.Test;

/**
 * content
 *
 * @author nouuid
 * @date 5/20/2017
 * @since 0.1.0
 */
public class NodeCacheDemo {
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
    public void nodeCache() throws Exception {
        NodeCache nodeCache = new NodeCache(client, "/create/test");
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("NodeCache changed, data is: " + new String(nodeCache.getCurrentData().getData()));
            }
        });
        nodeCache.start(true);

        client.setData().forPath("/create/test", "1111".getBytes());
        System.out.println(new String(nodeCache.getCurrentData().getData()));

        Thread.sleep(10000);
        CloseableUtils.closeQuietly(nodeCache);
        CloseableUtils.closeQuietly(client);
    }

}
