package util;

import com.mongodb.DBObject;

public class ProductInfo {
	public String id;
	public String link;
	public String productName;
	public String productCategory;
	public String productTags;
	public String price;
	public String gender;
	public String style;
	public String age;
	public String personality;
	public String giftingEvent;
	public String business;

	public ProductInfo(String id) {
		this.id = id;
	}

	public ProductInfo(String id, String link) {
		this(id);
		this.link = link;
	}

	public ProductInfo(String id, String link, String productName) {
		this(id, link);
		this.productName = productName;
	}

	public ProductInfo(String id, String link, String productName,
			String productCategory, String productTags, String price) {
		this(id, link, productName);
		this.productCategory = productCategory;
		this.productTags = productTags;
		this.price = price;
	}

	public ProductInfo(DBObject obj) {
		this.id = (String) obj.get("product_id");
		this.link = (String) obj.get("link");
		this.productName = (String) obj.get("name");
		this.age = (String) obj.get("age");
		this.gender = (String) obj.get("gender");
		this.personality = (String) obj.get("personality");
		this.price = (String) obj.get("price_category");
		this.productCategory = (String) obj.get("category");
		this.productTags = (String) obj.get("tags");
		this.style = (String) obj.get("style");
		this.giftingEvent = (String) obj.get("gifting_event");
		//Business is not used in the new 
		this.business = (String) obj.get("business");

	}

	public double getScore(UserProfile user) {

		double score = 0.0;
		score += user.product_interests.coefficient
				* match(productCategory, user.product_interests.name);
		score += user.brand_interests.coefficient
				* match(productName, user.brand_interests.name);
		score += user.fb_interests.coefficient
				* match(productTags, user.fb_interests.name);
		score += user.fb_likes.coefficient
				* match(productTags, user.fb_likes.name);
		score += user.gender.coefficient * match(gender, user.gender.name);
		score += user.age.coefficient * match(age, user.age.name);
		score += user.style.coefficient * match(style, user.style.name);
		score += user.personality.coefficient
				* match(personality, user.personality.name);
		//should add user's brand 
		return score;
	}

	private int match(String str1, String str2) {
		int match = 0;
		String[] srcs = str1.split(" ");
		String[] dests = str2.split(" ");
		// int sum = srcs.length * dests.length;
		for (String src : srcs) {
			for (String dest : dests) {
				if (src.length() != 0 && dest.length() != 0) {
					if (src.equalsIgnoreCase(dest)) {
						match++;
					}
				}
			}
		}

		return match;
	}

}
