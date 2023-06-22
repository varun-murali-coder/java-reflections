package external.impl;

import external.HttpClient;

public class HttpClientImpl implements HttpClient {

	@Override
	public void initialize() {
System.out.println("Initializing http client");
try {
	Thread.sleep(500);
}catch(InterruptedException e) {
	e.printStackTrace();
}
		
	}

	@Override
	public String sendRequest(String request) {
		System.out.println("Request send to server with data"+request);
   try {
	   Thread.sleep(1000);
   }catch(InterruptedException e) {
		e.printStackTrace();
   }
		System.out.println("Sending response");
		return "some Response data";
	}

}
