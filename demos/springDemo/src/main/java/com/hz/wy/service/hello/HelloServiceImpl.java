package com.hz.wy.service.hello;


public class HelloServiceImpl implements HelloService {

	@Override
	public String say(String sentence) {
		System.out.println(sentence);
		return sentence;
	}

	@Override
	public void throwexception() throws Exception {
		throw new Exception();
	}

}
