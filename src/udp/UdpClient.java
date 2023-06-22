package udp;

public class UdpClient {

	public void sendAndFormat(String reqPayload) {
		System.out.println(String.format("Request %s was send thru udp", reqPayload));
	}
}
