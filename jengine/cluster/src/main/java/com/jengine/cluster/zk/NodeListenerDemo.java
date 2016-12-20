package com.jengine.cluster.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * content
 *
 * @author bl07637
 * @date 12/20/2016
 * @since 0.1.0
 */
public class NodeListenerDemo {

    private CuratorFramework curatorFramework;
    private String statusPath;

    public void test() throws Exception {

        statusPath = "/local/test";
        curatorFramework = CuratorFrameworkFactory.newClient("127.0.0.1",
                30000,
                30000,
                new ExponentialBackoffRetry(3, Integer.MAX_VALUE));
        NodeCache statusCache = new NodeCache(curatorFramework, statusPath);
        StatusListener statusListener = new StatusListener(statusCache, curatorFramework, statusPath);
        statusCache.getListenable().addListener(statusListener);
        statusCache.start();

        Thread.sleep(3*60*1000);
    }

}

class StatusListener implements NodeCacheListener {
    private String statusPath;
    private NodeCache statusCache;
    private CuratorFramework curatorFramework;

    public StatusListener(NodeCache statusCache, CuratorFramework curatorFramework, String statusPath) {
        this.statusCache = statusCache;
        this.curatorFramework = curatorFramework;
        this.statusPath = statusPath;
    }

    @Override
    public void nodeChanged() throws Exception {
        if (curatorFramework.checkExists().forPath(statusPath) == null) {
            return;
        }
        byte modifiedValues[] = statusCache.getCurrentData().getData();
        if (modifiedValues==null || modifiedValues.length<=0) {
            return;
        }
        // process modifiedValues
    }


}
