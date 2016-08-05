package com.jengine.feature.serialize.avro;

import org.apache.avro.Protocol;
import org.apache.avro.Protocol.Message;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.ipc.HttpServer;
import org.apache.avro.ipc.generic.GenericResponder;

/**
 * @author nouuid
 * @date 4/14/2016
 * @description
 */
public class AvroRpcServer extends GenericResponder {

    private Protocol protocol = null;
    private int port;

    public AvroRpcServer(Protocol protocol, int port) {
        super(protocol);
        this.protocol = protocol;
        this.port = port;
    }

    @Override
    public Object respond(Message message, Object request) throws Exception {
        GenericRecord req = (GenericRecord) request;
        GenericRecord reMessage = null;
        if (message.getName().equals("sendMessage")) {
            GenericRecord msg = (GenericRecord)req.get("message");
            System.out.print("accept message：");
            System.out.println(msg);
            reMessage =  new GenericData.Record(protocol.getType("message"));
            reMessage.put("name", "apple");
            reMessage.put("type", 100);
            reMessage.put("price", 4.6);
            reMessage.put("valid", true);
            reMessage.put("content", "new goods");
        }
        return reMessage;
    }

    public void run() {
        System.out.println("server start");
        try {
            HttpServer server = new HttpServer(this, port);
            server.start();
//            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
