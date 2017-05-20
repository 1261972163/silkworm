package com.jengine.cluster.distributedlock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.EnsurePath;
import org.apache.zookeeper.CreateMode;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * content
 *
 * @author bl07637
 * @date 5/19/2017
 * @since 0.1.0
 */
public class ZkDisLock {

    @Test
    public void test() throws InterruptedException {
        ArrayList<Node> nodes = new ArrayList<Node>();
        for (int i=1; i<5; i++) {
            Node node = new Node("NODE" + i);
            nodes.add(node);
        }
        for (Node node : nodes) {
            final Node nodeTmp = node;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        nodeTmp.start();
                        System.out.println("------" + nodeTmp.getName() + " is stoped");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }

        Thread.sleep(10*1000);
        for (Node node : nodes) {
            if (node.isLeader()) {
                node.stop();
            }
        }
        Thread.sleep(60*1000);
        System.out.println("end.");


    }

    private CuratorFramework getClient() throws Exception {
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1", 5000, 5000, retryPolicy);
        client.start();
        EnsurePath ensurePath = client.newNamespaceAwareEnsurePath("/create/test");
        ensurePath.ensure(client.getZookeeperClient());
        return client;
    }

    class Node {
        private String name;
        private CuratorFramework client;
        private volatile boolean running = false;
        private volatile boolean isLeader = false;

        public Node(String name) {
            this.name = name;
            try {
                client = getClient();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public String getName() {
            return name;
        }

        public boolean isLeader() {
            return isLeader;
        }

        public void start() throws Exception {
            if (running) {
                throw new Exception("Already in running.");
            }
            running = true;
            PathChildrenCache cache = pathChildrenCache(client, "/create/nodes", true);
            cache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
            registry();
            work();
            cache.close();
            client.close();
        }

        public PathChildrenCache pathChildrenCache(CuratorFramework client, String path, Boolean cacheData) throws Exception {
            final PathChildrenCache cached = new PathChildrenCache(client, path, cacheData);
            cached.getListenable().addListener(new PathChildrenCacheListener() {
                @Override
                public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                    PathChildrenCacheEvent.Type eventType = event.getType();
                    switch (eventType) {
                        case INITIALIZED:
                        case CONNECTION_RECONNECTED:
                            registry();
                            break;
                        case CONNECTION_SUSPENDED:
                        case CONNECTION_LOST:
                        case CHILD_ADDED:
                        case CHILD_REMOVED:
                        case CHILD_UPDATED:
                            updateLeader();
                            break;
                        default:
                            ;
                    }
                }
            });
            return cached;
        }


        private void registry() {
            System.out.println("---------" + name + " registry.");
            try {
                client.create().creatingParentsIfNeeded()
                        .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                        .forPath("/create/nodes/node", name.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void updateLeader() {
            System.out.println(name + " update");
            try {
                List<String> nodes = client.getChildren().forPath("/create/nodes");
                // 排序
                TreeSet<String> treeSet = new TreeSet<String>();
                treeSet.addAll(nodes);
                String first = treeSet.first();
                byte[] bytes = client.getData().forPath("/create/nodes/" + first);
                String leader = new String(bytes);
                // 验证
                if (name.equals(leader)) {
                    isLeader = true;
                } else {
                    isLeader = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void work() {
            while (running) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isLeader) {
                    System.out.println("#####" + name + ": I'm leader.");
                }
            }
        }

        public void stop() {
            running = false;
        }


    }

}
