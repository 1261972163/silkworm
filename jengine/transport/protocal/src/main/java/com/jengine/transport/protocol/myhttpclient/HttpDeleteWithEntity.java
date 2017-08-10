package com.jengine.transport.protocol.myhttpclient;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.net.URI;

/**
 * content
 *
 * @author nouuid
 * @date 8/10/2017
 * @since 0.1.0
 */
public class HttpDeleteWithEntity extends HttpEntityEnclosingRequestBase {

    public HttpDeleteWithEntity() {
        super();
    }

    public HttpDeleteWithEntity(URI uri) {
        super();
        setURI(uri);
    }

    public HttpDeleteWithEntity(String uri) {
        super();
        setURI(URI.create(uri));
    }

    @Override
    public String getMethod() {
        return HttpDelete.METHOD_NAME;
    }
}
