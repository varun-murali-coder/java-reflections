package codeasgraph;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class Database {

	private  Map<String,List<Float>> GAME_TO_PRICE=new HashMap<String, List<Float>>();
	public  void setGameToPrice() {
		GAME_TO_PRICE.put("Fortine", Arrays.asList(5f,10f));
		GAME_TO_PRICE.put("MineCraft", Arrays.asList(4.3f,100f));
		GAME_TO_PRICE.put("League of Legends", Arrays.asList(4.9f,89f));
		GAME_TO_PRICE.put("Ace Combat", Arrays.asList(4.8f,50f));
		GAME_TO_PRICE.put("StarCraft", Arrays.asList(4.7f,66f));
		GAME_TO_PRICE.put("Burnout", Arrays.asList(4.4f,31f));


	}
	
	public Set<String> readAllGames(){
		return Collections.unmodifiableSet(GAME_TO_PRICE.keySet());
	}
	
	public Map<String,Float> gameToRatings(Set<String>games){
		
		
		return GAME_TO_PRICE.entrySet().stream().filter(entry->games.contains(entry.getKey())).collect(
				Collectors.toMap(Map.Entry::getKey, entry->entry.getValue().get(0))
				);
	}
	
	public Map<String,Float> gameToPrice(Set<String>games){
		
		return GAME_TO_PRICE.entrySet().stream().filter(entry->games.contains(entry.getKey()))
				.collect(Collectors.toMap(Map.Entry::getKey, entry->entry.getValue().get(1)));
	}
	

	
}
