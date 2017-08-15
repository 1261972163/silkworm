package com.jengine.data.nosql.mongo;


import com.jengine.transport.serialize.SerializeStrategy;

/**
 * Created by nouuid on 2015/5/7.
 */
public class MongoFileService {

  SerializeStrategy serializeStrategy = null;
  private MongoService mongoService;

  public MongoFileService(MongoService mongoService, SerializeStrategy serializeStrategy) {
    this.mongoService = mongoService;
    this.serializeStrategy = serializeStrategy;
  }

  public void save(String filename, Object fileData) {
    mongoService.set(filename, serializeStrategy.toByte(fileData));
  }

  public void remove(String filename) {
    mongoService.remove(filename);
  }

  public Object get(String filename) {
    return mongoService.get(filename);
  }
}
