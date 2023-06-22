package jsonwriter;

import java.lang.reflect.Array;

public class ArrayReader {

	public static void main(String[] args) {

		int [] input=new int[] {10,20,30,40,50};
		String [] names=new String[] {"Ravi","David","Ishan"};
		Object o=getArrayElement(names,-1);
		System.out.println((String)o);
	}
	
	  public  static Object getArrayElement(Object array, int index) {
	       /**
	        * Complete your code here
	        */ 
		  Object element=null;
		  int arrLength=Array.getLength(array);
		  if(index<0) {
			  element=Array.get(array, arrLength+index);
		  }else {
			  element=Array.get(array, index);

		  }
		  return element;
	    }

}
