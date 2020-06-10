package store;

public class Books extends Product{
	protected String category;
	protected int pages;
	
	public Books(String name, String brand, double pret, String category, int pages) {
		super(name, brand, pret);
		this.category = category;
		this.pages = pages;
	}
	
	@Override
	public String toString() {
		return super.toString() + " " + this.category + " " + this.pages;
	}
}
