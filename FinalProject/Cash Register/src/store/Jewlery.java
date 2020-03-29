package store;

public class Jewlery extends Product{
	protected String material;
	
	public Jewlery(String name, String brand, double pret, String material) {
		super(name, brand, pret);
		this.material = material;
	}
	
	@Override
	public String toString() {
		return super.toString() + " " + this.material;
	}
}
