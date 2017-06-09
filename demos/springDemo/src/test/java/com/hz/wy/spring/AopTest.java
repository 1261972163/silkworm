package com.hz.wy.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hz.wy.service.hello.HelloService;

public class AopTest {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Test
	public void test() {
		String contextPath = "META-INF/spring/aop-context.xml";
		ClassPathXmlApplicationContext clientCtx = new ClassPathXmlApplicationContext(new String[] { contextPath });
		HelloService helloService = (HelloService)clientCtx.getBean("helloService");
		Assert.assertEquals("2015-06-02", helloService.say("2015-06-02"));
		
		System.out.println("===============================================");
		
//		try {
//			helloService.throwexception();
//		} catch (Exception e) {
//			logger.info("");
//		}
	}

}