package com.jengine.feature.memoryCache;

import com.jengine.feature.cluster.LoadBalanceStrategy;
import com.jengine.feature.cluster.RoundRobinLoadBalance;

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
    private List<String> hostnames = new ArrayList<String>();

    private final ReadWriteLock hostnameLock = new ReentrantReadWriteLock(true);
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
            if (hostnames ==null || hostnames.size()<=0) {
                return null;
            }
            return loadBalanceStrategy.select(hostnames);
        } finally {
            hostnameLock.readLock().unlock();
        }
    }

    public void addHostname(String hostname) {
        try {
            hostnameLock.writeLock().lock();
            Thread.sleep(1000);
            hostnames.add(hostname);
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
