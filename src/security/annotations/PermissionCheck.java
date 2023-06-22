package security.annotations;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import security.annotations.Annotations.MethodOperations;
import security.annotations.Annotations.Permissions;

public class PermissionCheck {

	public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
User user=new User("Varun",Role.MANAGER);
CompanyDataSource ds=new CompanyDataSource(user);
System.out.println(ds.readAllAccounts());
	}

	public static void checkPermissions(Object callerObject, String methodName) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
    User user=getLoggedInUser(callerObject);
    Method callingMethod=getCallingMethod(callerObject,methodName);
    Permissions[] permissions=getAllClassAnnotatedPermissions(callerObject);
	OperationType[] methodOperationTypes=getMethodOperationType(callingMethod);
	List<OperationType> userAllowedOperations=findUserAllowedOperations(permissions,user);
	for(OperationType methodOperationType:methodOperationTypes ) {
		if(!userAllowedOperations.contains(methodOperationType))
			throw new PermissionDeniedException();
	}
		
	}

	private static List<OperationType> findUserAllowedOperations(Permissions[] permissions, User user) {
          for(Permissions permission:permissions) {
        	  if(user.getRole().equals(permission.role()))
        		  return Arrays.asList(permission.allowed());
          }
		return Collections.emptyList();
	}

	private static OperationType[] getMethodOperationType(Method callingMethod) {
		return callingMethod.getAnnotation(MethodOperations.class).value();
	}

	private static Permissions[] getAllClassAnnotatedPermissions(Object callerObject) {
		return callerObject.getClass().getAnnotationsByType(Permissions.class);
	}

	private static Method getCallingMethod(Object callerObject, String methodName) {
		return Arrays.stream(callerObject.getClass().getDeclaredMethods())
				.filter(method->method.getName().equals(methodName)).findFirst()
				.orElseThrow(()->new IllegalStateException("method not annotated with operations"));
	}

	private static User getLoggedInUser(Object callerObject) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field=callerObject.getClass().getDeclaredField("user");
		field.setAccessible(true);
		return (User) field.get(callerObject);
	}

	

}
