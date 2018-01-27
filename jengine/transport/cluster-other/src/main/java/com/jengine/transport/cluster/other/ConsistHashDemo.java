package com.jengine.transport.cluster.other;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 一致性hash
 *
 * 1、Node和Data都计算hash，映射到环上 2、Data如何寻找Node？Data存储在环上顺时针方向最近的Node 3、防止雪崩？在环上设置很多“虚拟节点”，数据的存储是沿着环的顺时针方向找一个虚拟节点，每个虚拟节点都会关联到一个真实节点
 *
 * @author nouuid
 * @date 5/15/2017
 * @since 0.1.0
 */
public class ConsistHashDemo {
  @Test
  public void test() {
    List<Node> nodes = new ArrayList<Node>();
    for (int i = 1; i <= 9; i++) {
      nodes.add(new Node("node" + i));
    }
    ConsistHashRing<Node> consistHashRing = new ConsistHashRing<Node>(nodes);
    System.out.println(consistHashRing.selectNode("app1").name);
    System.out.println(consistHashRing.selectNode("app2").name);
    System.out.println(consistHashRing.selectNode("app3").name);
    System.out.println(consistHashRing.selectNode("app4").name);
    System.out.println(consistHashRing.selectNode("app5").name);
  }

  class Node {
    public String name;

    public Node(String name) {
      this.name = name;
    }
  }

  /**
   * 一致性hash
   *
   * @param <T> T类封装了机器节点的信息 ，如name、password、ip、port等
   */
  class ConsistHashRing<T> {

    private final int SHARD_NUM_PER_NODE = 100; // 每个节点关联的虚拟节点个数
    private TreeMap<Long, T> shards; // 排序的虚拟节点
    private List<T> nodes; // 真实节点

    public ConsistHashRing(List<T> nodes) {
      super();
      this.nodes = nodes;
      init();
    }

    // 初始化一致性hash环
    private void init() {
      shards = new TreeMap<Long, T>();
      for (int i = 0; i != nodes.size(); ++i) { // 每个真实机器节点都需要关联虚拟节点
        final T nodeInfo = nodes.get(i);
        for (int j = 0; j < SHARD_NUM_PER_NODE; j++)
          // 一个真实机器节点关联NODE_NUM个虚拟节点
          shards.put(hash("NODE-" + i + "-SHARD-" + j), nodeInfo);
      }
    }

    public T selectNode(String key) {
      // 沿环的顺时针找到一个虚拟节点
      SortedMap<Long, T> tail = shards.tailMap(hash(key)); // tailmap是返回大于某个key的子map
      //没命中则选择第一个
      Long k = null;
      if (tail.isEmpty()) {
        k = shards.firstKey();
      } else {
        k = tail.firstKey();
      }
      // 返回该虚拟节点对应的真实机器节点的信息
      return shards.get(k);
    }

    /**
     * MurMurHash算法，是非加密HASH算法，性能很高， 比传统的CRC32,MD5，SHA-1（这两个算法都是加密HASH算法，复杂度本身就很高，带来的性能上的损害也不可避免）
     * 等HASH算法要快很多，而且据说这个算法的碰撞率很低. http://murmurhash.googlepages.com/
     */
    private Long hash(String key) {
      ByteBuffer buf = ByteBuffer.wrap(key.getBytes());
      int seed = 0x1234ABCD;
      ByteOrder byteOrder = buf.order();
      buf.order(ByteOrder.LITTLE_ENDIAN);
      long m = 0xc6a4a7935bd1e995L;
      int r = 47;
      long h = seed ^ (buf.remaining() * m);
      long k;
      while (buf.remaining() >= 8) {
        k = buf.getLong();
        k *= m;
        k ^= k >>> r;
        k *= m;
        h ^= k;
        h *= m;
      }
      if (buf.remaining() > 0) {
        ByteBuffer finish = ByteBuffer.allocate(8).order(
            ByteOrder.LITTLE_ENDIAN);
        // for big-endian version, do this first:
        // finish.position(8-buf.remaining());
        finish.put(buf).rewind();
        h ^= finish.getLong();
        h *= m;
      }
      h ^= h >>> r;
      h *= m;
      h ^= h >>> r;
      buf.order(byteOrder);
      return h;
    }

  }
}
