package com.jengine.transport.protocol.http;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * content
 *
 * @author bl07637
 * @date 8/10/2017
 * @since 0.1.0
 */
public class HttpNoRunTest {

    @Test
    public void put() throws IOException {
        String url="http://localhost:9998/helloworld/put";
        String payload = "this is a test";
        ContentType requestContentType = ContentType.APPLICATION_JSON.withCharset("utf-8");
        EntityBuilder entityBuilder = EntityBuilder.create()
                .setText(payload)
                .setContentType(requestContentType);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);
        httpPut.setEntity(entityBuilder.build());
        CloseableHttpResponse response = null;
        response = client.execute(httpPut);
        System.out.println(response.getStatusLine().getStatusCode());
    }

    @Test
    public void get() {
//        String url="http://www.baidu.com";
        String url="http://localhost:9998/helloworld";

        // 1.使用默认的配置的httpclient
        CloseableHttpClient client = HttpClients.createDefault();
        //2.使用get方法
        HttpGet httpGet = new HttpGet(url);
        InputStream inputStream = null;
        CloseableHttpResponse response = null;
        try {
            //3.执行请求，获取响应
            response = client.execute(httpGet);


            //看请求是否成功，这儿打印的是http状态码
            System.out.println(response.getStatusLine().getStatusCode());
            //4.获取响应的实体内容，就是我们所要抓取得网页内容
            HttpEntity entity = response.getEntity();

            //5.将其打印到控制台上面
            //方法一：使用EntityUtils
            if (entity != null) {
                System.out.println(EntityUtils.toString(entity, "utf-8"));
            }
            EntityUtils.consume(entity);

            //方法二  :使用inputStream
           /* if (entity != null) {
                inputStream = entity.getContent();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);

                }
            }*/

        } catch (UnsupportedOperationException | IOException e) {
            // TODO Auto-generated catch block
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
