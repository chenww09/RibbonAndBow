package util;

public class UserPreference {
	private String business;
	private String gifting_events;
	private String pricing;
	private String personality;
	private String age;
	private String gender;
	public UserPreference (String business, String gifting_events, String pricing,
			String personality, String age, String gender){
		this.business = business;
		this.gifting_events = gifting_events;
		this.pricing = pricing;
		this.personality = personality;
		this.age = age;
		this.gender = gender;
	}
	
	public String getBusiness(){
		return this.business;
	}
	
	public String getGiftingEvents(){
		return this.gifting_events;
	}
	
	public String getPricing(){
		return this.pricing;
	}
	
	public String getPersonality(){
		return this.personality;
	}
	
	public String getAge(){
		return this.age;
	}
	
	public String getGender(){
		return this.gender;
	}
}
