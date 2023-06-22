package repeatableannotations;

import repeatableannotations.Annotations.ExecuteMethodOnSchedule;
import repeatableannotations.Annotations.ScheduleExecutorClass;


@ScheduleExecutorClass
public class Cache {
	
	@ExecuteMethodOnSchedule(periodSeconds=5)
	@ExecuteMethodOnSchedule(delaySeconds=10,periodSeconds=1)
	public static void reloadCache() {
		System.out.println("Reloading the cache");
	}

}
