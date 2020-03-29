package store;

import java.util.Scanner;

public class Client extends Person{
	private DebitCard debitCard;
	private FidelityCard fidelityCard;
	
	public Client( String name, int age, String method , boolean check) {
		super(name, age);
		this.paymentMethod(method);
		//this.fidelity(check);
		this.debitCard = new DebitCard(0000);
		this.fidelityCard = new FidelityCard(0000);
	}
	
	public void fidelity(boolean check) {
		if(check) {
			Scanner s = new Scanner(System.in);
			System.out.println("Te rog sa introduci pinul cardului de fideltate");
			int pin = s.nextInt();
			//System.out.println(s.next()+ " problema");
			s.close();
			this.fidelityCard = new FidelityCard(pin);
			
			
			
			//return this.getName() + " are card de fidelitate " + fidelityCard.getPin();
		}
		else {
			//return this.getName() + " nu are card de fidelitate ";
		}
	}
		
	public String paymentMethod(String method) {
			if(method == "card") {
				Scanner s = new Scanner(System.in);
				System.out.println("Te rog sa introduci pinul");
				int pin = s.nextInt();
				
				this.debitCard = new DebitCard(pin);
				
				s.close();
				
				return this.getName() + " plateste cu cardul " + debitCard.getPin();
			}
			else {
				return this.getName() + " plateste cu cash";
			}
		}
		
	
	
	@Override
	public String toString() {
		return super.toString() + "\n";
	}
	
	public DebitCard getDebitCard() {
		return this.debitCard;
	}
	
	public FidelityCard getFidelityCard() {
		return this.fidelityCard;
	}
		
}