package engine;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

import util.UserProfile;


/**
 * Get coefficients and values from
 * 
 * @author Weiwei Chen
 * @since Feb 15, 2014
 * 
 */
public class ProfileConnect {
	public static void main (String[] args){
		//test connection
		ProfileConnect profile = new ProfileConnect();
		String user_id = "123457";
		JSONObject coefficients = profile.connect(urlString, table_coefficients, user_id);
		JSONObject values = profile.connect(urlString, table_profiles, user_id);
		UserProfile user = new UserProfile(values, coefficients);
		System.out.println(user.personality.coefficient);
	}
	
	private final static String urlString = "http://api.ribbonandbow.com/";
	private final static String table_coefficients = "coefficients";
	private final static String table_profiles = "profiles";
	
	public UserProfile getUserProfile(String user_id){
		JSONObject coefficients = connect(urlString, table_coefficients, user_id);
		JSONObject values = connect(urlString, table_profiles, user_id);
		return new UserProfile(values, coefficients);
	}
	
	public JSONObject connect(String urlString, String table, String user_id) {

		try {
			URL u = new URL(urlString + "/" + table + "/" + user_id);
	        HttpURLConnection c = (HttpURLConnection) u.openConnection();
	        c.setRequestMethod("GET");
	        c.setRequestProperty("Content-length", "0");
	        c.setUseCaches(false);
	        c.setAllowUserInteraction(false);
	        c.connect();
	        int status = c.getResponseCode();

	        switch (status) {
	            case 200:
	            case 201:
	                BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
	                StringBuilder sb = new StringBuilder();
	                String line = null;
	                while ((line = br.readLine()) != null) {
	                    sb.append(line+"\n");
	                }
	                br.close();
	                String jsonTxt = sb.toString();
	                JSONArray json = new JSONArray ( jsonTxt );  
	                JSONObject obj = json.getJSONObject(0);
	                return obj;
	        }

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
