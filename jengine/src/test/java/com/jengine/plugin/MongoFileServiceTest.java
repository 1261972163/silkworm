package com.jengine.plugin;

import com.jengine.java.io.FileService;
import org.junit.Test;

/**
 * Created by nouuid on 2015/5/7.
 */
public class MongoFileServiceTest {

    @Test
    public void connectTest() {
        String databaseName = "resumedb";
        String host = "127.0.0.1";
        String port = "27017";
        String username = "test";//ÓÃ»§Ãû(api key);
        String password = "test123";//ÃÜÂë(secret key)
        String table = "test";
        FileService fs = new MongoFileService(databaseName, host, port, username, password, table);

    }
}
