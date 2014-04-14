package util;

import java.util.Comparator;

public class ProductUserPreferenceComparator implements Comparator<ProductInfo> {

	UserPreference preference = null;
	public ProductUserPreferenceComparator (UserPreference preference){
		this.preference = preference;
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

    	score += match(product.business, preference.getBusiness());
    	//this is in filtering
    	//score += match(product.gender, preference.getGender());
    	//score += match(product.age, user.age.name);
    	score += match(product.personality, preference.getPersonality());
    	score += match(product.giftingEvent, preference.getGiftingEvents());
    	score += match(product.price, preference.getPricing());
    
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
