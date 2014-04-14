package api;

import org.json.JSONObject;

import engine.MongoDBConnection;
import engine.RecommendationEngine;

import util.UserPreference;

public class RecommendationTest {
	public static void main(String[] args){

		testRecommendUserPreference();
	}
	
	public static void testSearchProducts(){
		MongoDBConnection connection = new MongoDBConnection();
		connection.match("11311021");
		connection.searchProduct("Book,Fiction,");
	}
	
	public static void testRecommendUserPreference(){
		MongoDBConnection connection = new MongoDBConnection();
		UserPreference preference = new UserPreference("", "", "", 
				"", "", "female");
		RecommendationEngine engine = new RecommendationEngine();
		JSONObject obj = engine.recommend(preference);
		System.out.println(obj);
	}
}
