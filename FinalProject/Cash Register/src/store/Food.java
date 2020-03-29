package store;

public class Food extends Product{
	protected double quantity;
	protected boolean packaging;
	
	public Food(String name, String brand, double pret, double quantity, boolean packaging) {
		super(name, brand, pret);
		this.quantity = quantity;
		this.packaging = packaging;
	}
	
	@Override
	public String toString() {
		return super.toString() + " " + this.quantity + " " + this.packaging;
	}
}
