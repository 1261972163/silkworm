---
title: CXF实践(1)——WSDL开发
categories: 服务化
tags: 
  - cxf
date: 2015/7/3 17:37:25
---

wsdl文件中存放了service的相关信息，client端通过wsdl文件获取service。wsdl文件是server端生成的，如果没有wsdl文件，自己通过server端生成一个。

# 1 生成wsdl

我们首先生成一个wsdl，如下：

## 1.1 service接口

	package com.wy.demo.cxf.wsdl.peopleservice;

	import javax.jws.WebMethod;
	import javax.jws.WebService;

	@WebService
	public interface PeopleService {

	    @WebMethod
	    String pushPeople(String name);

	    @WebMethod
	    String putPeople(String name);

	}

## 1.2 service实现

	package com.wy.demo.cxf.wsdl.peopleservice;

	public class PeopleServiceImpl implements PeopleService {

	    @Override
	    public String pushPeople(String name) {
	        System.out.println("push " + name);
	        return "push " + name;
	    }

	    @Override
	    public String putPeople(String name) {
	        System.out.println("put " + name);
	        return "put " + name;
	    }

	}

## 1.3 启动Server

	public void startServer() throws Exception {
	    System.out.println("Starting Server...");

	    PeopleService implementor = new PeopleServiceImpl();
	    String address = "http://localhost:9000/PeopleService/PeopleServicePort";
	    EndpointImpl ep = (EndpointImpl) Endpoint.publish(address, implementor);

	    System.out.println("Server is ready...");

	    Thread.sleep(30000);
	}

## 1.4 浏览器访问

浏览器中访问

	http://localhost:9000/PeopleService/PeopleServicePort?wsdl

获得wsdl内容，将该内容写入文件PeopleService.wsdl，如下：

	<wsdl:definitions xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
	    xmlns:tns="http://peopleservice.wsdl.cxf.demo.wy.com/" xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/" xmlns:ns1="http://schemas.xmlsoap.org/soap/http"
	    name="PeopleServiceImplService" targetNamespace="http://peopleservice.wsdl.cxf.demo.wy.com/">
	    <wsdl:types>
	        <xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:tns="http://peopleservice.wsdl.cxf.demo.wy.com/"
	            elementFormDefault="unqualified" targetNamespace="http://peopleservice.wsdl.cxf.demo.wy.com/" version="1.0">
	            <xs:element name="pushPeople" type="tns:pushPeople" />
	            <xs:element name="pushPeopleResponse" type="tns:pushPeopleResponse" />
	            <xs:element name="putPeople" type="tns:putPeople" />
	            <xs:element name="putPeopleResponse" type="tns:putPeopleResponse" />
	            <xs:complexType name="putPeople">
	                <xs:sequence>
	                    <xs:element minOccurs="0" name="arg0" type="xs:string" />
	                </xs:sequence>
	            </xs:complexType>
	            <xs:complexType name="putPeopleResponse">
	                <xs:sequence>
	                    <xs:element minOccurs="0" name="return" type="xs:string" />
	                </xs:sequence>
	            </xs:complexType>
	            <xs:complexType name="pushPeople">
	                <xs:sequence>
	                    <xs:element minOccurs="0" name="arg0" type="xs:string" />
	                </xs:sequence>
	            </xs:complexType>
	            <xs:complexType name="pushPeopleResponse">
	                <xs:sequence>
	                    <xs:element minOccurs="0" name="return" type="xs:string" />
	                </xs:sequence>
	            </xs:complexType>
	        </xs:schema>
	    </wsdl:types>
	    <wsdl:message name="pushPeople">
	        <wsdl:part element="tns:pushPeople" name="parameters"></wsdl:part>
	    </wsdl:message>
	    <wsdl:message name="putPeople">
	        <wsdl:part element="tns:putPeople" name="parameters"></wsdl:part>
	    </wsdl:message>
	    <wsdl:message name="putPeopleResponse">
	        <wsdl:part element="tns:putPeopleResponse" name="parameters"></wsdl:part>
	    </wsdl:message>
	    <wsdl:message name="pushPeopleResponse">
	        <wsdl:part element="tns:pushPeopleResponse" name="parameters"></wsdl:part>
	    </wsdl:message>
	    <wsdl:portType name="PeopleService">
	        <wsdl:operation name="putPeople">
	            <wsdl:input message="tns:putPeople" name="putPeople"></wsdl:input>
	            <wsdl:output message="tns:putPeopleResponse" name="putPeopleResponse"></wsdl:output>
	        </wsdl:operation>
	        <wsdl:operation name="pushPeople">
	            <wsdl:input message="tns:pushPeople" name="pushPeople"></wsdl:input>
	            <wsdl:output message="tns:pushPeopleResponse" name="pushPeopleResponse"></wsdl:output>
	        </wsdl:operation>
	    </wsdl:portType>
	    <wsdl:binding name="PeopleServiceImplServiceSoapBinding" type="tns:PeopleService">
	        <soap:binding style="document" transport="http://schemas.xmlsoap.org/soap/http" />
	        <wsdl:operation name="pushPeople">
	            <soap:operation soapAction="" style="document" />
	            <wsdl:input name="pushPeople">
	                <soap:body use="literal" />
	            </wsdl:input>
	            <wsdl:output name="pushPeopleResponse">
	                <soap:body use="literal" />
	            </wsdl:output>
	        </wsdl:operation>
	        <wsdl:operation name="putPeople">
	            <soap:operation soapAction="" style="document" />
	            <wsdl:input name="putPeople">
	                <soap:body use="literal" />
	            </wsdl:input>
	            <wsdl:output name="putPeopleResponse">
	                <soap:body use="literal" />
	            </wsdl:output>
	        </wsdl:operation>
	    </wsdl:binding>
	    <wsdl:service name="PeopleServiceImplService">
	        <wsdl:port binding="tns:PeopleServiceImplServiceSoapBinding" name="PeopleServiceImplPort">
	            <soap:address location="http://localhost:9000/PeopleService/PeopleServicePort" />
	        </wsdl:port>
	    </wsdl:service>
	</wsdl:definitions>

