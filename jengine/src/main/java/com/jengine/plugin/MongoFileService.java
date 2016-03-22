package com.jengine.plugin;

import com.jengine.java.io.FileService;
import com.mongodb.*;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by nouuid on 2015/5/7.
 */
public class MongoFileService implements FileService {

    private MongoClient mongoClient;
    private DB mongoDB;
    private GridFS gridFS;

    public MongoFileService(String databaseName, String host, String port, String username, String password, String table) {
        try {
            mongoClient = new MongoClient(
                    new ServerAddress(host + ":" + port),
                    Arrays.asList(MongoCredential.createMongoCRCredential(username, databaseName, password.toCharArray())),
                    new MongoClientOptions.Builder().cursorFinalizerEnabled(false).build());
            mongoDB = (DB) mongoClient.getDatabase(databaseName);
//            mongoDB.authenticate(username, password.toCharArray());
            gridFS = new GridFS(mongoDB, table);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean save(String filename, byte[] fileData) {
        boolean flag = true;
        try {
            GridFSInputFile inputFile = gridFS.createFile(new ByteArrayInputStream(fileData), filename);
            inputFile.save();
        } catch(Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            close();
        }
        return flag;
    }

    @Override
    public boolean remove(String filename) {
        boolean flag = true;
        try {
            gridFS.remove(filename);
        } catch(Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            close();
        }
        return flag;
    }

    @Override
    public ByteArrayOutputStream get(String filename) {
        ByteArrayOutputStream baos = null;
        GridFSDBFile gfsFile;
        try {
            baos = new ByteArrayOutputStream();
            gfsFile = gridFS.findOne(filename);
            gfsFile.writeTo(baos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return baos;
    }

    public void close() {
        mongoClient.close();
    }
}
