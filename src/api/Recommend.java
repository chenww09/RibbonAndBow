package api;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import engine.MongoDBConnection;
import engine.RecommendationEngine;

import util.Secret;
import util.UserPreference;


/**
 * Servlet implementation class Recommend
 * @author Weiwei Chen
 * @since Feb 15, 2014
 */
@WebServlet("/Recommend")
public class Recommend extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Recommend() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		response.addHeader("Access-Control-Allow-Origin", "*");
		MongoDBConnection connection = new MongoDBConnection();
		JSONObject obj = null;
		if(request.getParameter("username")==null){
			obj = connection.getUsers();
		}else{
			String username = request.getParameter("username");
			String facebookId = request.getParameter("facebookId");
			String linkedInId = request.getParameter("linkedInId");
			String gender = request.getParameter("gender");
			String password = request.getParameter("password");
			obj = connection.addUser(username, facebookId, linkedInId, gender, password);

		}
		

		PrintWriter out = response.getWriter();


		out.print(obj);
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Receiving one request");
		
		
		StringBuffer jb = new StringBuffer();
		  String line = null;
		  try {
		    BufferedReader reader = request.getReader();
		    while ((line = reader.readLine()) != null)
		      jb.append(line);
		  } catch (Exception e) { /*report an error*/ }

		  try {
			JSONObject input = new JSONObject(jb.toString());
			JSONObject obj = null;
			MongoDBConnection connection = new MongoDBConnection();
			RecommendationEngine engine = new RecommendationEngine();
			if(!input.has("secretKey")){
				return;
			}
			if(!input.get("secretKey").equals(Secret.key1)){
				return;
			}
			if(input.has("userId")){
				String userId = (String) input.get("userId");
				//obj = connection.match(userId);
				obj = engine.recommend(userId);
			} else if(input.has("keywords")){
				String keywords = (String) input.get("keywords");
				obj = connection.searchProduct(keywords);
			} else {
				//String business = (String) input.get("business");
				String events = (String) input.get("gifting_event");
				//String pricing = (String) input.get("pricing");
				String personality = (String) input.get("personality");
				String age = (String) input.get("age");
				String gender = (String) input.get("gender");
						
				UserPreference preference = new UserPreference("", events, "", 
						personality, age, gender);
				obj = engine.recommend(preference);
			}
			
			response.setContentType("application/json");
			response.addHeader("Access-Control-Allow-Origin", "*");
			PrintWriter out = response.getWriter();
			out.print(obj);
			out.flush();
			out.close();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
