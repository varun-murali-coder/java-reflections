package app.databases;

import java.io.IOException;

import annotations.InitializeClass;
import annotations.InitializeMethod;
import annotations.RetryConnection;

@InitializeClass
public class DatabaseConnection {
	private int failCount=5;
	@InitializeMethod
	@RetryConnection(numberOfRetries=10,
			durationBetweenRetriesMs=1000,
			retryExceptions= {IOException.class},
			failureMessage="Hey database issue occured"
			)
	public void connectToDatabase1() throws IOException {
		System.out.println("Connecting to DB1");
		if(failCount>0) {
		failCount--;
		throw new IOException("Some technical glitch going on at server level");
		}
		System.out.println("Connected to DB1");
	}
	@InitializeMethod
	public void connectToDatabase2() {
		System.out.println("Connecting to DB2");
	}

}
