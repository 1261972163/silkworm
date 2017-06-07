package com.jengine.transport.serialize.avro;

import org.junit.Test;

/**
 * @author nouuid
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
