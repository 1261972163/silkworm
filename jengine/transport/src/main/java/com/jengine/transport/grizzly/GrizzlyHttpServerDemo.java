package com.jengine.transport.grizzly;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;

import java.io.IOException;

/**
 * content
 *
 * @author bl07637
 * @date 10/25/2016
 * @since 0.1.0
 */
public class GrizzlyHttpServerDemo {

    public static void main(String[] args) {
        HttpServer httpServer = new HttpServer();
        NetworkListener networkListener = new NetworkListener("sample-listener", "127.0.0.1", 18888);
        ThreadPoolConfig threadPoolConfig = ThreadPoolConfig
                .defaultConfig()
                .setCorePoolSize(1)
                .setMaxPoolSize(1);
        networkListener.getTransport().setWorkerThreadPoolConfig(threadPoolConfig);
        httpServer.addListener(networkListener);
        GrizzlyHttpHandler httpHandler = new GrizzlyHttpHandler();
        httpServer.getServerConfiguration().addHttpHandler(httpHandler, new String[]{"/demo"});
        try {
            httpServer.start();
            Thread.sleep(30*1000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
