package com.jengine.cluster.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.EnsurePath;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * content
 *
 * @author nouuid
 * @date 1/5/2017
 * @since 0.1.0
 */
public class ZKDemo {

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
        List<String> childPathList = client.getChildren().forPath("/create");
        for (String child : childPathList) {
            System.out.println(child);
        }
    }

}
