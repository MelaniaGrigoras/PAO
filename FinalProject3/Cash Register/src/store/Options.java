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
import java.sql.Timestamp;
import java.io.*; 


public class Options {
	
	ReadWriteFile fin = ReadWriteFile.getInstance();
	
	protected List<Client> clients;
	protected List<Employee> employees;
	protected Map<Integer, Product> basket;
	
	public Options() throws IOException{
		Scanner scanner = new Scanner(System.in); 
		
		//Lista clienti
		File file = new File("C:\\Users\\domo\\Desktop\\ClientsList.csv"); 
		
		
		List<String[]>s = fin.read(file);
		this.clients = new ArrayList<>();
		
		for(String[] temp : s) {
			if(temp.length == 4) {
				clients.add(new Client(temp[0], Integer.parseInt(temp[1]), temp[2], Boolean.parseBoolean(temp[3])));
			}
		}
		
		//Lista angajati
		file = new File("C:\\Users\\domo\\Desktop\\EmployeesList.csv"); 
		s = fin.read(file);
		this.employees = new ArrayList<>();
		
		for(String[] temp : s) {
			if(temp.length == 3) {
				employees.add(new Employee(temp[0], Integer.parseInt(temp[1]), Double.parseDouble(temp[2])));
			}
		}
				
		//cos de cumparaturi
		file = new File("C:\\Users\\domo\\Desktop\\Basket.csv"); 
		s = fin.read(file);
		
		this.basket = new HashMap();

		for(String[] temp : s) {
			switch(temp[0]) {
				case "1":
					basket.put(basket.size() + 1, new AnimalFood(temp[1], temp[2], Double.parseDouble(temp[3]), temp[4], Double.parseDouble(temp[5])));
					break;
					
				case "2":
					basket.put(basket.size() + 1, new Books(temp[1], temp[2], Double.parseDouble(temp[3]), temp[4], Integer.parseInt(temp[5])));
					break;
					
				case "3":
					basket.put(basket.size() + 1, new Cloth(temp[1], temp[2], Double.parseDouble(temp[3]), temp[4].charAt(0), temp[5].charAt(0)));
					break;
					
				case "4":
					basket.put(basket.size() + 1, new Cosmetics(temp[1], temp[2], Double.parseDouble(temp[3])));
					break;
					
				case "5":
					basket.put(basket.size() + 1, new Electronics(temp[1], temp[2], Double.parseDouble(temp[3]), Integer.parseInt(temp[4])));
					break;
					
				case "6":
					basket.put(basket.size() + 1, new Food(temp[1], temp[2], Double.parseDouble(temp[3]), Double.parseDouble(temp[4]), Boolean.parseBoolean(temp[5])));
					break;
					
				case "7":
					basket.put(basket.size() + 1, new Jewlery(temp[1], temp[2], Double.parseDouble(temp[3]), temp[4]));
					break;
					
				case "8":
					basket.put(basket.size() + 1, new Toys(temp[1], temp[2], Double.parseDouble(temp[3]), temp[4].charAt(0), Integer.parseInt(temp[5])));
					break;
					
				default:
					System.out.println("The product doesn't belong to any category!");
			}
		}

//		scanner.close();
		
		
		
	}
	
