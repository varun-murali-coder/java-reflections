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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import annotations.InitializeClass;
import annotations.InitializeMethod;
import annotations.RetryConnection;
import annotations.ScanClass;


@ScanClass({"app.configs","app.http","app.databases","app.configs"})
public class CustomFramework {

	public static void main(String[] args) throws Throwable {
		ScanClass scanClass=CustomFramework.class.getAnnotation(ScanClass.class);
		initialize(scanClass.value());
	}
	
	public static List<Method> getAllInitializingMethods(Class<?> clazz){
		List<Method> initializingMethods=new ArrayList<>();
		for(Method method:clazz.getDeclaredMethods()) {
			if(method.isAnnotationPresent(InitializeMethod.class)) {
				initializingMethods.add(method);
			}
		}
		return initializingMethods;
	}
	
	public static void initialize(String ...packageNames) throws Throwable {
		
		List<Class<?>> classes=getAllClasses(packageNames);
		for(Class<?> clazz:classes) {
			if(!clazz.isAnnotationPresent(InitializeClass.class)) {
				continue;
			}
			List<Method> methods=getAllInitializingMethods(clazz);
			Object instance=clazz.getConstructor().newInstance();
			for(Method method:methods) {
				//method.invoke(instance);
				callInitializedMethods(instance,method);
			}
		}
	}

	private static void callInitializedMethods(Object instance, Method method) throws Throwable {
		RetryConnection retry=method.getAnnotation(RetryConnection.class);
		int numberOfRetries=retry==null?0:retry.numberOfRetries();
		while(true) {
			try {
				method.invoke(instance);
				break;
			}catch(InvocationTargetException e) {
				Throwable targetException=e.getTargetException();
				Set<Class<?>> exceptionClasses=new HashSet<>();
				for(Class<?> exClass:retry.retryExceptions()) {
					exceptionClasses.add(exClass);
				}
				if(numberOfRetries>0 && exceptionClasses.contains(targetException.getClass())) {
					numberOfRetries--;
					System.out.println("Retrying");
					try {
						Thread.sleep(retry.durationBetweenRetriesMs());
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}else if(retry!=null)
					throw new Exception(retry.failureMessage(),targetException);
			else
				throw targetException;
		}
		
	}
	}

	private static List<Class<?>> getAllClasses(String... packageNames) throws URISyntaxException, ClassNotFoundException, IOException {
List<Class<?>> allClasses=new ArrayList<>();
for(String packageName:packageNames) {
	String packageRelativePath=packageName.replace(".", "/");
	URI packageUri=CustomFramework.class.getResource(packageRelativePath).toURI();
	if(packageUri.getScheme().equals("file")) {
		Path packageFullPath=Paths.get(packageUri);
		allClasses.addAll(getAllPackageClasses(packageFullPath,packageName));
	}
	if(packageUri.getScheme().equals("jar")) {
		FileSystem fileSystem=FileSystems.newFileSystem(packageUri, Collections.emptyMap());
		Path packageJarFullPath=fileSystem.getPath(packageRelativePath);
		allClasses.addAll(getAllPackageClasses(packageJarFullPath,packageName));

		fileSystem.close();
	}

}
		return allClasses;
	}
	
	private static List<Class<?>> getAllPackageClasses(Path packagePath,String packageName) throws IOException, ClassNotFoundException{
		List<Class<?>> allClasses=new ArrayList<>();
		if(!Files.exists(packagePath))
		return Collections.emptyList();
		List<Path> files=Files.list(packagePath).filter(Files::isRegularFile).collect(Collectors.toList());
		for(Path filePath:files) {
			String fileName=filePath.getFileName().toString();
			if(fileName.endsWith(".class")) {
				String classFullName=packageName+"."+fileName.replaceFirst("\\.class", "");
				Class<?>clazz=Class.forName(classFullName);
				allClasses.add(clazz);
			}
		}
		return allClasses;

	}
}
