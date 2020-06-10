package store;

import java.util.List;

public abstract class Product {
	protected String name;
	protected String brand;
	private double pret;
	
	public Product(String name, String brand, double pret) {
		this.name = name;
		this.brand = brand;
		this.pret =  pret;
	}
	
	public String toString() {
		return this.name + " " + this.brand + " " + this.pret + " " ; //
	}
	
}
