package com.reflections;

import java.util.Collection;
import java.util.HashMap;

public class Exercise1 {

	public static void main(String[] args) {
		try {
			Drawable circleObject=new Drawable() {
				public int getNumberOfCorners() {
					return 0;
				}

			};

		Class<String> stringClass=String.class;
		Class<?> hashMapObject=HashMap.class;
		Class<?> squareObject=Class.forName("com.reflections.Exercise1$Square");
		//printInfo(stringClass,hashMapObject,squareObject);
		printInfo(Collection.class,int[][].class,circleObject.getClass(),Color.class,boolean.class);
		} 
		catch (ClassNotFoundException e) {
		    System.out.println("The given class in not found");
				}
	}
	private enum Color{
		BLUE,
		RED,
		GREEN
	}

	private static interface Drawable{
		public int getNumberOfCorners();
	}
	
	private static class Square implements Drawable {
		@Override
		public int getNumberOfCorners() {
			// TODO Auto-generated method stub
			return 4;
		}
	}
	
	

	private static void printInfo(Class<?> ...classes) {
		for(Class<?> clazz:classes) {
			System.out.println(String.format("class name: %s,Package name: %s", clazz.getSimpleName(),clazz.getPackage()));
			Class<?>[] implementedInterfaces=clazz.getInterfaces();
			for(Class<?>implementedInterface:implementedInterfaces ) {
				System.out.println(String.format("class %s implements %s", clazz.getSimpleName(),implementedInterface.getSimpleName()));
			}
			System.out.println("Is array:"+clazz.isArray());
			System.out.println("Is primitive:"+clazz.isPrimitive());
			System.out.println("Is interface:"+clazz.isInterface());
			System.out.println("Is anonymous:"+clazz.isAnonymousClass());
			System.out.println("Is enum:"+clazz.isEnum());
			System.out.println();
            System.out.println();



		}
	}
	
	
}
