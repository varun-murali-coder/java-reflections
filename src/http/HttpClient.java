package http;

public class HttpClient {
	
	private String serverAddress="192.180.80.20";
	
	public boolean sendRequest(String data) {
		System.out.println(String.format("Request with body %s send to server with address %s", data
				,serverAddress));
		return true;
	}

}
