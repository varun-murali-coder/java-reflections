package data;

import java.util.Arrays;

public class GameConfig {
	
	private int releaseYear;
	private String gameName;
	private double price;
	private String [] knownCharacters;
	public int getReleaseYear() {
		return releaseYear;
	}
	public String getGameName() {
		return gameName;
	}
	public double getPrice() {
		return price;
	}
	public String[] getKnownCharacters() {
		return knownCharacters;
	}
	@Override
	public String toString() {
		return "GameConfig [releaseYear=" + releaseYear + ", gameName=" + gameName + ", price=" + price
				+ ", knownCharacters=" + Arrays.toString(knownCharacters) + "]";
	}
	
	
	
	

}
