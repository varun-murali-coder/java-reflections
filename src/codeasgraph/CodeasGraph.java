package codeasgraph;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import codeasgraph.Annotations.DependsOn;
import codeasgraph.Annotations.FinalResult;
import codeasgraph.Annotations.Input;
import codeasgraph.Annotations.Operation;

public class CodeasGraph {

	public static void main(String... args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		//List<String> bestGames=execute(new BestGameFinder());
		//System.out.println(bestGames);
		SqlQueryBuilder sqlBuilder=new SqlQueryBuilder(Arrays.asList("1","2","5"), 
				5, Arrays.asList("id","name"), "orders");
		String query=execute(sqlBuilder);
		System.out.println(query);
		
		
	}
	
	
	private static Method getFinalMethod(Class<?>clazz) {
		
		for(Method method:clazz.getDeclaredMethods()) {
			if(method.isAnnotationPresent(FinalResult.class))
				return method;
		
	}
	throw new RuntimeException("No method annotated with final result");
	}
	/**Helper methods**/
	
	private static Map<String,Method> getOperationToMethod(Class<?> clazz){
		
		Map<String,Method> getOperationToMethod=new HashMap<>();
		for(Method method:clazz.getDeclaredMethods()) {
			if(method.isAnnotationPresent(Operation.class)) {
				String operationName=method.getAnnotation(Operation.class).value();
				getOperationToMethod.put(operationName, method);
			}

		}
		
		return getOperationToMethod;
	}
	
	private static Map<String,Field> getInputToField(Class<?>clazz){
		Map<String,Field> getInputToField=new HashMap<>();
		for(Field field:clazz.getDeclaredFields()) {
			
			if(!field.isAnnotationPresent(Input.class)) 
				continue;
				String inputName=field.getAnnotation(Input.class).value();
				getInputToField.put(inputName, field);
			
		}
		
		return getInputToField;

	}
	
	/**Helper methods-end**/

	private static <T>T execute(Object instance) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Class<?> clazz=instance.getClass();
		Method getFinalMethod=getFinalMethod(clazz);
		Map<String,Method> getOperationToMethod=getOperationToMethod(clazz);
		Map<String,Field> getInputToField=getInputToField(clazz);
		return (T) executeWithDependencies(instance,getFinalMethod,getOperationToMethod,getInputToField);
	}


	private static Object executeWithDependencies(Object instance, Method currentMethod,
			Map<String, Method> getOperationToMethod, Map<String, Field> getInputToField) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
         List<Object> parameterValues=new ArrayList<>(currentMethod.getParameterCount());
         for(Parameter parameter:currentMethod.getParameters()) {
        	 Object value=null;
        	 if(parameter.isAnnotationPresent(DependsOn.class)) {
        		String operationName=parameter.getAnnotation(DependsOn.class).value();
        		value=executeWithDependencies(instance,getOperationToMethod.get(operationName),getOperationToMethod,getInputToField);
        	 }else if(parameter.isAnnotationPresent(Input.class)) {
        		 String inputName=parameter.getAnnotation(Input.class).value();
        		 Field field=getInputToField.get(inputName);
        		 field.setAccessible(true);
        		 value=field.get(instance);
        	 }
        	 parameterValues.add(value);

        	 
         }
         //To make it available to call private methods also
         currentMethod.setAccessible(true);
		return currentMethod.invoke(instance, parameterValues.toArray());
	}
	

	
}
