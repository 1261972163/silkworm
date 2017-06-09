package com.hz.wy.spring;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hz.wy.service.HelloService;

public class AopTest {
	
	@Test
	public void test() {
		String contextPath = "META-INF/spring/aop-context.xml";
		ClassPathXmlApplicationContext clientCtx = new ClassPathXmlApplicationContext(new String[] { contextPath });
		HelloService helloService = (HelloService)clientCtx.getBean("helloService");
		helloService.say("2015-06-02");
	}

}
