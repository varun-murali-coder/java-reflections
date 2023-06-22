package app.databases;

import annotations.InitializeClass;
import annotations.InitializeMethod;
@InitializeClass
public class CacheLoader {
	@InitializeMethod
	public void  cacheLoader(){
		System.out.println("Connecting to gemfire cache");
	}
	
	public void reloadCache() {
		System.out.println("Time to reload cache due to technical issues");
	}
}
