package store;

public class Toys extends Product{
	protected char gender;
	protected int minAge;
	
	public Toys(String name, String brand, double pret, char gender, int minAge) {
		super(name, brand, pret);
		this.gender = gender;
		this.minAge = minAge;
	}
	
	@Override
	public String toString() {
		return super.toString() + " " + this.gender + " " + this.minAge;
	}
}
