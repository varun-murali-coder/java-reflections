import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import repeatableannotations.Annotations.ExecuteMethodOnSchedule;
import repeatableannotations.Annotations.ScanPackages;
import repeatableannotations.Annotations.ScheduleExecutorClass;

@ScanPackages("repeatableannotations")
public class ScheduledExecutorFramework {

	public static void main(String[] args) throws URISyntaxException, ClassNotFoundException, IOException {

		schedule();
	}
	
	private static List<Method> getScheduledExecutorMethods(List<Class<?>> allClasses){
		
		List<Method> scheduledMethods=new ArrayList<>();
		for(Class<?> clazz:allClasses) {
			if(!clazz.isAnnotationPresent(ScheduleExecutorClass.class))
				continue;
				for(Method method:clazz.getDeclaredMethods()) {
					if(method.getAnnotationsByType(ExecuteMethodOnSchedule.class).length!=0)
						scheduledMethods.add(method);
				}
		}
		
		return scheduledMethods;
	}
	
	private static void schedule() throws URISyntaxException, ClassNotFoundException, IOException {
		ScanPackages scanPackages=ScheduledExecutorFramework.class.getAnnotation(ScanPackages.class);
		List<Class<?>> allClasses=getAllClasses(scanPackages.value());
		List<Method> getScheduledExecutorMethods=getScheduledExecutorMethods(allClasses);
        for(Method method:getScheduledExecutorMethods) {
        	scheduleMethod(method);
        }
	}

	private static void scheduleMethod(Method method) {
		ExecuteMethodOnSchedule[]  executeMethodOnSchedules=method.getAnnotationsByType(ExecuteMethodOnSchedule.class);
		ScheduledExecutorService schedules =Executors.newSingleThreadScheduledExecutor();
		for(ExecuteMethodOnSchedule executeMethodOnSchedule:executeMethodOnSchedules ) {
			schedules.scheduleAtFixedRate(()->runWithSchedule(method), executeMethodOnSchedule.delaySeconds(), executeMethodOnSchedule.periodSeconds(), TimeUnit.SECONDS);
		}
		
	}

	private static void runWithSchedule(Method method)  {
Date date=new Date();
SimpleDateFormat dateFormat=new SimpleDateFormat("HH:mm:ss");
System.out.println(String.format("Executing at %s", dateFormat.format(date)));
	try {
		method.invoke(null);
	} catch (IllegalAccessException | IllegalArgumentException|InvocationTargetException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	}

	private static List<Class<?>> getAllClasses(String value) throws URISyntaxException, ClassNotFoundException, IOException {
    List<Class<?>> allClasses=new ArrayList<>();
    URI packageUri=ScheduledExecutorFramework.class.getResource(value).toURI();
    if(packageUri.getScheme().equals("file")) {
    	Path packageFullPath=Paths.get(packageUri);
    	allClasses.addAll(getPackageAllClasses(packageFullPath,value));
    }else if(packageUri.getScheme().equals("jar")) {
    	FileSystem fileSystem=FileSystems.newFileSystem(packageUri, Collections.emptyMap());
    	Path packageJarFile=fileSystem.getPath(value);
    	allClasses.addAll(getPackageAllClasses(packageJarFile,value));
    	fileSystem.close();
    }

		return allClasses;
	}
	
	private static List<Class<?>> getPackageAllClasses(Path packageFullPath,String packageName) throws IOException, ClassNotFoundException{
		List<Class<?>> classes=new ArrayList<>();
		if(!Files.exists(packageFullPath))
			return Collections.emptyList();
		List<Path> files=Files.list(packageFullPath).filter(Files::isRegularFile).collect(
				Collectors.toList());
		for(Path filePath:files) {
			String fileName=filePath.getFileName().toString();
			if(fileName.endsWith("class")) {
				String classFullPath=packageName+"."+fileName.replaceFirst("\\.class$", "");
				Class<?> clazz=Class.forName(classFullPath);
				classes.add(clazz);
			}
		}
		
		return classes;
		
	}

}
