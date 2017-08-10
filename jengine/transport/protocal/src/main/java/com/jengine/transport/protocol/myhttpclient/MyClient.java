package com.jengine.transport.protocol.myhttpclient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * content
 *
 * @author bl07637
 * @date 8/10/2017
 * @since 0.1.0
 */
public class MyClient {
    private final static Logger log = LoggerFactory.getLogger(MyClient.class);

    protected ContentType requestContentType = ContentType.APPLICATION_JSON.withCharset("utf-8");

    private CloseableHttpClient httpClient;

    public String execute(String restMethodName, String url, String data) throws IOException {
        HttpUriRequest request = constructHttpMethod(restMethodName, url, data);
        HttpResponse response = httpClient.execute(request);
        return deserializeResponse(response);
    }

    private String deserializeResponse(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity, "utf-8");
    }

    protected HttpUriRequest constructHttpMethod(String methodName, String url, String payload) {
        HttpUriRequest httpUriRequest = null;

        if (methodName.equalsIgnoreCase("POST")) {
            httpUriRequest = new HttpPost(url);
            log.debug("POST method created based on client request");
        } else if (methodName.equalsIgnoreCase("PUT")) {
            httpUriRequest = new HttpPut(url);
            log.debug("PUT method created based on client request");
        } else if (methodName.equalsIgnoreCase("DELETE")) {
            httpUriRequest = new HttpDelete(url);
            log.debug("DELETE method created based on client request");
        } else if (methodName.equalsIgnoreCase("GET")) {
            httpUriRequest = new HttpGet(url);
            log.debug("GET method created based on client request");
        } else if (methodName.equalsIgnoreCase("HEAD")) {
            httpUriRequest = new HttpHead(url);
            log.debug("HEAD method created based on client request");
        }
        if (httpUriRequest != null && httpUriRequest instanceof HttpEntityEnclosingRequestBase && payload != null) {
            EntityBuilder entityBuilder = EntityBuilder.create()
                    .setText(payload)
                    .setContentType(requestContentType);
            ((HttpEntityEnclosingRequestBase) httpUriRequest).setEntity(entityBuilder.build());
        }

        return httpUriRequest;
    }
}
