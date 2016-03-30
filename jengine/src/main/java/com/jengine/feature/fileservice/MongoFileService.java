package com.jengine.feature.fileservice;

import com.jengine.feature.serialize.SerializeStrategy;
import com.jengine.plugin.mongo.MongoService;

/**
 * Created by nouuid on 2015/5/7.
 */
public class MongoFileService implements FileService {

    private MongoService mongoService;
    SerializeStrategy serializeStrategy = null;

    public MongoFileService(MongoService mongoService, SerializeStrategy serializeStrategy) {
        this.mongoService = mongoService;
        this.serializeStrategy = serializeStrategy;
    }

    @Override
    public void save(String filename, Object fileData) {
        mongoService.set(filename, serializeStrategy.toByte(fileData));
    }

    @Override
    public void remove(String filename) {
        mongoService.remove(filename);
    }

    @Override
    public Object get(String filename) {
       return mongoService.get(filename);
    }
}
