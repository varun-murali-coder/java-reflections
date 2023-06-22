package polymorphism;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.DatabaseClient;
import http.HttpClient;
import logging.FileLogger;
import udp.UdpClient;

public class PolyMorphismImpl {
 
	public static Map<Object,Method> groupExecutors(List<Object>requestExecutors,
			List<Class<?>>methodParameterTypes){
		Map<Object,Method> instanceToMethod=new HashMap<>();
		for(Object requestExecutor:requestExecutors) {
			Method [] allMethods=requestExecutor.getClass().getDeclaredMethods();
			for(Method method:allMethods) {
				if(Arrays.asList(method.getParameterTypes()).equals(methodParameterTypes)) {
					instanceToMethod.put(requestExecutor, method);
				}
			}
		}
		return instanceToMethod;
	}
	
	public static void executeAll(Map<Object,Method>requestExecutors,String reqBody) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		for(Map.Entry<Object, Method> requestExecutorEntry:requestExecutors.entrySet()) {
			Object requestExecutor=requestExecutorEntry.getKey();
			Method method=requestExecutorEntry.getValue();
			Boolean value=(Boolean) method.invoke(requestExecutor, reqBody);
		
		}
	}
	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		DatabaseClient dbClient=new DatabaseClient();
		HttpClient client1=new HttpClient();
		HttpClient client2=new HttpClient();
		FileLogger logger =new FileLogger();
		UdpClient udpClient=new UdpClient();
		String data="Vital business data";
		
		Map<Object,Method> groupExecutors=groupExecutors(Arrays.asList(dbClient,client1,client2,udpClient,logger),
				Arrays.asList(new Class<?>[] {String.class}));
		executeAll(groupExecutors, data);


	}

}