## 1.5 将PeopleService.wsdl文件放在合适的位置上。

# 2 纯Java开发

## 2.1 产生service的类

	package com.wy.demo.cxf.wsdl.peopleservice;

	import java.net.URL;

	import javax.xml.namespace.QName;
	import javax.xml.ws.Service;
	import javax.xml.ws.WebEndpoint;
	import javax.xml.ws.WebServiceFeature;

	import com.wy.demo.cxf.wsdl.customerservice.CustomerService;
	import com.wy.demo.cxf.wsdl.customerservice.CustomerServiceService;

	public class PeopleServiceService extends Service {

	    public final static URL WSDL_LOCATION;

	    public final static QName SERVICE = new QName("http://peopleservice.wsdl.cxf.demo.wy.com/", "PeopleServiceImplService");
	    public final static QName PeopleServicePort = new QName("http://peopleservice.wsdl.cxf.demo.wy.com/", "PeopleServiceImplPort");
	    static {
	        URL url = CustomerServiceService.class.getResource("/META-INF/cxf/wsdl/PeopleService.wsdl");

	        if (url == null) {
	            url = CustomerServiceService.class.getClassLoader().getResource("/META-INF/cxf/wsdl/PeopleService.wsdl");
	        } 
	        if (url == null) {
	            java.util.logging.Logger.getLogger(PeopleServiceService.class.getName())
	                .log(java.util.logging.Level.INFO, 
	                     "Can not initialize the default wsdl from {0}", "PeopleService.wsdl");
	        }       
	        WSDL_LOCATION = url;
	    }

	    public PeopleServiceService(URL wsdlLocation) {
	        super(wsdlLocation, SERVICE);
	    }

	    public PeopleServiceService(URL wsdlLocation, QName serviceName) {
	        super(wsdlLocation, serviceName);
	    }

	    public PeopleServiceService() {
	        super(WSDL_LOCATION, SERVICE);
	    }

	    public PeopleService getPeopleServicePort() {
	        return super.getPort(PeopleServicePort, PeopleService.class);
	    }

	    public PeopleService getPeopleServicePort(WebServiceFeature... features) {
	        return super.getPort(PeopleServicePort, PeopleService.class, features);
	    }
	}

