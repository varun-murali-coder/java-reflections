package init;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import game.Game;
import game.internal.TicTacToeGame;

public class Main {
public static <T> T createObjectRecursively(Class<?> clazz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
	Constructor<?> consructor=getFirstConstructor(clazz);
	List<Object> constructorArguments=new ArrayList<>();
	for(Class<?> argumentType:consructor.getParameterTypes()) {
		Object argumentValue=createObjectRecursively(argumentType);
		constructorArguments.add(argumentValue);
	}
	consructor.setAccessible(true);
	return (T)consructor.newInstance(constructorArguments.toArray());
}
	private static Constructor<?> getFirstConstructor(Class<?> clazz) {
Constructor<?>[] constructors=clazz.getDeclaredConstructors();
if(constructors.length==0) {
	throw new IllegalStateException(String.format("No constrcutor found"));
}
		return constructors[0];
}
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
Game game=createObjectRecursively(TicTacToeGame.class);
game.startGame();
	}

}
