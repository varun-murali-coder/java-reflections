package app.http;

import annotations.InitializeClass;
import annotations.InitializeMethod;

@InitializeClass
public class ServiceRegistry {
	
	@InitializeMethod
	public void registerService() {
		System.out.println("Service registered successfuly");
	}

}
