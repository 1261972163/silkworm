package com.jengine.transport.restful.jersey;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * The Java class will be hosted at the URI path "/helloworld"
 *
 * @author bl07637
 * @date 10/25/2016
 * @since 0.1.0
 */
@Path("/helloworld")
public class HelloWorldResource {
    @GET
    @Produces("text/plain")
    public String getClichedMessage() {
        // curl -XGET http://localhost:9998/helloworld
        return "Hello World!";
    }

    @PUT
    @Path("/put")
    public String put(String s) {
        // curl -XPUT 'http://localhost:9998/helloworld/put' -d 'this is a test'
        System.out.println(s);
        return s;
    }
}
