package testframework;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import data.ClothingProduct;

public class TestFramework {

	public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, SecurityException {
		//testGetters(Product.class);
		//testSetters(Product.class);
		testGetters(ClothingProduct.class);
		testSetters(ClothingProduct.class);
	}
	
	private static Map<String,Method> mapMethodNameToMethod(Class<?> dataClass){
		Method[] allMethods=dataClass.getMethods();
		Map<String,Method> nameToMethod=new HashMap<>();
		for(Method method:allMethods) {
			nameToMethod.put(method.getName(), method);
		}
		
		return nameToMethod;
	}
	
	private static void testGetters(Class<?> dataClass) throws IllegalAccessException {
		//Field[] fields=dataClass.getDeclaredFields();
		List<Field> fields=getAllFields(dataClass);

		Map<String,Method> nameToMethod=mapMethodNameToMethod(dataClass);
		for(Field field:fields) {
			String getterName="get"+capitalizeFirstLetter(field.getName());
			Method getter=nameToMethod.get(getterName);
			if(!nameToMethod.containsKey(getterName)) {
				throw new IllegalAccessException(String.format("The class doesnt contain getter "
						+ "method:%s", getterName));
			}else if(!getter.getReturnType().getName().equals(field.getType().getTypeName())){
					throw new IllegalAccessException(String.format("The return type of method"+
			" %s should be %s but it is %s  ",getter.getName(),field.getType().getName(),
			getter.getReturnType().getName()));
			
			}else if(getter.getParameterCount()>0)
				throw new IllegalAccessException(String.format("Getter should not take any inputs"));
	
		}
	}
	
	
	private static void testSetters(Class<?> dataClass) throws NoSuchMethodException, SecurityException, IllegalAccessException {
		//Field[] fields=dataClass.getDeclaredFields();
		List<Field> fields=getAllFields(dataClass);
		for(Field field:fields) {
			String setterName="set"+capitalizeFirstLetter(field.getName());
			try {
			Method setter=dataClass.getMethod(setterName, field.getType());
			if(!setter.getReturnType().equals(void.class)) {
				throw new IllegalAccessException(String.format("Setter return type should be void"));
			}
			}catch(NoSuchMethodException ex) {
				throw new IllegalAccessException(String.format("Class doesnt contain setter method:%s",
						setterName));
			}
			
		}
	}

	private static String capitalizeFirstLetter(String name) {
		return name.substring(0,1).toUpperCase().concat(name.substring(1));
	}
	
	private static List<Field> getAllFields(Class<?> dataClass) {
		if(dataClass.equals(Object.class)||dataClass==null) {
			return Collections.emptyList();
			
		}
		Field[] currentFields=dataClass.getDeclaredFields();
		List<Field>superClassFields=getAllFields(dataClass.getSuperclass());
		List<Field> allFields=new ArrayList<>();
		allFields.addAll(superClassFields);
		allFields.addAll(Arrays.asList(currentFields));
		return allFields;
		
	}

}
