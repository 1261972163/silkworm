package com.jengine.cluster.cache;

import com.jengine.cluster.loadbalance.LoadBalanceStrategy;
import com.jengine.cluster.loadbalance.Node;
import com.jengine.cluster.loadbalance.RoundRobinLoadBalance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author nouuid
 * @date 4/18/2016
 * @description
 */
public class HostnameCache {
    private List<Node> hostnames = new ArrayList<Node>();

    private final  ReadWriteLock       hostnameLock        = new ReentrantReadWriteLock(true);
    private static LoadBalanceStrategy loadBalanceStrategy = new RoundRobinLoadBalance();

    private static HostnameCache hostnameCache = new HostnameCache();

    private HostnameCache() {

    }

    public static HostnameCache getInstance() {
        return hostnameCache;
    }

    public String getString() {
        try {
            hostnameLock.readLock().lock();
            if (hostnames == null || hostnames.size() <= 0) {
                return null;
            }
            return loadBalanceStrategy.select(hostnames).getHostIp();
        } finally {
            hostnameLock.readLock().unlock();
        }
    }

    public void addHostname(String hostname) {
        try {
            hostnameLock.writeLock().lock();
            Thread.sleep(1000);
            Node node = new Node();
            node.setHostIp(hostname);
            hostnames.add(node);
            System.out.println("after add " + hostname);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(showHostnames());
            hostnameLock.writeLock().unlock();
        }
    }

    public void removeHostname(String hostname) {
        try {
            hostnameLock.writeLock().lock();
            hostnames.remove(hostname);
            System.out.println("                                        " + "after remove " + hostname);
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("                                        " + showHostnames());
            hostnameLock.writeLock().unlock();
        }
    }

    private String showHostnames() {
        try {
            hostnameLock.readLock().lock();
            return hostnames.toString();
        } finally {
//            System.out.println("after show");
            hostnameLock.readLock().unlock();
        }
    }
}
