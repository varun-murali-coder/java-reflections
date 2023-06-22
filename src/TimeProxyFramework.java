import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import external.DatabaseReader;
import external.HttpClient;
import external.impl.DatabaseReaderImpl;
import external.impl.HttpClientImpl;

public class TimeProxyFramework {

	public static void main(String[] args) throws InterruptedException {
		//useHttpClient(new HttpClientImpl());
		//HttpClient client=createProxy(new HttpClientImpl());
		//useHttpClient(client);
		//useDatabaseReader(new DatabaseReaderImpl());
		useDatabaseReader(createProxy(new DatabaseReaderImpl()));
	}
	
	public static void useHttpClient(HttpClient client) {
		client.initialize();
		String response=client.sendRequest("some request");
		System.out.println(String.format("The response received is:%s", response));
	}
	
	public static void useDatabaseReader(DatabaseReader reader) throws InterruptedException {
		try {
		int numberOfRows=reader.rowInTables("Orders");
		System.out.println(String.format("Number of rows in table order is %d", numberOfRows));

		}catch(IOException e) {
			System.out.println(String.format("Caught the io exception as expected"));
		}
		String[] columns=reader.readColumns("select * from orders");
		System.out.println(String.format("The column names are %s", String.join(",", columns)));
	}
	
	private static class TimeMeasuringProxyHandler implements InvocationHandler{
 private final Object originalObject;
 
 
 public TimeMeasuringProxyHandler(Object originalObject) {
	 this.originalObject=originalObject;
 }
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
           Object result;
           long startTime=System.currentTimeMillis();
           try {
           result=method.invoke(originalObject, args);
           }catch(InvocationTargetException e) {
        	   throw e.getTargetException();
           }
           long endTime=System.currentTimeMillis();
           
           System.out.println(String.format("The execution time of method %s() is %dms", method.getName(),endTime-startTime));
			
			return result;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T createProxy(Object argObj) {
		return (T)Proxy.newProxyInstance(argObj.getClass().getClassLoader(), 
				argObj.getClass().getInterfaces(), new TimeMeasuringProxyHandler(argObj));
	}

}
