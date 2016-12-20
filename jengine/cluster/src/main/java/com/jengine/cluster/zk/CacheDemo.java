package com.jengine.cluster.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;
import org.apache.curator.utils.EnsurePath;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * content
 *
 * @author bl07637
 * @date 12/20/2016
 * @since 0.1.0
 */
public class CacheDemo {
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
    public void pathChildrenCache() throws Exception {
        PathChildrenCache cache = pathChildrenCache(client, "/create", true);
        System.out.println("-----BUILD_INITIAL_CACHE");
        cache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
//        cache.start();
        List<ChildData> datas = cache.getCurrentData();
        for (ChildData data : datas) {
            System.out.println("pathcache:{" + data.getPath() + ":" + new String(data.getData())+"}");
        }
        System.out.println("-----cache.getCurrentData()");

        Thread.sleep(30*60*1000);
        CloseableUtils.closeQuietly(cache);
        CloseableUtils.closeQuietly(client);
    }

    @Test
    public void nodeCache() throws Exception {
        NodeCache nodeCache = nodeCache(client, "/create/test");
        nodeCache.start(true);

        client.setData().forPath("/create/test", "1111".getBytes());

        System.out.println(new String(nodeCache.getCurrentData().getData()));

        Thread.sleep(10000);
        CloseableUtils.closeQuietly(nodeCache);
        CloseableUtils.closeQuietly(client);
    }


    public static PathChildrenCache pathChildrenCache(CuratorFramework client, String path, Boolean cacheData) throws Exception {
        final PathChildrenCache cached = new PathChildrenCache(client, path, cacheData);
        cached.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                PathChildrenCacheEvent.Type eventType = event.getType();
                switch (eventType) {
                    case CONNECTION_RECONNECTED:
                        cached.rebuild();
                        break;
                    case CONNECTION_SUSPENDED:
                    case CONNECTION_LOST:
                        System.out.println("Connection error,waiting...");
                        break;
                    case CHILD_ADDED:
                        System.out.println("add child : {path:" + event.getData().getPath() + " data:" +
                                new String(event.getData().getData()) + "}");
                        break;
                    case CHILD_REMOVED:
                        System.out.println("remove child : {path:" + event.getData().getPath() + " data:" +
                                new String(event.getData().getData()) + "}");
                        break;
                    case CHILD_UPDATED:
                        System.out.println("update child : {path:" + event.getData().getPath() + " data:" +
                                new String(event.getData().getData()) + "}");
                        break;
                    default:
                        System.out.println("PathChildrenCache changed : {path:" + event.getData().getPath() + " data:" +
                                new String(event.getData().getData()) + "}");
                }
            }
        });
        return cached;
    }


    public static NodeCache nodeCache(CuratorFramework client, String path) {
        final NodeCache cache = new NodeCache(client, path);
        cache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("NodeCache changed, data is: " + new String(cache.getCurrentData().getData()));
            }
        });

        return cache;
    }
}
