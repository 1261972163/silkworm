package com.jengine.feature.discovery;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author nouuid
 * @date 4/18/2016
 * @description
 * one service name, one ServiceInstance
 */
public class ServiceCacheImpl<T> implements ServiceCache<T> {
    private final ConcurrentMap<String, ServiceInstance<T>> instanceConfigs = Maps.newConcurrentMap();
    private final ConcurrentMap<String, ConcurrentMap<String, ServiceInstance<T>>> bindingConfigs = Maps.newConcurrentMap();

    private final ReadWriteLock configLock = new ReentrantReadWriteLock(true);

    @Override
    public List<ServiceInstance<T>> getInstances() throws Exception {
        configLock.readLock().lock();
        try {
            return Lists.newArrayList(instanceConfigs.values());
        } finally {
            configLock.readLock().unlock();
        }
    }

    @Override
    public List<ServiceInstance<T>> getInstances(String bindingName) {
        if (bindingName == null || bindingName.isEmpty()) {
            return null;
        }
        configLock.readLock().lock();
        try {
            ConcurrentMap<String, ServiceInstance<T>> maps = bindingConfigs.get(bindingName);
            if (maps != null) {
                return Lists.newArrayList(maps.values());
            } else {
                return new ArrayList<ServiceInstance<T>>();
            }
        } finally {
            configLock.readLock().unlock();
        }
    }
}
