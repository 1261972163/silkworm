package com.jengine.cluster;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.EnsurePath;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * content
 *
 * @author bl07637
 * @date 5/19/2017
 * @since 0.1.0
 */
public class LeaderElection {

    @Test
    public void test() throws InterruptedException {
        // 5个节点Node
        ArrayList<Node> nodes = new ArrayList<Node>();
        for (int i=1; i<5; i++) {
            Node node = new Node("NODE" + i);
            nodes.add(node);
        }
        // Node启动
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
        // 只有一个Leader
        System.out.println("-----------------------");
        for (Node node : nodes) {
            System.out.println(node.getName() + ":" + node.isLeader());
        }
        System.out.println("-----------------------");
        // 将Leader节点关闭掉
        for (Node node : nodes) {
            if (node.isLeader()) {
                node.stop();
            }
        }
        Thread.sleep(60 * 1000);
        // 重新选出Leader，只有一个Leader
        System.out.println("-----------------------");
        for (Node node : nodes) {
            System.out.println(node.getName() + ":" + node.isLeader());
        }
        System.out.println("-----------------------");
        System.out.println("end.");
    }

    private CuratorFramework getClient() throws Exception {
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("10.45.11.84", 5000, 5000, retryPolicy);
        client.start();
        EnsurePath ensurePath = client.newNamespaceAwareEnsurePath("/app/nodes");
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
            PathChildrenCache cache = pathChildrenCache(client, "/app/nodes", true);
            cache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
            registry();
            while (running) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            isLeader = false;
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
                        .forPath("/app/nodes/node", name.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void updateLeader() {
            System.out.println(name + " update");
            try {
                List<String> nodes = client.getChildren().forPath("/app/nodes");
                // 排序
                TreeSet<String> treeSet = new TreeSet<String>();
                treeSet.addAll(nodes);
                String first = treeSet.first();
                byte[] bytes = client.getData().forPath("/app/nodes/" + first);
                String leaderName = new String(bytes);
                // 验证
                if (name.equals(leaderName)) {
                    isLeader = true;
                } else {
                    isLeader = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void stop() {
            running = false;
        }


    }

}
