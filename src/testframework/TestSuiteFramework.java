package testframework;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestSuiteFramework {

	public static void main(String[] args) throws Throwable {
		runTestSuite(PaymentServiceTest.class);
	}
	
	private static List<Method> findMethodByPrefix(Method[] methods,String prefix){
		List<Method> methodList=
		Arrays.asList(methods).stream().filter(method->method.getName().startsWith(prefix)&&method.getReturnType().equals(void.class)&&
				method.getParameterCount()==0).collect(Collectors.toList());
		return methodList;
	}
	
	private static Method findMethodByName(Method[] methods,String name) {
		for(Method method:methods) {
			if(method.getName().equals(name))
				return method;

		}
		return null;
	}
	
	public static void runTestSuite(Class<?> testClass) throws Throwable{
		Method[] allMethods=testClass.getDeclaredMethods();
		Constructor con=testClass.getConstructor();
		Object instance=con.newInstance();
			//Method m=findMethodByName(allMethods,)
		List<Method> testClassMethods=findMethodByPrefix(allMethods,"beforeClass");
		testClassMethods.addAll(findMethodByPrefix(allMethods,"setUpTest"));
		testClassMethods.addAll(findMethodByPrefix(allMethods,"test"));
		testClassMethods.addAll(findMethodByPrefix(allMethods,"afterClass"));
		for(Method testClassMethod:testClassMethods) {
			if(findMethodByName(allMethods,testClassMethod.getName()).getName().equals("beforeClass")) {
				findMethodByName(allMethods,"beforeClass").invoke(testClass);
			}else if(testClassMethod.getName().startsWith("test")) {
				Object clazz=con.newInstance();
				findMethodByName(allMethods,"setUpTest").invoke(clazz);
				findMethodByName(allMethods,testClassMethod.getName()).invoke(instance);
			}
			else if(findMethodByName(allMethods,testClassMethod.getName()).getName().equals("afterClass")) {
				findMethodByName(allMethods,"afterClass").invoke(testClass);
		}
			
		}

	}

	}
