package com.jengine.transport.restful.jersey;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

/**
 * content
 *
 * @author nouuid
 * @date 10/25/2016
 * @since 0.1.0
 */
public class JerseyDemo {

    private HttpServer server = null;

    public static void main(String[] args) throws IOException, InterruptedException {
        JerseyDemo jerseyDemo = new JerseyDemo();
        jerseyDemo.startServer();
        Thread.sleep(30*60*1000);
        jerseyDemo.stopServer();
    }


    private void startServer() throws IOException {
        final URI BASE_URI = getBaseURI();
        ResourceConfig config = new ResourceConfig(HelloWorldResource.class);
        System.out.println("Starting grizzly...");
        server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, config);
        server.start();
    }

    private void stopServer() {
        server.shutdown();
        System.out.println("grizzly is shutdown.");
    }

    private URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost/").port(getPort()).build();
    }

    private static int getPort() {
        int defaultPort = 9998;
        String port = System.getenv("JERSEY_HTTP_PORT");
        if (null != port) {
            try {
                return Integer.parseInt(port);
            } catch (NumberFormatException e) {
            }
        }
        return defaultPort;
    }
}
