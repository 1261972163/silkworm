package com.jengine.transport.cluster.api;


import com.jengine.transport.cluster.api.loadbalance.RandomLoadBalance;
import com.jengine.transport.cluster.api.loadbalance.RoundRobinLoadBalance;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nouuid on 13/01/2018.
 */
public class LoadBalanceTest {

  @Test
  public void randomLoadBalance() {
    RandomLoadBalance<String> randomLoadBalance =
        new RandomLoadBalance<String>();
    List<String> nodes = initNodes();
    int count1 = 0;
    int count2 = 0;
    int count3 = 0;
    int total = 100000;
    for (int i = 0; i < total; i++) {
      String node = randomLoadBalance.getNode(nodes);
      if ("node1".equals(node)) {
        count1++;
      } else if ("node2".equals(node)) {
        count2++;
      } else if ("node3".equals(node)) {
        count3++;
      }
      System.out.println("i=" + i);
    }
    count1 = (int) (count1 / (float) total * 100);
    count2 = (int) (count2 / (float) total * 100);
    count3 = (int) (count3 / (float) total * 100);
    Assert.assertEquals(33, count1);
    Assert.assertEquals(33, count2);
    Assert.assertEquals(33, count3);
  }

  @Test
  public void roundRobinLoadBalance() {
    RoundRobinLoadBalance<String> roundRobinLoadBalance =
        new RoundRobinLoadBalance<String>();
    List<String> nodes = initNodes();
    String node1 = (String)roundRobinLoadBalance.select(nodes);
    String node2 = (String)roundRobinLoadBalance.select(nodes);
    String node3 = (String)roundRobinLoadBalance.select(nodes);
    System.out.println(node1);
    System.out.println(node2);
    System.out.println(node3);
    Assert.assertEquals("node1", node1);
    Assert.assertEquals("node2", node2);
    Assert.assertEquals("node3", node3);
  }

  private List<String> initNodes() {
    List<String> nodes = new ArrayList<String>();
    nodes.add("node1");
    nodes.add("node2");
    nodes.add("node3");
    return nodes;
  }

}
