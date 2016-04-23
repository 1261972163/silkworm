package com.jengine.feature.discovery;

import java.util.List;

/**
 * @author bl07637
 * @date 4/18/2016
 * @description
 * use bindingName to bind to a list of hosts
 */
public interface ServiceCache<T> extends InstanceProvider<T> {
    public List<ServiceInstance<T>> getInstances(String bindingName);
}
