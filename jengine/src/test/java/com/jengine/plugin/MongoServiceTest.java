package com.jengine.plugin;

import com.jengine.feature.serialize.SimpleSerialize;
import com.jengine.plugin.mongo.MongoConfig;
import com.jengine.plugin.mongo.MongoService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author nouuid
 * @date 3/28/2016
 * @description
 */
public class MongoServiceTest {

    MongoService mongoService;

    @Before
    public void init() {
        MongoConfig mongoConfig = new MongoConfig();
        mongoConfig.setDatabaseName("");
        mongoConfig.setHost("");
        mongoConfig.setPort("");
        mongoConfig.setUsername("");
        mongoConfig.setPassword("");
        mongoConfig.setTable("");
        mongoService = new MongoService(mongoConfig);
    }

    @After
    public void after() {
        mongoService.close();
    }

    @Test
    public void setTest() {
        String uuid = "";
        Object object = new Object();
        SimpleSerialize ss = new SimpleSerialize();
        mongoService.set(uuid, ss.toByte(object));
    }
}
