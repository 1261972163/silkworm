package com.jengine.feature.serialize;

import com.jengine.feature.serialize.avro.AvroRpcRunner;
import com.jengine.feature.serialize.avro.AvroRunner;
import org.junit.Test;

/**
 * @author bl07637
 * @date 4/14/2016
 * @description
 */
public class AvroTest {
    @Test
    public void generateSchema() throws InterruptedException {
        AvroRunner avroRunner = new AvroRunner();
        avroRunner.generateSchema();
        Thread.sleep(30*1000);
    }

    @Test
    public void serializeDeserialize() throws InterruptedException {
        AvroRunner avroRunner = new AvroRunner();
        avroRunner.serializeDeserialize();
        Thread.sleep(30*1000);
    }

    @Test
    public void rpcServer() throws InterruptedException {
        AvroRpcRunner avroRpcRunner = new AvroRpcRunner();
        avroRpcRunner.rpcServer();
        Thread.sleep(30*1000);
    }

    @Test
    public void rpcClient() throws InterruptedException {
        AvroRpcRunner avroRpcRunner = new AvroRpcRunner();
        avroRpcRunner.rpcClient();
        Thread.sleep(30*1000);
    }
}
