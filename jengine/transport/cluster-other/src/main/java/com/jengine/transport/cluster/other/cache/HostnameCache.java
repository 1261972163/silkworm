package com.jengine.transport.cluster.other.cache;

import com.jengine.transport.cluster.api.loadbalance.LoadBalance;
import com.jengine.transport.cluster.api.loadbalance.RoundRobinLoadBalance;

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

  private static LoadBalance<String> loadBalance = new RoundRobinLoadBalance<String>();
  private static HostnameCache hostnameCache = new HostnameCache();

  private final ReadWriteLock hostnameLock = new ReentrantReadWriteLock(true);
  private List<String> hostnames = new ArrayList<String>();

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
      return loadBalance.select(hostnames);
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
      System.out.println("                                        after remove " + hostname);
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
