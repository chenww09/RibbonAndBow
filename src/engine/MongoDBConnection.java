package engine;
/**
 * MongoDBConnection handles the MongoDB Java driver
 * @author chenweiwei
 * @since Dec 19, 2013
 *
 */


import com.mongodb.CommandResult;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;
import com.mongodb.WriteResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import util.ProductUserComparator;
import util.ProductInfo;
import util.ProductUserPreferenceComparator;
import util.Secret;
import util.UserProfile;
import util.UserPreference;

public class MongoDBConnection {

//	final String server_url = "ec2-54-186-106-80.us-west-2.compute.amazonaws.com";
	final String server_url = "ec2-54-213-98-38.us-west-2.compute.amazonaws.com";
	// final String server_url =
	// "ec2-54-201-168-36.us-west-2.compute.amazonaws.com";
	// final String server_url = "ip-172-31-8-63";
	final int server_port = 27017;
	final String db_name = "test";
	final String db_user = "ribbonandbow";
	final String db_pwd = "theperfectgift";

	public MongoDBConnection() {
		// To directly connect to a single MongoDB server (note that this will
		// not auto-discover the primary even

	}

	public void testConnection() {
		MongoClient mongoClient;
		try {
			System.out.println("Connecting to MongoDB Server");
			//mongoClient = new MongoClient(server_url, server_port);
			MongoCredential credential = MongoCredential.createMongoCRCredential(Secret.mongoDBuser, db_name, Secret.mongoDBkey.toCharArray());

			mongoClient = new MongoClient(new ServerAddress(server_url), Arrays.asList(credential));

			DB db = mongoClient.getDB(db_name);

			Set<String> colls = db.getCollectionNames();

			for (String s : colls) {
				System.out.println(s);
			}
			DBCollection collection = db.getCollection("test");
			DBCursor cursor = collection.find();
			try {
				while (cursor.hasNext()) {
					System.out.println(cursor.next());
				}
			} finally {
				cursor.close();
			}
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JSONObject getUsers() {
		MongoClient mongoClient;
		try {
			System.out.println("Connecting to MongoDB Server");
			//mongoClient = new MongoClient(server_url, server_port);
			MongoCredential credential = MongoCredential.createMongoCRCredential(Secret.mongoDBuser, db_name, Secret.mongoDBkey.toCharArray());

			mongoClient = new MongoClient(new ServerAddress(server_url), Arrays.asList(credential));
			DB db = mongoClient.getDB(db_name);
			
			DBCollection collection = db.getCollection("user_personal_info");
			DBCursor cursor = collection.find();
			JSONObject obj = new JSONObject();
			try {
				int index = 0;
				while (cursor.hasNext()) {
					DBObject dbObj = cursor.next();
					obj.append(Integer.toString(index), dbObj);
					index++;
				}
			} finally {
				cursor.close();
			}

			return obj;

		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public JSONObject getProducts() {
		MongoClient mongoClient;
		try {
			System.out.println("Connecting to MongoDB Server");
			//mongoClient = new MongoClient(server_url, server_port);
			
			MongoCredential credential = MongoCredential.createMongoCRCredential(Secret.mongoDBuser, db_name, Secret.mongoDBkey.toCharArray());

			mongoClient = new MongoClient(new ServerAddress(server_url), Arrays.asList(credential));
			
			DB db = mongoClient.getDB(db_name);

			DBCollection collection = db.getCollection("products");
			DBCursor cursor = collection.find();
			JSONObject obj = new JSONObject();
			try {
				int index = 0;
				while (cursor.hasNext()) {
					DBObject dbObj = cursor.next();
					obj.append(Integer.toString(index), dbObj);
					index++;
				}
			} finally {
				cursor.close();
			}

			return obj;

		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void addCollection(Map<String, String> map, String collectionName) {
		MongoClient mongoClient;
		try {
			System.out.println("Connecting to MongoDB Server");
			//mongoClient = new MongoClient(server_url, server_port);
			MongoCredential credential = MongoCredential.createMongoCRCredential(Secret.mongoDBuser, db_name, Secret.mongoDBkey.toCharArray());

			mongoClient = new MongoClient(new ServerAddress(server_url), Arrays.asList(credential));
			DB db = mongoClient.getDB(db_name);

			DBCollection collection = db.getCollection(collectionName);
			BasicDBObject doc = new BasicDBObject();
			for (Entry<String, String> entry : map.entrySet()) {
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();
				doc.append(key, value);
			}

			WriteResult result = collection.insert(doc);
			// result.getLastError().get("ok");
			double ok = (Double) result.getLastError().get("ok");
			JSONObject obj = new JSONObject();
			if (ok == 1.0) {
				System.out.println("Successful");
			} else {
				System.out.println("Fail");
			}

		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public JSONObject match(String id) {
		MongoClient mongoClient;
		try {
			System.out.println("Connecting to MongoDB Server");
			//mongoClient = new MongoClient(server_url, server_port);
			MongoCredential credential = MongoCredential.createMongoCRCredential(Secret.mongoDBuser, db_name, Secret.mongoDBkey.toCharArray());

			mongoClient = new MongoClient(new ServerAddress(server_url), Arrays.asList(credential));
			DB db = mongoClient.getDB(db_name);

			DBCollection collection = db.getCollection("user_personal_info");
			BasicDBObject query = new BasicDBObject("id", id);
			DBCursor cursor = collection.find(query);
			String keywords = "";
			if (cursor.hasNext()) {
				System.out.println("Found One");
				DBObject obj = cursor.next();
				String interests = (String) obj.get("interests");
				String hobby = (String) obj.get("hobby");
				String likes = (String) obj.get("likes");
				keywords = interests + "," + hobby + "," + likes + ",";
				return this.searchProduct(keywords);
			} else {
				System.out.println("Failed to find one person with id " + id);
			}
			// CommandResult result = db.command("");

		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public JSONObject searchProduct(String keywords) {

		MongoClient mongoClient;
		try {
			System.out.println("Connecting to MongoDB Server");
			//mongoClient = new MongoClient(server_url, server_port);
			MongoCredential credential = MongoCredential.createMongoCRCredential(Secret.mongoDBuser, db_name, Secret.mongoDBkey.toCharArray());

			mongoClient = new MongoClient(new ServerAddress(server_url), Arrays.asList(credential));
			DB db = mongoClient.getDB(db_name);

			DBCollection collection = db.getCollection("products");

			Map<String, Integer> map = new HashMap<String, Integer>();
			Map<String, ProductInfo> mapInfo = new HashMap<String, ProductInfo>();

			for (String keyword : keywords.split(",")) {
				if (!keyword.equals("")) {
					Pattern pattern = Pattern.compile(keyword,
							Pattern.CASE_INSENSITIVE);
					BasicDBObject query = new BasicDBObject("Product Tags",
							pattern);
					DBCursor cursor = collection.find(query);
					while (cursor.hasNext()) {
						DBObject obj = cursor.next();
						String productId = (String) obj.get("Product ID");
						String link = (String) obj.get("Link");
						String productName = (String) obj.get("Product Name");
						ProductInfo info = new ProductInfo(productId, link,
								productName);
						mapInfo.put(productId, info);
						if (!map.containsKey(productId)) {
							map.put(productId, 1);
						} else {
							int num = map.get(productId);
							num++;
							map.put(productId, num);

						}
					}
				}
			}
			ValueComparator bvc = new ValueComparator(map);
			TreeMap<String, Integer> sorted_map = new TreeMap<String, Integer>(
					bvc);
			sorted_map.putAll(map);
			System.out.println(sorted_map);
			JSONObject obj = new JSONObject();
			JSONArray array = new JSONArray();
			for (Map.Entry<String, Integer> entry : sorted_map.entrySet()) {
				String productId = entry.getKey();
				ProductInfo info = mapInfo.get(productId);
				JSONObject product = new JSONObject();
				product.put("product_id", productId);
				product.put("link", info.link);
				product.put("productName", info.productName);
				array.put(product);
			}
			obj.put("products", array);

			return obj;

		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public JSONObject filterProduct(UserPreference preference){
		MongoClient mongoClient;
		try {
			System.out.println("Connecting to MongoDB Server");
			//mongoClient =new MongoClient(server_url, server_port);
			MongoCredential credential = MongoCredential.createMongoCRCredential(Secret.mongoDBuser, db_name, Secret.mongoDBkey.toCharArray());

			mongoClient = new MongoClient(new ServerAddress(server_url), Arrays.asList(credential));
			DB db = mongoClient.getDB(db_name);

			
			DBCollection collection = db.getCollection("products");

			//do filtering based on age and gender
			
			
			BasicDBObject query = new BasicDBObject();
			if(!preference.getAge().equals("")){
				query.append("age", Pattern.compile(preference.getAge(), Pattern.CASE_INSENSITIVE));
			}
			if(!preference.getGender().equals("")){
				query.append("gender", Pattern.compile(preference.getGender(), Pattern.CASE_INSENSITIVE));
			}
			DBCursor cursor = collection.find(query);
			ArrayList<ProductInfo> productList = new ArrayList<ProductInfo>();
			while (cursor.hasNext()) {
				DBObject obj = cursor.next();
				ProductInfo info = new ProductInfo(obj);
				productList.add(info);
			}
			
			
			ProductUserPreferenceComparator comparator = new ProductUserPreferenceComparator(preference);
			Collections.sort(productList, comparator);
			//for(ProductInfo info:productList){
				//System.out.println(info.getScore(user));
			//}

			JSONObject obj = new JSONObject();
			JSONArray array = new JSONArray();
			for (ProductInfo info: productList) {
				String productId = info.id;
				JSONObject product = new JSONObject();
				product.put("product_id", productId);
				product.put("link", info.link);
				product.put("productName", info.productName);
				array.put(product);
			}
			obj.put("products", array);

			return obj;

		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public JSONObject filterProduct(UserProfile user) {

		MongoClient mongoClient;
		try {
			System.out.println("Connecting to MongoDB Server");
			//mongoClient = new MongoClient(server_url, server_port);
			MongoCredential credential = MongoCredential.createMongoCRCredential(Secret.mongoDBuser, db_name, Secret.mongoDBkey.toCharArray());

			mongoClient = new MongoClient(new ServerAddress(server_url), Arrays.asList(credential));
			DB db = mongoClient.getDB(db_name);

			DBCollection collection = db.getCollection("products");

			BasicDBObject query = new BasicDBObject();
			if(!user.age.name.equals("")){
				query.append("age", Pattern.compile(user.age.name, Pattern.CASE_INSENSITIVE));
			}
			if(!user.gender.name.equals("")){
				query.append("gender", Pattern.compile(user.gender.name, Pattern.CASE_INSENSITIVE));
			}
			DBCursor cursor = collection.find(query);
			ArrayList<ProductInfo> productList = new ArrayList<ProductInfo>();
			while (cursor.hasNext()) {
				DBObject obj = cursor.next();
				ProductInfo info = new ProductInfo(obj);
				productList.add(info);
			}
			
			
			ProductUserComparator comparator = new ProductUserComparator(user);
			Collections.sort(productList, comparator);
			for(ProductInfo info:productList){
				System.out.println(info.getScore(user));
			}

			JSONObject obj = new JSONObject();
			JSONArray array = new JSONArray();
			for (ProductInfo info: productList) {
				String productId = info.id;
				JSONObject product = new JSONObject();
				product.put("product_id", productId);
				product.put("link", info.link);
				product.put("productName", info.productName);
				array.put(product);
			}
			obj.put("products", array);

			return obj;

		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public JSONObject addUser(String username, String facebookId,
			String linkedInId, String gender, String password) {
		MongoClient mongoClient;
		try {
			System.out.println("Connecting to MongoDB Server");
			//mongoClient = new MongoClient(server_url, server_port);
			MongoCredential credential = MongoCredential.createMongoCRCredential(Secret.mongoDBuser, db_name, Secret.mongoDBkey.toCharArray());

			mongoClient = new MongoClient(new ServerAddress(server_url), Arrays.asList(credential));
			DB db = mongoClient.getDB(db_name);

			DBCollection collection = db.getCollection("test");
			BasicDBObject doc = new BasicDBObject("facebook", facebookId)
					.append("linkedin", linkedInId).append("gender", gender)
					.append("password", password);
			WriteResult result = collection.insert(doc);
			// result.getLastError().get("ok");
			double ok = (Double) result.getLastError().get("ok");
			JSONObject obj = new JSONObject();

			try {
				obj.put("ok", ok);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return obj;

		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * For debug purpose
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MongoDBConnection connect = new MongoDBConnection();
		connect.testConnection();

	}
}

class ValueComparator implements Comparator<String> {

	Map<String, Integer> base;

	public ValueComparator(Map<String, Integer> base) {
		this.base = base;
	}

	// Note: this comparator imposes orderings that are inconsistent with
	// equals.
	public int compare(String a, String b) {
		if (base.get(a) >= base.get(b)) {
			return -1;
		} else {
			return 1;
		} // returning 0 would merge keys
	}
}
