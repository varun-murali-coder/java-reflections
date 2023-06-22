package com.reflections;

import java.lang.reflect.Field;

public class InnerClassAccessibility {

	public static void main(String[] args) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException {
		InnerClassAccessibility.Inner i=new InnerClassAccessibility.Inner(5);
		//System.out.println(i.i);
		Class<?> inner=Class.forName("com.reflections.InnerClassAccessibility$Inner");
		Field [] fields=inner.getDeclaredFields();
		for(Field field:fields) {
			field.setAccessible(true);
			System.out.println(String.format("The fields are %s", field.getName()));
			System.out.println(String.format("Is synthetic %s", field.isSynthetic()));
			System.out.println(String.format("Field value %s", field.get(i)));
			

		}
		Object o=new String("test data");
		String s="something";
		accept1(s);
	}
	
	public static  void accept(String input) {
		System.out.println(input);
	}
	//Object o=(String)
	public static void accept1(Object input) {
		String i=(String) input;
	}
	
	public static class Inner{
		
		private int i;

		public Inner(int i) {
			this.i = i;
		}
		
		
	}

}
