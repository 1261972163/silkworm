package com.jengine.transport.cluster.api.loadbalance;

import com.jengine.common.javacommon.utils.NetworkUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * 源地址哈希法的优点在于：保证了相同客户端IP地址将会被哈希到同一台后端服务器，直到后端服务器列表变更。根据此特性可以在服务消费者与服务提供者之间建立有状态的session会话。
 *
 * 源地址哈希算法的缺点在于：除非集群中服务器的非常稳定，基本不会上下线，否则一旦有服务器上线、下线，那么通过源地址哈希算法路由到的服务器是服务器上线、
 * 下线前路由到的服务器的概率非常低，如果是session则取不到session，如果是缓存则可能引发”雪崩”。
 *
 * @author nouuid
 * @date 12/30/2016
 * @since 0.1.0
 */
public class SourceAddressHashLoadBalance<T> extends AbstractLoadBalance<T> implements
    LoadBalance<T> {

  @Override
  protected List<T> rebuildNodes(List<T> nodes) {
    List<T> nodeListTmp = new LinkedList<T>();
    nodeListTmp.addAll(nodes);
    return nodeListTmp;
  }

  @Override
  protected T getNode(List<T> nodeList) {
    if (nodeList == null || nodeList.size() <= 0) {
      return null;
    }
    int hashCode = NetworkUtil.getHostIp().hashCode();
    if (hashCode < 0) {
      return null;
    }
    int res = hashCode % nodeList.size();
    if (res >= 0 && res <= nodeList.size() - 1) {
      return nodeList.get(res);
    } else {
      return null;
    }
  }
}