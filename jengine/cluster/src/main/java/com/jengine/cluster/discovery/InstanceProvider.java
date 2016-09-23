package com.jengine.cluster.discovery;

import java.util.List;

/**
 * @author nouuid
 * @date 4/18/2016
 * @description
 * Provides a set of available instances for a host so that a strategy can pick one of them
 */
public interface InstanceProvider<T> {
    /**
     * Return the current available set of instances
     * @return instances
     * @throws Exception any errors
     */
    public List<ServiceInstance<T>> getInstances() throws Exception;
}