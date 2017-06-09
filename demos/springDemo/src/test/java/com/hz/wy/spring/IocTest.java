package com.hz.wy.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hz.wy.service.feature.FeatureService;

public class IocTest {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Test
	public void test() {
		String contextPath = "META-INF/spring/ioc-context.xml";
		ClassPathXmlApplicationContext clientCtx = new ClassPathXmlApplicationContext(new String[] { contextPath });
		
		FeatureService featureService = (FeatureService)clientCtx.getBean("featureService");
		Assert.assertEquals("2015-06-02", featureService.get("2015-06-02"));
	}
}
