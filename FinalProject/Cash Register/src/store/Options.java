package store;

//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//import java.util.Map;
//import java.util.HashMap;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.*; 
import java.lang.*; 
import java.io.*; 

public class Options {
	protected List<Client> clients;
	protected List<Employee> employees;
	protected Map<Integer, Product> basket;
	
	public Options() throws FileNotFoundException{
		
		//Lista clienti
		File file = new File("C:\\Users\\domo\\Desktop\\ClientsList.txt"); 
		Scanner scanner = new Scanner(file); 
					  
		this.clients = new ArrayList<>();
		while (scanner.hasNextLine()) {
			String []temp = scanner.nextLine().split(" ");
			this.clients.add(new Client(temp[0], Integer.parseInt(temp[1]), temp[2], Boolean.parseBoolean(temp[3])));
			}
		scanner.close();
		
		//Lista angajati
				file = new File("C:\\Users\\domo\\Desktop\\EmployeesList.txt"); 
				scanner = new Scanner(file);
				this.employees = new ArrayList<>();
				int nr = 10;
				while (nr>0) {
					String []temp = scanner.nextLine().split(" ");
					this.employees.add(new Employee(temp[0], Integer.parseInt(temp[1]), Double.parseDouble(temp[2])));
					nr--;
					}
		
				
		//cos de cumparaturi
		file = new File("C:\\Users\\domo\\Desktop\\Basket.txt"); 
		scanner = new Scanner(file); 
		this.basket = new HashMap();
		basket.put(3, new Cloth("Tricou", "Polo", 100, 'M', 'M'));
		basket.put(6, new Food("Ardei", "Capia", 1.5, 3, false));
		basket.put(8, new Toys("Superhero", "Noriel", 55.99, 'M', 5));
		basket.put(5, new Electronics("S10", "Samsung", 4500.98, 1));
		basket.put(7, new Jewlery("Inel", "Taylor", 250, "Argint"));
		basket.put(1,new AnimalFood("HranaPisici", "Whisky", 9.99, "Pisica", 500));
		basket.put(4,new Cosmetics("Parfum", "Starlet", 276));
		basket.put(2,new Books("Comoara", "Humanitas", 23.97, "Mister", 555));

		scanner.close();
	}
	
