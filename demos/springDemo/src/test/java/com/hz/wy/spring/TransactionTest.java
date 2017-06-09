package com.hz.wy.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hz.wy.model.User;
import com.hz.wy.service.user.UserService;

public class TransactionTest {

	protected final Log logger = LogFactory.getLog(getClass());

	ClassPathXmlApplicationContext clientCtx = null;
	UserService userService = null;
	
	@Test
	public void test() {
		String contextPath = "META-INF/spring/mybatis-context.xml";
		clientCtx = new ClassPathXmlApplicationContext(new String[] { contextPath });
		userService = (UserService)clientCtx.getBean("userService");
//		add();
		get();
	}
	
	public void add() {
		User user = new User();
		user.setName("testname");
		user.setPassword("testpassword");
		user.setEmail("testemail");
		userService.add(user);
	}
	
	public void get() {
		User user2 = userService.get("testname");
		Assert.assertEquals("testname", user2.getName());
	}
}
