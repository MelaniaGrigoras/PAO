package store;

public class Electronics extends Product{
	protected int guarantee;
	
	public Electronics(String name, String brand, double pret, int guarantee) {
		super(name, brand, pret);
		this.guarantee = guarantee;
	}
	
	@Override
	public String toString() {
		return super.toString() + " " + this.guarantee;
	}
}
