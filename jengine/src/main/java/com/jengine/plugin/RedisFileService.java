package com.jengine.plugin;

import com.jengine.java.io.FileService;
import redis.clients.jedis.Jedis;

import java.io.ByteArrayOutputStream;

/**
 * Created by nouuid on 2015/5/7.
 */
public class RedisFileService implements FileService {

    private Jedis jedis;

    public RedisFileService(String host, int port, String password) {
        jedis = new Jedis(host, port);
        jedis.auth(password);
    }

    @Override
    public boolean save(String filename, byte[] fileData) {
        boolean flag = true;
        try {
            jedis.set(filename.getBytes(), fileData); //byte流存储，存key-value
        } catch(Exception e) {
            flag = false;
            e.printStackTrace();
        } finally {
            jedis.disconnect();
        }
        return flag;
    }

    @Override
    public boolean remove(String filename) {
        boolean flag = true;
        try {
            jedis.del(filename.getBytes());
        } catch(Exception e) {
            e.printStackTrace();
            flag = false;
        } finally {
            jedis.disconnect();
        }
        return flag;

    }

    @Override
    public ByteArrayOutputStream get(String filename) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            byte[] fileContent = jedis.get(filename.getBytes());
            baos.write(fileContent);
//			ObjectOutputStream o = new ObjectOutputStream(baos);
//	        o.writeObject(fileContent);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            jedis.disconnect();
        }
        return baos;
    }
}
