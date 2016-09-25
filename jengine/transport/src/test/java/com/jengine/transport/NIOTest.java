package com.jengine.transport;

import com.jengine.transport.nio.NIOClient;
import com.jengine.transport.nio.NIOServer;
import org.junit.Test;

import java.io.IOException;

/**
 * @author nouuid
 * @description
 * @date 9/24/16
 */
public class NIOTest {

    @Test
    public void server() throws IOException {
        NIOServer nioServer = new NIOServer(8000);
        nioServer.start();
    }

    @Test
    public void client() throws IOException {
        NIOClient nioClient = new NIOClient("127.0.0.1", 8000);
        nioClient.start();
    }
}
