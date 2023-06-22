package com.reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ObjectCreationConstrcutorDiscovery {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		//printConstructorInfo(Person.class); 
		Address a=createInstanceWithReflection(Address.class,"Sheksha street",121);
		Person p=createInstanceWithReflection(Person.class,"John",0,a);
		System.out.println(a);
		System.out.println(p);

	}
	
	public static <T>T createInstanceWithReflection(Class<T> clazz,Object...args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Constructor<?>[] declaredConstructors=clazz.getDeclaredConstructors();
		for(Constructor<?>declaredConstructor:declaredConstructors ) {
			
			if(args.length==declaredConstructor.getParameterTypes().length) {
				return (T)declaredConstructor.newInstance(args);
			}
			
		}
		
		return null;
	}
	
	
	public static void printConstructorInfo(Class<?> input) {
		
		Constructor<?>[] constructors=input.getDeclaredConstructors();
		System.out.println(String.format("class %s has %d declared constructors" ,input.getSimpleName(),constructors.length));
		for(int i=0;i<constructors.length;i++) {
			List<String> parameterTypeNames=Arrays.stream(constructors[i].getParameterTypes()).map(type->
			type.getSimpleName()).collect(Collectors.toList());
			System.out.println(parameterTypeNames);
					
		}
	}
	
	public static class Person{
		private  final String name;
		private final int age;
		private final Address address;
		public Person(String name, int age, Address address) {
			this.name = name;
			this.age = age;
			this.address = address;
		}
		public Person(String name) {
			this.name = name;
			this.age=0;
			this.address=null;
		}
		
		public Person(String name,int age) {
			this.name = name;
			this.age=age;
			this.address=null;
		}
		public Person() {
			this.name = "anonymous";
			this.age=0;
			this.address=null;
		}
		@Override
		public String toString() {
			return "Person [name=" + name + ", age=" + age + ", address=" + address + "]";
		}
		
		
		
	}
	
	public static class Address{
		private String street;
		private int number;
		public Address(String street, int number) {
			super();
			this.street = street;
			this.number = number;
		}
		@Override
		public String toString() {
			return "Address [street=" + street + ", number=" + number + "]";
		}
		
	}

}
