package store;

public class Cloth extends Product{
	protected char gender;
	protected char size;
	
	public Cloth(String name, String brand, double pret, char gender, char size) {
		super(name, brand, pret);
		this.gender = gender;
		this.size = size;
	}
	
	@Override
	public String toString() {
		return super.toString() + " " + this.gender + " " + " marimea " + this.size;
	}
}
