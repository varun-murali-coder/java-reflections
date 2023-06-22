package field.inspection;

import java.lang.reflect.Field;

public class Main {

	//public static class Movie e
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
		//printFieldsInfo(Movie.MovieStats.class);
		//printFieldsInfo(Category.class);
		Movie m=new Movie("Lord of the rings",2001,true,Category.ADVENTURE,19.99);
		printFieldsInfo(m.getClass(),m);
	}
	public static <T> void printFieldsInfo(Class<? extends T>clazz,T instance) throws IllegalArgumentException, IllegalAccessException {
		for(Field field:clazz.getDeclaredFields()) {
			System.out.println(String.format("The field is:%s ,type:%s", field.getName(),field.getType().getName()));
			System.out.println(String.format("Is synthetic field:%s", field.isSynthetic()));
			field.setAccessible(true);
			System.out.println(String.format("The field values are:%s", field.get(instance)));

		}
	}
	
	public static class Movie extends Product{
 public static final double MIN_PRICE=10.99;
 private boolean isReleased;
 private Category category;
 protected double actualPrice;
public Movie(String name, int year, boolean isReleased, Category category, double actualPrice) {
	super(name, year);
	this.isReleased = isReleased;
	this.category = category;
	this.actualPrice = Math.max(MIN_PRICE, actualPrice);
}

//Nested class
public  class MovieStats{
	private double timesWatched;

	public MovieStats(double timesWatched) {
		this.timesWatched = timesWatched;
	}
	
	public double getRevenue() {
		return timesWatched=actualPrice;
	}
	
}
		
		
	}
	
	public static class Product{
		protected String name;
		protected int year;
		protected double actualPrice;
		public Product(String name, int year) {
			this.name = name;
			this.year = year;
		}
		
		
	}
	
	//enum
	public enum Category{
		ADVENTURE,COMEDY,ACTION
	}

}
