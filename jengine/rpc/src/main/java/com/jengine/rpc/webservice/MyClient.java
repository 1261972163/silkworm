package com.jengine.rpc.webservice;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * content
 *
 * @author bl07637
 * @date 9/12/2016
 * @since 0.1.0
 */
public class MyClient {

    public void invoke(int result) {
        try {
            //创建访问wsdl服务地址的URL
            URL url = new URL("http://localhost:8888/ns?wsdl");
            //通过QName指明服务的具体信息
            QName sName = new QName("http://webservice.feature.jengine.com/", "MyServiceImplService");
            //创建服务
            Service service = Service.create(url, sName);
            //实现接口
            MyService ms = service.getPort(MyService.class);
            result = ms.add(12, 13);
            //以上服务有问题，依然依赖于Myservice接口
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
