package com.jengine.common.webSpider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * content
 *
 * @author nouuid
 * @date 6/6/2017
 * @since 0.1.0
 */
public class WebSpider {

    /**
     * 抓取网页内容
     * @param urlName
     */
    public static StringBuffer readByURL(String urlName){
        StringBuffer result = new StringBuffer();
        try{
            URL url = new URL(urlName);//由网址创建URL对象
            URLConnection tc = url.openConnection();//获得URLConnection对象
            tc.connect();//设置网络连接
            InputStreamReader in = new InputStreamReader(tc.getInputStream());
            BufferedReader dis = new BufferedReader(in);//采用缓冲式输入
            String inline;
            while((inline = dis.readLine())!=null){
                result.append(inline+"\n");
            }
            dis.close();//网上资源使用结束后，数据流及时关闭
        }catch(MalformedURLException e){
            e.printStackTrace();
        } catch(IOException e2) {
            e2.printStackTrace();
        }
        return result;
    }
}
