package com.jengine.java.net;

import com.jengine.feature.serialize.xml.DOMReader;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

/**
 * Created by nouuid on 2015/6/25.
 */
public class NetworkUtil {

    /**
     * 获得一个InetAddress对象，该对象含有本地机的域名和IP地址。
     * @return
     */
    public static InetAddress getInetAddress() {
        try{
            return InetAddress.getLocalHost();
        }catch(UnknownHostException e){
            System.out.println("unknown host!");
        }
        return null;
    }

    /**
     * 获取InetAddress对象的IP地址
     * @return
     */
    public static String getHostIp() {
        InetAddress netAddress = getInetAddress();
        if(null == netAddress){
            return null;
        }
        String ip = netAddress.getHostAddress(); //get the ip address
        return ip;
    }

    /**
     * 获取InetAddress对象的域名
     * @return
     */
    public static String getHostName() {
        InetAddress netAddress = getInetAddress();
        if (null == netAddress) {
            return null;
        }
        String name = netAddress.getHostName(); // get the host address
        return name;
    }

    /**
     * 获取ip+port
     * @return
     */
    public static String getServerWithPort() {
        String serverWithPort = null;
        String serverIp = getHostIp();
        String port = null;
        String tomcatHome = System.getProperty("catalina.home");
        File file = new File(tomcatHome + "/conf/server.xml");
        DOMReader domReader = new DOMReader();
        Document doc = domReader.getDocument(file);
        String tagName = "Connector";
        NodeList nodeList = domReader.getNodeList(doc, tagName);
        for(int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if("HTTP/1.1".equals(node.getAttributes().getNamedItem("protocol").getNodeValue())) {
                port = node.getAttributes().getNamedItem("port").getNodeValue();
                break;
            }
        }
        if(serverIp!=null && port!=null) {
            serverWithPort = serverIp + ":" + port;
        }

        return serverWithPort;
    }

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
