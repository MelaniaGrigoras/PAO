package store;

public class AnimalFood extends Product{
	protected String animal;
	protected double quantity;
	
	public AnimalFood(String name, String brand, double pret, String animal, double quantity) {
		super(name, brand, pret);
		this.animal = animal;
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		return super.toString() + " " + this.animal + " " + this.quantity;
	}
}
