package com.reflections;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;

import webserver.WebServer;

public class ServerConfiguration {
	private final InetSocketAddress serverAddress;
	private static ServerConfiguration sC;
	private final String greetingMessage;
	
	
	public static void initConfiguration() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Constructor<ServerConfiguration> constructor=ServerConfiguration.class.getDeclaredConstructor(int.class,String.class);
		constructor.newInstance(8080,"Good day");
		constructor.setAccessible(true);
	}

	public ServerConfiguration(int port, String greetingMessage) {
		this.serverAddress = new InetSocketAddress("localhost",port);
		this.greetingMessage = greetingMessage;
		if(sC==null)
			sC=this;
	}

public static ServerConfiguration getInstance() {
	
	return sC;
}


	public InetSocketAddress getServerAddress() {
	return serverAddress;
}



public String getGreetingMessage() {
	return greetingMessage;
}

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {

		initConfiguration();
		WebServer web=new WebServer();
		web.startServer();
		
	}

		

}
