package arrays;

import java.lang.reflect.Array;

public class Main {

	public static void main(String[] args) {

		int [] oneD=new int[] {1,2,3};
		int [][] twoD=new int[][] {{1,1},{2,2},{3,3}};

		inspectArrayObject(twoD);
	}

	private static void inspectArrayObject(Object arrayObj) {
		Class<?> clazz=arrayObj.getClass();
		//System.out.println(String.format("Is array %s", clazz.isArray()));
		Class<?>arrCompType=clazz.getComponentType();
		//System.out.println(String.format("This is an array of type %s",arrCompType));
		//Getting the length and value of array
		int lengthOfArray=Array.getLength(arrayObj);
		System.out.print("[");

		for(int i=0;i<lengthOfArray;i++) {
			Object value=Array.get(arrayObj, i);
			if(value.getClass().isArray()) {
				inspectArrayObject(value);
			}else {
			System.out.print(value);
			}
			if(i!=lengthOfArray-1) {
				System.out.print(",");

			}
		}
		System.out.print("]");

	}
}
