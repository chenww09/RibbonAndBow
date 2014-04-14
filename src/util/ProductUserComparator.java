package util;

import java.util.Comparator;

public class ProductUserComparator implements Comparator<ProductInfo> {

	UserProfile user = null;
	public ProductUserComparator (UserProfile user){
		this.user = user;
	}
    public int compare(ProductInfo p1, ProductInfo p2) {
    	double score1 = getScore(p1);
    	double score2 = getScore(p2);
    	if(score1 > score2){
    		return -1;
    	}else if(score1 < score2){
    		return 1;
    	}else{
    		return 0;
    	}
    }
    private double getScore(ProductInfo product){
    	
    	double score = 0.0;
    	score += user.product_interests.coefficient * 
    			match(product.productCategory, user.product_interests.name);
    	score += user.brand_interests.coefficient *
    			match(product.productName, user.brand_interests.name);
    	score += user.fb_interests.coefficient *
    			match(product.productTags, user.fb_interests.name);
    	score += user.fb_likes.coefficient * 
    			match(product.productTags, user.fb_likes.name);
    	score += user.gender.coefficient *
    			match(product.gender, user.gender.name);
    	score += user.age.coefficient *
    			match(product.age, user.age.name);
    	score += user.style.coefficient * 
    			match(product.style, user.style.name);
    	score += user.personality.coefficient *
    			match(product.personality, user.personality.name);
    	return score;
    }
    private int match(String str1, String str2){
    	int match = 0;
    	String[] srcs = str1.split(" ");
    	String[] dests = str2.split(" ");
    	//int sum = srcs.length * dests.length;
    	for(String src: srcs){
    		for(String dest: dests ){
    			if(src.length() != 0 && dest.length()!=0){
    				if(src.equalsIgnoreCase(dest)){
    					match ++;
    				}
    			}
    		}
    	}
    	
    	return match;
    }
}