	public void show() throws FileNotFoundException{
		Scanner scanner = new Scanner(System.in);

		do {

			System.out.print(this);
			
			int n = scanner.nextInt();
			switch(n) {
			    case 0:
			    	System.out.print(this);
			    	break;
				
			    case 1:
					showClientsList();
					break;
				
				case 2:
					showEmployeeList();
					break;
				
				case 3:
					System.out.println("Please write the name, age, payment method and the existence of a fideliy card of the client!");
					Scanner scanner2 = new Scanner(System.in);
					String []temp2 = scanner2.nextLine().split(" ");
					
					addClient(new Client(temp2[0], Integer.parseInt(temp2[1]), temp2[2], Boolean.parseBoolean(temp2[3])));
					//scanner2.close();
					break;
				
				case 4:
					System.out.println("Please write the name, age, salary of the client!");
					Scanner scanner3 = new Scanner(System.in);
					String []temp3 = scanner3.nextLine().split(" ");
					addEmployee(new Employee(temp3[0], Integer.parseInt(temp3[1]), Double.parseDouble(temp3[2])));
					//scanner3.close();
					break;
				
				case 5:
					System.out.println(basket + "\n");
					break;
				
				case 6:
					sortEmployeesList();
					break;
					
				case 7:
					System.out.println(basket);
					for(Integer index: basket.keySet())
						   System.out.println(index + ". " + basket.get(index));
					System.out.println();
					break;
					
				case 8:
					
					System.out.println("Please write the name, brand, price, gender and minimum age for the toy!");
					Scanner scanner4 = new Scanner(System.in);
					String []temp4 = scanner4.nextLine().split(" ");
					addProduct(new Toys(temp4[0], temp4[1], Double.parseDouble(temp4[2]), temp4[3].charAt(0), Integer.parseInt(temp4[4])));
					break;
				
				case 9:
					System.out.println("Please write the name, brand, price, gender and size for your piece of clothing!");
					Scanner scanner5 = new Scanner(System.in);
					String []temp5 = scanner5.nextLine().split(" ");
					addProduct(new Cloth(temp5[0], temp5[1], Double.parseDouble(temp5[2]), temp5[3].charAt(0), temp5[4].charAt(0)));
					break;
					
				case 10:
					System.out.println("Please write the name, brand, price, quantity and if it is packed for your food!");
					Scanner scanner6 = new Scanner(System.in);
					String []temp6 = scanner6.nextLine().split(" ");
					addProduct(new Food(temp6[0], temp6[1], Double.parseDouble(temp6[2]), Double.parseDouble(temp6[3]), Boolean.parseBoolean(temp6[4])));
					break;
					
				case 11:
					System.out.println("Please write the name, brand, price, quantity and material for your jewlery!");
					Scanner scanner7 = new Scanner(System.in);
					String []temp7 = scanner7.nextLine().split(" ");
					addProduct(new Jewlery(temp7[0], temp7[1], Double.parseDouble(temp7[2]), temp7[3]));
					break;
					
				case 12:
					System.out.println("Please write the name, brand, price, duration of yout guarantee (in years) for your electronic!");
					Scanner scanner8 = new Scanner(System.in);
					String []temp8 = scanner8.nextLine().split(" ");
					addProduct(new Electronics(temp8[0], temp8[1], Double.parseDouble(temp8[2]), Integer.parseInt(temp8[3])));
					break;
					
				case 13:
					System.out.println("Please write the name, brand, price, type and quantity for your animal!");
					Scanner scanner9 = new Scanner(System.in);
					String []temp9 = scanner9.nextLine().split(" ");
					addProduct(new AnimalFood(temp9[0], temp9[1], Double.parseDouble(temp9[2]), temp9[3], Double.parseDouble(temp9[4])));
					break;
				
				case 14:
					System.out.println("Please write the name, brand, and price for your cosmetics!");
					Scanner scanner10 = new Scanner(System.in);
					String []temp10 = scanner10.nextLine().split(" ");
					addProduct(new Cosmetics(temp10[0], temp10[1], Double.parseDouble(temp10[2])));
					break;
					
				default:
					System.out.println(" Thank you!");
			}
		}while(true);
		
		//scanner.close();
	}

	void addProduct(Product product) {
		basket.put(basket.size(), product);
	}
	void sortEmployeesList(){
		Collections.sort(employees, new Comparator <Employee> () {
			public int compare(Employee a, Employee b) {
				return Double.compare(a.getSalary(), b.getSalary());
			}
		});
	}
	
	void showClientsList() {
		for(Client client : clients) {
			System.out.println(client);
		}
	}
	
	public void showEmployeeList() {
		for(Employee employee : employees) {
			System.out.println(employee);
		}
	}
	
	public void addClient(Client client) {
		this.clients.add(client);
	}
	
	public void addEmployee(Employee employee) {
		this.employees.add(employee);
	}
	
	public String toString() {
		return 
				"Option 0 : Show options\n" +
				"Option 1 : Clients list\n" +
				"Option 2 : Employee list\n" +
				"Option 3: Add client\n" +
				"Option 4: Add employee\n" + 
				"Option 5: Show the sold product of today\n" + 
				"Option 6: Sort employees by salary\n" + 
				"Option 7: Show the basket\n" + 
				"Option 8: Buy another toy\n" + 
				"Option 9: Buy another piece of clothing\n" + 
				"Option 10: Buy food\n" + 
				"Option 11: Buy another jewlery\n" + 
				"Option 12: Buy another electronic\n" + 
				"Option 13: Buy food for your other animals\n" + 
				"Option 14: Buy another type of cosmetic product\n" + 
				"Option 15: Exit\n";
	}
}
