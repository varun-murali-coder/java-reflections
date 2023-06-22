package data;

public class Movie {
	
	private final String name;
	private final float rating;
	private final String[] categories;
	private final Actor[] actors;
	private  static final int DEFAULT_RATING=5;
	public Movie(String name, float rating, String[] categories,Actor[] actors) {
		this.actors = actors;
		this.name = name;
		this.rating = rating;
		this.categories = categories;
	}
	
	
	

}
