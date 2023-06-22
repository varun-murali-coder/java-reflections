package jsonwriter;

import java.lang.reflect.Field;

import data.AccountSummary;

public class ObjectSizeCalculator {
private static final long HEADER_SIZE=12;
private static final long REFERENCE_SIZE=4;

	private  static long sizeOfPrimitiveType(Class<?> primitiveType) {
		if(primitiveType.getTypeName().equals(int.class))
			return 4;
		else if(primitiveType.equals(long.class))
			return 8;
		else if(primitiveType.equals(float.class))
			return 4;
		else if(primitiveType.equals(double.class))
		return 8;
		else if (primitiveType.equals(short.class))
			return 2;
		else if(primitiveType.equals(boolean.class))
			return 1;
		throw new RuntimeException(String.format("Type:%s unsupported",primitiveType.getTypeName() ));
	}
	
	private static long  sizeOfString(String input) {
		int stringByteSize=input.getBytes().length;
		return HEADER_SIZE+REFERENCE_SIZE+stringByteSize;
	}
	
	private static long sizeOfObject(Object input) throws IllegalArgumentException, IllegalAccessException {
		long size=HEADER_SIZE+REFERENCE_SIZE;
		for(Field field:input.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			if(field.getType().isPrimitive())
				size+=sizeOfPrimitiveType(field.getType());
			else
				size+=sizeOfString((String)field.get(input));
		}
		return size;
	}


	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		AccountSummary ac=new AccountSummary("varun","murali",(short) 20,10988.00);
		System.out.println(sizeOfObject(ac));
	}

}
