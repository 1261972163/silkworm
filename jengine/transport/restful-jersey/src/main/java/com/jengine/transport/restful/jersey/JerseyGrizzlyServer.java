package com.jengine.transport.restful.jersey;

import com.jengine.transport.restful.RestfulServer;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

/**
 * @author nouuid
 * @description
 * @date 8/13/17
 */
public class JerseyGrizzlyServer implements RestfulServer {

  private String host = "localhost";
  private int port = 9998;
  private HttpServer server = null;

  public JerseyGrizzlyServer(String host, int port) {
    this.host = host;
    this.port = port;
  }

  @Override
  public void start() throws IOException {
    String url = "http://" + host + "/";
    final URI BASE_URI = UriBuilder.fromUri(url).port(port).build();
    ResourceConfig config = new ResourceConfig(HelloWorldResource.class);
    System.out.println("Starting grizzly...");
    server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, config);
    server.start();
  }

  @Override
  public void stop() {
    server.shutdown();
    System.out.println("grizzly is shutdown.");
  }
}
