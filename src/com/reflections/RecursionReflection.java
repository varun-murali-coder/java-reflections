package com.reflections;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class RecursionReflection {

	public static void main(String[] args) {

		Set<Class<?>> values=recursiveInheritance(Vector.class);
		for(Class<?> value:values) {
			System.out.println(String.format("The values are %s\n", value.getSimpleName()));
		}
	}
	
	public static Set<Class<?>> recursiveInheritance(Class<?> inputClass){
		Set<Class<?>> inheritedClasses=new HashSet<>();
		Class<?>[] inheritedInterfaces=inputClass.getInterfaces();
		for(Class<?>inheritedInterface: inheritedInterfaces) {
			inheritedClasses.add(inheritedInterface);
			inheritedClasses.addAll(recursiveInheritance(inheritedInterface));
		}
		
		return inheritedClasses;
		
		
	}

}
