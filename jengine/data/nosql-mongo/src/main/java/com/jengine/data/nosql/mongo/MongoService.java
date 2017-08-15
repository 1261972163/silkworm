package com.jengine.data.nosql.mongo;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author nouuid
 * @date 3/25/2016
 * @description
 */
public class MongoService {

  private static final Log logger = LogFactory.getLog(MongoService.class);

  private MongoConfig mongoConfig;
  private MongoClient mongoClient;
  private DB mongoDB;
  private GridFS gridFS;

  public MongoService(MongoConfig mongoConfig) {
    this.mongoConfig = mongoConfig;
    init();
  }

  public void close() {
    mongoClient.close();
  }

  public void init() {
    try {
      mongoClient = new MongoClient(
          new ServerAddress(mongoConfig.getHost() + ":" + mongoConfig.getPort()),
          Arrays.asList(MongoCredential
              .createMongoCRCredential(mongoConfig.getUsername(), mongoConfig.getDatabase(),
                  mongoConfig.getPassword().toCharArray())),
          new MongoClientOptions.Builder().cursorFinalizerEnabled(false).build());
      mongoDB = (DB) mongoClient.getDatabase(mongoConfig.getDatabase());
//            mongoDB.authenticate(username, password.toCharArray());
      gridFS = new GridFS(mongoDB, mongoConfig.getTable());
    } catch (Exception e) {
      logger.error("", e);
    }
  }

  public boolean set(String filename, byte[] fileData) {
    boolean flag = true;
    try {
      GridFSInputFile inputFile = gridFS.createFile(new ByteArrayInputStream(fileData), filename);
      inputFile.save();
    } catch (Exception e) {
      e.printStackTrace();
      flag = false;
    } finally {
      close();
    }
    return flag;
  }

  public ByteArrayOutputStream get(String filename) {
    ByteArrayOutputStream baos = null;
    GridFSDBFile gfsFile = null;
    try {
      baos = new ByteArrayOutputStream();
      gfsFile = gridFS.findOne(filename);
      gfsFile.writeTo(baos);
    } catch (IOException e) {
      logger.error("", e);
    } finally {
      close();
    }
    return baos;
  }

  public void remove(String filename) {
    try {
      gridFS.remove(filename);
    } catch (Exception e) {
      logger.error("", e);
    } finally {
      close();
    }
  }

}