## 2.2 测试程序

	package com.wy.demo.cxf.wsdl.test;

	import java.io.File;
	import java.net.URL;

	import javax.xml.ws.Endpoint;

	import org.apache.cxf.jaxws.EndpointImpl;
	import org.junit.Assert;
	import org.junit.Before;
	import org.junit.Test;

	import com.wy.demo.cxf.wsdl.peopleservice.PeopleService;
	import com.wy.demo.cxf.wsdl.peopleservice.PeopleServiceImpl;
	import com.wy.demo.cxf.wsdl.peopleservice.PeopleServiceService;

	public class TestPeopleService {

	    PeopleServiceService peopleServiceService;

	    @Before
	    public void startServer() throws Exception {
	        System.out.println("Starting Server...");

	        PeopleService implementor = new PeopleServiceImpl();
	        String address = "http://localhost:9000/PeopleService/PeopleServicePort";
	        EndpointImpl ep = (EndpointImpl) Endpoint.publish(address, implementor);

	        System.out.println("Server is ready...");

	        Thread.sleep(1000);
	    }

	    @Test
	    public void runClient() throws Exception { // client

	        System.out.println("Client is running...");

	        String filePath = this.getClass().getResource("/").getPath() + "META-INF/cxf/wsdl/PeopleService.wsdl";
	        File wsdlFile = new File(filePath);
	        URL wsdlURL;
	        if (wsdlFile.exists()) {
	            wsdlURL = wsdlFile.toURI().toURL();
	        } else {
	            wsdlURL = new URL(filePath);
	        }

	        // Create the service client with specified wsdlurl
	        peopleServiceService = new PeopleServiceService(wsdlURL);

	        PeopleService peopleService = peopleServiceService.getPeopleServicePort();

	        Assert.assertEquals(peopleService.pushPeople("best"), "push best");
	        Assert.assertEquals(peopleService.putPeople("best"), "put best");
	    }

	}

## 3 Annotation开发

一般，我们创建webservice（基于WSDL）的client的代码如下：

	@WebServiceClient( name = "IdentityService", targetNamespace = "http://www.project.org/is",
	                   wsdlLocation = "file:/home/joachim/apps/java/ca/pxclient/src/main/wsdl/IdentityService.wsdl" )
	public class IdentityServiceService extends Service
	{
	    public final static URL WSDL_LOCATION;
	    public final static QName SERVICE = new QName( "http://www.project.org/is", "IdentityService" );
	    public final static QName IDENTITY_SERVICE_IMPL_PORT =
	        new QName( "http://www.project.org/is", "IdentityServiceImplPort" );

	    static
	    {
	        ClassLoader cl = Thread.currentThread().getContextClassLoader();
	        if ( null == cl ) cl = IdentityServiceService.class.getClassLoader();
	        WSDL_LOCATION = cl.getResource( "be/progs/ca/pxclient/wsdl/IdentityService.wsdl" );
	    }

	    public IdentityServiceService()
	    {
	        super( WSDL_LOCATION, SERVICE );
	    }

	    /** @return returns IdentityService */
	    @WebEndpoint( name = "IdentityServiceImplPort" )
	    public IdentityService getIdentityServiceImplPort()
	    {
	        IdentityService proxy = super.getPort( IDENTITY_SERVICE_IMPL_PORT, IdentityService.class );
	        ( (BindingProvider) proxy ).getRequestContext().put( BindingProvider.ENDPOINT_ADDRESS_PROPERTY, getUrl() );
	        return proxy;
	    }

	    /**
	     * @param features A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the features parameter will have their default values.
	     * @return returns IdentityService
	     */
	    @WebEndpoint( name = "IdentityServiceImplPort" )
	    public IdentityService getIdentityServiceImplPort( WebServiceFeature... features )
	    {
	        IdentityService proxy = super.getPort( IDENTITY_SERVICE_IMPL_PORT, IdentityService.class, features );
	        ( (BindingProvider) proxy ).getRequestContext().put( BindingProvider.ENDPOINT_ADDRESS_PROPERTY, getUrl() );
	        return proxy;
	    }

	    private static String getUrl()
	    {
	        String res = System.getProperty( "ca..identityservice.url" );
	        if ( null == res ) res = "http://testportal.-dev.de/services/jbi/IdentityService/";
	        return res;
	    }
	}

# 4 参考文档

http://blog.progs.be/92/cxf-ws-client-dynamic-endpoint-and-loading-wsdl-from-the-classpath