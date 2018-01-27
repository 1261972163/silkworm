/**
 * Copyright (C) 2017 The BEST Authors
 */

package com.jengine.apps.apm.skywalking;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author bl07637
 */
public class Client {
  public static void main(String[] args) {
    String url="http://localhost:8080";

    CloseableHttpClient client = HttpClients.createDefault();
    HttpGet httpGet = new HttpGet(url);
    InputStream inputStream = null;
    CloseableHttpResponse response = null;
    try {
      while (true) {
        response = client.execute(httpGet);
        System.out.println(response.getStatusLine().getStatusCode());
        HttpEntity entity = response.getEntity();
        if (entity != null) {
          System.out.println(EntityUtils.toString(entity, "utf-8"));
        }
        EntityUtils.consume(entity);
        Thread.sleep(1000*5);
      }

    } catch (UnsupportedOperationException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
      if (response != null) {
        try {
          response.close();
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }

    }
  }
}
