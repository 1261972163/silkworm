package com.jengine.plugin.zookeeper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;

/**
 * Created by nouuid on 2015/5/8.
 */
public class ZookeeperServiceImpl implements ZookeeperService {

    protected final Log logger = LogFactory.getLog(getClass());

    private ZookeeperConfig zookeeperConfig;

    private CuratorFramework curatorFramework;

    public ZookeeperServiceImpl(ZookeeperConfig zookeeperConfig) {
        this.zookeeperConfig = zookeeperConfig;
        curatorFramework = CuratorFrameworkFactory.newClient(
                zookeeperConfig.getConnectString(),
                zookeeperConfig.getSessionTimeoutMs(),
                zookeeperConfig.getConnectionTimeoutMs(),
                zookeeperConfig.getRetryPolicy());
    }
}
