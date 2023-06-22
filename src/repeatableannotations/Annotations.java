package repeatableannotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Annotations {

    @Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface ScheduleExecutorClass{
		
	}
    
    @Target(ElementType.METHOD)
    @Repeatable(ExecutorSchedules.class)
	public @interface ExecuteMethodOnSchedule{
		int periodSeconds();
		int delaySeconds() default 1;
	}
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface ExecutorSchedules{
    	ExecuteMethodOnSchedule[] value();
    }
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface ScanPackages{
    	String value();
    }
}
