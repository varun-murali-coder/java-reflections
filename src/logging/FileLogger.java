package logging;

public class FileLogger {
	
	public void sendRequest(String data) {
		System.out.println(String.format("Data %s logged to file system", data));
	}

}
