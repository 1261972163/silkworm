package com.hz.wy.service.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;


public class LogInterceptor {

	public void before() {
		System.out.println("method before");
	}
	
	public void afterreturn() {
		System.out.println("method afterreturn");
	}
	
	public void afterreturn2(String s) {
		System.out.println("method afterreturn2:" + s);
	}
	
	public void afterthrow(Exception exception) {
		System.out.println("method afterthrow");
	}
	
	public void after() {
		System.out.println("method after");
	}
	
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("method around");
		// start stopwatch
	    Object retVal = pjp.proceed();
	    // stop stopwatch
	    return retVal;
	}
	
}
