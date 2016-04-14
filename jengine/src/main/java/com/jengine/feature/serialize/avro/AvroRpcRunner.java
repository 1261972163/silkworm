package com.jengine.feature.serialize.avro;

/**
 * @author bl07637
 * @date 4/14/2016
 * @description
 */
public class AvroRpcRunner {

    public void rpcServer() throws InterruptedException {
        String path = AvroRpcRunner.class.getResource("/").getPath() + "avro/message.avpr";
        new AvroRpcServer(AvroUtil.getProtocol(path), 9090).run();
    }

    public void rpcClient() throws InterruptedException {
        String path = AvroRpcRunner.class.getResource("/").getPath() + "avro/message.avpr";
        new AvroRpcClient(AvroUtil.getProtocol(path), "127.0.0.1", 9090, 5).run();
    }

}
