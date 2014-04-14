package engine;
import org.json.JSONObject;

import util.UserPreference;
import util.UserProfile;


/**
 * Recommendation Engine
 * @author Weiwei Chen
 * @since Feb 14, 2014
 *
 */
public class RecommendationEngine {
	public static void main(String[] args){
		new RecommendationEngine().recommend("123457");
	}
	public JSONObject recommend(String userId){
		MongoDBConnection connection = new MongoDBConnection();
		
		ProfileConnect connect = new ProfileConnect();
		UserProfile user = connect.getUserProfile(userId);
		return connection.filterProduct(user);
		
	}
	
	public JSONObject recommend(UserPreference preference){
		MongoDBConnection connection = new MongoDBConnection();
		return connection.filterProduct(preference);
		
	}
}
