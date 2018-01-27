package com.jengine.transport.server.tomcat;

import com.jengine.common.javacommon.utils.NetworkUtil;
import com.jengine.transport.serialize.xml.DOMReader;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.Iterator;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

/**
 * 放在tomcat中加载，可获取http端口号
 *
 * @author nouuid
 * @date 6/6/2017
 * @since 0.1.0
 */
public class TomcatDemo {
    /**
     * 获取ip+port
     * @return
     */
    @Test
    public String getServerWithPort() {
        String serverWithPort = null;
        String serverIp = NetworkUtil.getHostIp();
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
     * 基于MBean获取端口号
     * @return
     */
    public void getServerWithPortBasedOnMBean() throws Exception {
        MBeanServer server = null;
        if (MBeanServerFactory.findMBeanServer(null).size() > 0) {
            server = MBeanServerFactory.findMBeanServer(null).get(0);
        }

        Set names = server.queryNames(new ObjectName("Catalina:type=Connector,*"), null);

        Iterator iterator = names.iterator();
        ObjectName name = null;
        while (iterator.hasNext()) {
            name = (ObjectName) iterator.next();
            String protocol = server.getAttribute(name, "protocol").toString();
            String scheme = server.getAttribute(name, "scheme").toString();
            String port = server.getAttribute(name, "port").toString();
            System.out.println(protocol + " : " + scheme + " : " + port);
        }
    }
}