	public void show() throws IOException{
		Scanner scanner = new Scanner(System.in);

		do {

			System.out.print("\n" + this);
			
			int n = scanner.nextInt();
			String temp;
			switch(n) {
			    case 0:
			    	System.out.print(this);
			    	break;
				
			    case 1:
			    	AuditCenter.audit("Display clients list");
					showClientsList();
					break;
				
				case 2:
			    	AuditCenter.audit("Display employees list");
					showEmployeeList();
					break;
				
				case 3:
			    	AuditCenter.audit("Add a client");
					System.out.println("Please write the name, age, payment method and the existence of a fideliy card of the client!");
					Scanner scanner2 = new Scanner(System.in);
					temp = scanner2.nextLine();
					
					fin.write(new File("C:\\Users\\domo\\Desktop\\ClientsList.csv"), temp);
					String[] temp2 = temp.split(",");
					
					addClient(new Client(temp2[0], Integer.parseInt(temp2[1]), temp2[2], Boolean.parseBoolean(temp2[3])));
					//scanner2.close();
					break;
				
				case 4:
			    	AuditCenter.audit("Add an employee");
					System.out.println("Please write the name, age, salary of the client!");
					Scanner scanner3 = new Scanner(System.in);
					temp = scanner3.nextLine();
					
					fin.write(new File("C:\\Users\\domo\\Desktop\\EmployeesList.csv"), temp);
					String[] temp3 = temp.split(",");
					
					addEmployee(new Employee(temp3[0], Integer.parseInt(temp3[1]), Double.parseDouble(temp3[2])));
					//scanner3.close();
					break;
				
				case 5:
			    	AuditCenter.audit("Display the products sold today");
					System.out.println(basket + "\n");
					break;
				
				case 6:
			    	AuditCenter.audit("Sort employee list");
					sortEmployeesList();
					break;
					
				case 7:
			    	AuditCenter.audit("Display the contents of the client's basket");
					//System.out.println(basket);
					for(Integer index: basket.keySet())
						   System.out.println(index + ". " + basket.get(index));
					System.out.println();
					break;
					
				case 8:
			    	AuditCenter.audit("Bought a toy");
					System.out.println("Please write the name, brand, price, gender(F/M) and minimum age for the toy!");
					Scanner scanner4 = new Scanner(System.in);
					
					temp = scanner4.nextLine();
					
					fin.write(new File("C:\\Users\\domo\\Desktop\\Basket.csv"), "8," + temp);
					String[] temp4 = temp.split(",");
					
					addProduct(new Toys(temp4[0], temp4[1], Double.parseDouble(temp4[2]), temp4[3].charAt(0), Integer.parseInt(temp4[4])));
					break;
				
				case 9:
			    	AuditCenter.audit("Bought a piece of clothing");
					System.out.println("Please write the name, brand, price, gender and size(S-XXL) for your piece of clothing!");
					Scanner scanner5 = new Scanner(System.in);
					
					temp = scanner5.nextLine();
					
					fin.write(new File("C:\\Users\\domo\\Desktop\\Basket.csv"), "3," + temp);
					String[] temp5 = temp.split(",");
					
					addProduct(new Cloth(temp5[0], temp5[1], Double.parseDouble(temp5[2]), temp5[3].charAt(0), temp5[4].charAt(0)));
					break;
					
				case 10:
			    	AuditCenter.audit("Bought food");
					System.out.println("Please write the name, brand, price, quantity and if it is packed for your food!");
					Scanner scanner6 = new Scanner(System.in);
					
					temp = scanner6.nextLine();
					
					fin.write(new File("C:\\Users\\domo\\Desktop\\Basket.csv"), "6," + temp);
					String[] temp6 = temp.split(",");
					
					addProduct(new Food(temp6[0], temp6[1], Double.parseDouble(temp6[2]), Double.parseDouble(temp6[3]), Boolean.parseBoolean(temp6[4])));
					break;
					
				case 11:
			    	AuditCenter.audit("Bought jewlery");
					System.out.println("Please write the name, brand, price and material for your jewlery!");
					Scanner scanner7 = new Scanner(System.in);
					
					temp = scanner7.nextLine();
					
					fin.write(new File("C:\\Users\\domo\\Desktop\\Basket.csv"), "7," + temp);
					String[] temp7 = temp.split(",");
					
					addProduct(new Jewlery(temp7[0], temp7[1], Double.parseDouble(temp7[2]), temp7[3]));
					break;
					
				case 12:
			    	AuditCenter.audit("Bought an electronic");
					System.out.println("Please write the name, brand, price, duration of your guarantee (in years) for your electronic!");
					Scanner scanner8 = new Scanner(System.in);
					
					temp = scanner8.nextLine();
					
					fin.write(new File("C:\\Users\\domo\\Desktop\\Basket.csv"), "5," + temp);
					String[] temp8 = temp.split(",");
					
					addProduct(new Electronics(temp8[0], temp8[1], Double.parseDouble(temp8[2]), Integer.parseInt(temp8[3])));
					break;
					
				case 13:
			    	AuditCenter.audit("Bought an animal");
					System.out.println("Please write the name, brand, price, type and quantity for your animal!");
					Scanner scanner9 = new Scanner(System.in);
					
					temp = scanner9.nextLine();
					
					fin.write(new File("C:\\Users\\domo\\Desktop\\Basket.csv"), "1," + temp);
					String[] temp9 = temp.split(",");
					
					addProduct(new AnimalFood(temp9[0], temp9[1], Double.parseDouble(temp9[2]), temp9[3], Double.parseDouble(temp9[4])));
					break;
				
				case 14:
			    	AuditCenter.audit("Bought cosmetics");
					System.out.println("Please write the name, brand and price for your cosmetics!");
					Scanner scanner10 = new Scanner(System.in);
					
					temp = scanner10.nextLine();
					
					fin.write(new File("C:\\Users\\domo\\Desktop\\Basket.csv"), "4," + temp);
					String[] temp10 = temp.split(",");
					
					addProduct(new Cosmetics(temp10[0], temp10[1], Double.parseDouble(temp10[2])));
					break;
					
				case 15:
			    	AuditCenter.audit("Exited the program succesfully");
					System.out.println("Thank you!");
					System.exit(0);
					break;
					
				default:
			    	AuditCenter.audit("Wrong number");
					System.out.println("Wrong number!");
			}
		}while(true);
		
		//scanner.close();
	}

	void addProduct(Product product) {
		basket.put(basket.size() + 1, product);
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
				"Option 15: Exit!\n";
	}
}
