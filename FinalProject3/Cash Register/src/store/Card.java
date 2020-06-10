package store;

public abstract class Card {
	private int pin;
	
	public Card(int pin) {
		this.pin = pin;
	}
	
	public int getPin() {
		return this.pin;
	}
}
