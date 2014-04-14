package api;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import engine.MongoDBConnection;

/**
 * Servlet implementation class User
 */
@WebServlet("/User")
public class User extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public User() {
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
		// TODO Auto-generated method stub
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
				String facebookId = (String) input.get("facebook");
				String linkedInId = (String) input.get("linkedIn");
				String gender = (String) input.get("gender");
				String password = (String) input.get("password");
				String username = (String) input.get("username");
				MongoDBConnection connection = new MongoDBConnection();
				JSONObject obj = connection.addUser(username, facebookId, linkedInId, gender, password);;
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
