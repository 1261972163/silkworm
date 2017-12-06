package com.jengine.data.mq.kafka.kafkaConsumerMonitor.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * @author nouuid
 */
public class Cache {
  private static final Logger logger = LoggerFactory.getLogger(Cache.class);

  private OffsetCache offsetCache;
  private OwnerCache ownerCache;
  private static Cache cache;

  public static CountDownLatch ownerCacheCountDownLatch = new CountDownLatch(1);
  public static CountDownLatch offsetCacheCountDownLatch = new CountDownLatch(1);

  public Cache(String bootstrapServers) {
    offsetCache = new OffsetCache(bootstrapServers);
    ownerCache = new OwnerCache(bootstrapServers, 3000);
  }

  public static Cache getInstance(String bootstrapServers) {
    if (cache == null) {
      synchronized (Cache.class) {
        if (cache == null) {
          cache = new Cache(bootstrapServers);
        }
      }
    }
    return cache;
  }

  public void start() {
    Thread thread1 = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          offsetCache.start();
        } catch (InterruptedException e) {
          logger.error("", e);
        }
      }
    });
    Thread thread2 = new Thread(new Runnable() {
      @Override
      public void run() {
        ownerCache.start();
      }
    });
    thread1.start();
    thread2.start();
    try {
      ownerCacheCountDownLatch.await();
      offsetCacheCountDownLatch.await();
    } catch (InterruptedException e) {
      logger.error("", e);
    }

  }

  public String getOffset(String key) {
    return offsetCache.getOffset(key);
  }

  public Set<String> getKeys() {
    return offsetCache.getKeys();
  }

  public String getConumserOwner(String key) {
    return ownerCache.getConumserOwner(key);
  }

  public void stop() {
    if (offsetCache != null) {
      offsetCache.stop();
    }
    if (ownerCache != null) {
      ownerCache.stop();
    }
  }
}
