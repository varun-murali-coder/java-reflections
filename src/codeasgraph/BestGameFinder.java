package codeasgraph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import codeasgraph.Annotations.DependsOn;
import codeasgraph.Annotations.FinalResult;
import codeasgraph.Annotations.Operation;

public class BestGameFinder {
	private Database db=new Database();
	@Operation("allGames")
	public Set<String> readAllGames(){
		db.setGameToPrice();
		return db.readAllGames();
	}
	@Operation("Game-To-Price")
	public Map<String,Float> getGameToPrice(@DependsOn("allGames")Set<String>games){
		return db.gameToPrice(games);
	}
	@Operation("Game-To-Rating")
	public Map<String,Float> getGameToRating(@DependsOn("allGames")Set<String>games){
		return db.gameToRatings(games);
	}
	@Operation("scoreGame")
	public SortedMap<Double,String> scoreGame(@DependsOn("Game-To-Price")Map<String,Float> getGameToPrice,@DependsOn("Game-To-Rating")Map<String,Float> getGameToRating)
	{
		 SortedMap<Double,String> scoreGame=new TreeMap<>(Collections.reverseOrder());
		 for(String gameName:getGameToPrice.keySet()) {
			 
			 double score=(double)getGameToRating.get(gameName)/getGameToPrice.get(gameName);
			 scoreGame.put(score, gameName);
		 }
		 return scoreGame;
		
	}
	@FinalResult
	public List<String> topGamesInDescOrder(@DependsOn("scoreGame")SortedMap<Double,String> scoreGame){
		return new ArrayList<>(scoreGame.values());
	}

}
