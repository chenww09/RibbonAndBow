package util;

public class Item{
	public String name ; 
	public double coefficient;
	public Item(String name, double coefficient){
		this.name = name;
		this.coefficient = coefficient;
	}
	public Item(Object name, Object coefficient){
		this((String)name, Double.parseDouble((String)coefficient));
	}
	
}
