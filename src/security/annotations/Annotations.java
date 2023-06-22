package security.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Annotations {
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface MethodOperations{
		OperationType[] value();
	}
	
	@Target(ElementType.TYPE)
    @Repeatable(PermissionsContainer.class)
	public @interface Permissions{
		Role role();
		OperationType[] allowed();
	}
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface PermissionsContainer{
		Permissions[] value();
	}
}
