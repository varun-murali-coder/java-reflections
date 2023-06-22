package arrays;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

public class ArrayFlattening {

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		//Object[]  result=(Object [])concat(1,2,3,4,5,new int[] {6,7},new int [] {8,9});
		Object[]  result=(Object [])concat("a","b",new String[] {"c","d"},new String [] {"e"});

		for(Object r:result) {
			System.out.println(r);
		}

	}
	
	


	    public static Object concat(Object ... arguments) throws IllegalArgumentException, IllegalAccessException {
	    	int arrLength;
	    	int innerArrLength;
	    	int finalArrLength=0;
	    	int counter=0;
	        if (arguments.length == 0) {
	            return null;
	        }
	        
	        arrLength=Array.getLength(arguments);  
    		finalArrLength=arrLength;
	        for(int i=0;i<arrLength;i++) {
	        	if(arguments[i].getClass().isArray()) {
	    	         innerArrLength=Array.getLength(arguments[i]);
                     finalArrLength=finalArrLength+innerArrLength-1;
	        	}
	        	
	        }
	        
	        Object arrayInstance=Array.newInstance(arguments.getClass().getComponentType(), finalArrLength);
	        for(int i=0;i<arrLength;i++) {
	        	if(arguments[i].getClass().isArray()) {
	        		int l=Array.getLength(arguments[i]);
                     Object values=Array.get(arguments, i);
	        		for(int j=0;j<l;j++) {
	    	        	Array.set(arrayInstance, counter, Array.get(values, j));
	        		counter++;
	        		}
	        		
	        	}else {
	        	Array.set(arrayInstance, counter, arguments[i]);
	        	counter++;
	        	}
	        }
	        System.out.println("Length of required array is:"+finalArrLength);
	        return arrayInstance;
	    }




		

}
