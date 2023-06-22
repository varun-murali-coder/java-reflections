package jsonwriter;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import data.Actor;
import data.Movie;

public class JsonWriter {

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
//		Address a=new Address("Main street",(short)11);
//		Company c=new Company("Udemy","Los Angels",a);
//		Person p=new Person("John",29,1000.55f,a,c);
//		System.out.println(objectToJson(p, 0));
		
		Actor actor1=new Actor("Elijah wood" ,new String [] {"Lord of the rings","Good son"});
		Actor actor2=new Actor("Ian Mackelin" ,new String [] {"X-men","Hobbit"});
		Actor actor3=new Actor("Grlando bloom" ,new String [] {"Kingdom of heven","Pirate of caribbean"});

		Movie m=new Movie("Lord of the rings",8.8f,new String[] {"Adventure","Drama","Action"},new 
				Actor[] {actor1,actor2,actor3});
		System.out.println(objectToJson(m, 0));

	}
	
	private static String objectToJson(Object instance,int indentSize) throws IllegalArgumentException, IllegalAccessException {
		StringBuilder sb=new StringBuilder();
		Field[] fields=instance.getClass().getDeclaredFields();
		sb.append(indent(indentSize));
		sb.append("{");
		sb.append("\n");
		for(int i=0;i<fields.length;i++) {
			Field field=fields[i];
			field.setAccessible(true);
			if(field.isSynthetic())
				continue;//ignore
			//Ignore fields with modifiers as static and transient
			int modifiers=field.getModifiers();
			if(Modifier.isTransient(modifiers)||Modifier.isStatic(modifiers))
				continue;
			sb.append(indent(indentSize+1));

			sb.append(formatStringValue(field.getName()));
			sb.append(":");
			if(field.getType().isPrimitive()) {
				sb.append(formatPrimitiveValue(field.getType(),field.get(instance)));
			}else if(field.getType().equals(String.class)) {
				sb.append(formatStringValue(field.get(instance).toString()));
			}else if(field.getType().isArray()) {
				sb.append(indent(indentSize+1));
				sb.append(arrayToJson(field.get(instance),indentSize));
			}
			else {
				sb.append(objectToJson(field.get(instance), indentSize+1));
			}
			if(i!=fields.length-1)
				sb.append(",");
			sb.append("\n");
			
		}
		sb.append(indent(indentSize));

		sb.append("}");

		return sb.toString();

	}
	private static String arrayToJson(Object object,int indentSize) throws IllegalArgumentException, IllegalAccessException {
StringBuilder sb=new StringBuilder();
int arrLength=Array.getLength(object);
Class<?>compType=object.getClass().getComponentType();
sb.append("[");
sb.append("\n");

for(int i=0;i<arrLength;i++) {
	Object element=Array.get(object, i);
	if(compType.isPrimitive())
		sb.append(formatPrimitiveValue(compType,element));
	else if(compType.equals(String.class))
		sb.append(formatStringValue(element.toString()));
	else
		sb.append(objectToJson(element, indentSize+1));
	if(i!=arrLength-1) {
		sb.append(",");
	}
	sb.append("\n");

}

sb.append("]");

		return sb.toString();
	}

	private static String formatPrimitiveValue(Class<?> type,Object instance) throws IllegalArgumentException, IllegalAccessException {
		if(type.equals(int.class) || type.equals(short.class)||
				type.equals(int.class)||type.equals(long.class)
				)
			return  instance.toString();
	else if(type.equals(float.class)|| type.equals(double.class))
	return String.format("%.02f", instance);
	throw new  RuntimeException(String.format("Type:%s not supportted",type.getTypeName()));
		
	}
	
	private static String formatStringValue(String value) {
		return String.format("\"%s\"", value);
	}
	
	private static String indent(int indentSize) {
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<indentSize;i++)
			sb.append("\t");
		return sb.toString();	
	}

}
