package com.jengine.plugin;

import com.jengine.java.io.FileService;
import org.junit.Test;

import com.jengine.plugin.RedisFileService;

/**
 * Created by nouuid on 2015/5/7.
 */
public class RedisFileServiceTest {

    @Test
    public void test() {
        String host = "127.0.0.1";
        int port = 6001;
        String password = "test123";//√‹¬Î(secret key)
        FileService fs = new RedisFileService(host, port, password);

    }
}
