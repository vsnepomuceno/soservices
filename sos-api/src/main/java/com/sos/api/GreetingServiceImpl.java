package com.sos.api;

public class GreetingServiceImpl implements GreetingService {

	@Override
	public String greet(String who) {
		return String.format("hello, %s!", who);
	}
}