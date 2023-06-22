package configloader;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import data.GameConfig;

public class jsonDeserializer {

	private static final Path GAME_CONFIG_PATH=Paths.get("resources/game-properties.cfg");
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
System.out.println(createConfigObject(GameConfig.class,GAME_CONFIG_PATH));
	}
	
	private static <T>Object createConfigObject(Class<?> clazz,Path filePath) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException{
		Scanner sc=new Scanner(filePath);
		Constructor<?> constructor=clazz.getDeclaredConstructor();
		constructor.setAccessible(true);
		T configInstance=(T) constructor.newInstance();
		while(sc.hasNextLine()) {
			String keyValue=sc.nextLine();
			String[] keyV=keyValue.split("=");
			String propName=keyV[0];
			String propValue=keyV[1];
			Field field;
				try {
					field=clazz.getDeclaredField(propName);
					field.setAccessible(true);
					Object parsedValue;
					if(field.getType().isArray())
						parsedValue=parseArray(field.getType().getComponentType(),propValue);
					else
					parsedValue=parseValue(field.getType(),propValue);
					field.set(configInstance, parsedValue);

				} catch (NoSuchFieldException e) {
                System.out.println(String.format("Type:%s unsupported", clazz.getTypeName()));
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
		
		
		return configInstance;
	}

	private static Object parseValue(Class<?> type, String propValue) {
if(type.equals(int.class))
	return Integer.parseInt(propValue);
else if(type.equals(long.class))
	return Long.parseLong(propValue);
else if(type.equals(float.class))
	return Float.parseFloat(propValue);
else if(type.equals(double.class))
	return Double.parseDouble(propValue);
else if(type.equals(short.class))
	return Short.parseShort(propValue);
else if(type.equals(String.class))
	return propValue;
throw new RuntimeException(String.format("Type:%s unsupported", type.getTypeName()));
	}
	
	private static Object parseArray(Class<?> arrObj,String value) {
		String [] elements=value.split(",");
		Object arrObject=Array.newInstance(arrObj, elements.length);
		for (int i=0;i<elements.length;i++) {
			Array.set(arrObject, i, parseValue(arrObj,elements[i]));
		}
		return arrObject;
	}

}
