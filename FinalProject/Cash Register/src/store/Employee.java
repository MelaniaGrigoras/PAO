package store;

public class Employee extends Person{
	private double salary;
	
	public Employee(String name, int age, double salary) {
		super(name, age);
		this.salary = salary;
	}
	
	public double getSalary() {
		return this.salary;
	}
	
	public String toString() {
		return super.toString() + " are salariul de " + this.salary;
	}
}
