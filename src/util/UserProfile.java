package util;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A list of items in user profile
 * @author Weiwei Chen
 * @since Feb 15, 2014
 *
 */
public class UserProfile {
	public Item age;
	public Item gender;
	public Item style;
	public Item personality;
	public Item product_interests;
	public Item brand_interests;
	public Item fb_interests;
	public Item fb_likes;
	public UserProfile(JSONObject values, JSONObject coefficients){
		try {
			this.age = new Item(values.get("age"), coefficients.get("age"));
			this.gender = new Item(values.get("gender"), coefficients.get("gender"));
			this.style = new Item(values.get("style"), coefficients.get("style"));
			this.personality = new Item(values.get("personality"), coefficients.get("personality"));
			this.product_interests = new Item(values.get("product_interests"), coefficients.get("product_interests"));
			this.brand_interests = new Item(values.get("brand_interests"), coefficients.get("brand_interests"));
			this.fb_interests = new Item(values.get("fb_interests"), coefficients.get("fb_interests"));
			this.fb_likes = new Item(values.get("fb_likes"), coefficients.get("fb_likes"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
}

