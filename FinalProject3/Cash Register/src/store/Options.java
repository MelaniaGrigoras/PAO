package store;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.sql.*;

public class Options {

    static String url = "jdbc:mysql://localhost:3306/new_schema";
    static String username = "root";
    static String password = "";
    static String schema = "new_schema";

    ReadWriteFile fin = ReadWriteFile.getInstance();
    protected static List<Client> clients;
    protected static List<Employee> employees;
    protected static Map<Integer, Product> basket;

    private Thread t;
    private String threadName;

    public Options() throws IOException {

        this.employees = new ArrayList();
        this.clients = new ArrayList();

        this.basket = new HashMap();

        ReadEmployees();
        ReadClients();


        try (Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement()){

            ResultSet resultSet = null;

            resultSet = getAllProducts(statement, "new_schema.basket");

            while(resultSet.next()){
                int type = resultSet.getInt("id");
                switch(type) {
                    case 1:
                        AnimalFood animalFood = new AnimalFood();
                        animalFood.setName(resultSet.getString("name"));
                        animalFood.setPret(resultSet.getDouble("price"));
                        animalFood.setBrand(resultSet.getString("brand"));
                        animalFood.setAnimal(resultSet.getString("attribute1"));
                        animalFood.setQuantity(resultSet.getInt("attribute2"));
                        this.basket.put(this.basket.size() + 1, animalFood);
                        break;

                    case 2:
                        Book book = new Book();
                        book.setName(resultSet.getString("name"));
                        book.setPret(resultSet.getDouble("price"));
                        book.setBrand(resultSet.getString("brand"));
                        book.setCategory(resultSet.getString("attribute1"));
                        book.setPages(resultSet.getInt("attribute2"));
                        this.basket.put(this.basket.size() + 1, book);
                        break;

                    case 3:
                        Cloth cloth = new Cloth();
                        cloth.setName(resultSet.getString("name"));
                        cloth.setPret(resultSet.getDouble("price"));
                        cloth.setBrand(resultSet.getString("brand"));
                        cloth.setGender(resultSet.getString("attribute1").charAt(0));
                        cloth.setSize(resultSet.getString("attribute2").charAt(0));
                        this.basket.put(this.basket.size() + 1, cloth);
                        break;

                    case 4:
                        Cosmetic cosmetic = new Cosmetic();
                        cosmetic.setName(resultSet.getString("name"));
                        cosmetic.setPret(resultSet.getDouble("price"));
                        cosmetic.setBrand(resultSet.getString("brand"));
                        this.basket.put(this.basket.size() + 1, cosmetic);
                        break;

                    case 5:
                        Electronics electronic = new Electronics();
                        electronic.setName(resultSet.getString("name"));
                        electronic.setPret(resultSet.getDouble("price"));
                        electronic.setBrand(resultSet.getString("brand"));
                        electronic.setGuarantee(resultSet.getInt("attribute1"));
                        this.basket.put(this.basket.size() + 1, electronic);
                        break;

                    case 6:
                        Food food = new Food();
                        food.setName(resultSet.getString("name"));
                        food.setPret(resultSet.getDouble("price"));
                        food.setBrand(resultSet.getString("brand"));
                        food.setQuantity(resultSet.getDouble("attribute1"));
                        food.setPackaging(resultSet.getBoolean("attribute2"));
                        this.basket.put(this.basket.size() + 1, food);
                        break;

                    case 7:
                        Jewlery jewlery = new Jewlery();
                        jewlery.setName(resultSet.getString("name"));
                        jewlery.setPret(resultSet.getDouble("price"));
                        jewlery.setBrand(resultSet.getString("brand"));
                        jewlery.setMaterial(resultSet.getString("attribute1"));
                        this.basket.put(this.basket.size() + 1, jewlery);
                        break;

                    case 8:
                        Toys toy = new Toys();
                        toy.setName(resultSet.getString("name"));
                        toy.setPret(resultSet.getDouble("price"));
                        toy.setBrand(resultSet.getString("brand"));
                        toy.setGender(resultSet.getString("attribute1").charAt(0));
                        toy.setMinAge(resultSet.getInt("attribute2"));
                        this.basket.put(this.basket.size() + 1, toy);
                        break;

                    default:
                        System.out.println("Nu exista acest tip de produs. Reincercati");
                }
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

    }


    private static void deleteProduct(Statement statement, String table, int id) {
        String query = "DELETE FROM " + table + " WHERE (`id` = '" + id + "')";
        System.out.println(table);

        try{
            statement.executeUpdate(query);
            if(table == "new_schema.employees"){
                employees.remove(id-1);
            }
            if(table == "new_schema.clients"){
                clients.remove(id-1);
            }
            if(table == "new_schema.basket"){
                basket.remove(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateProductById(Statement statement, String table, int id, String temp) {
        String []attributes = temp.split(",");
        String query = null;
        if(table == "new_schema.employees") {
            query = "UPDATE " + table + " SET `Name` = '" + attributes[0] + "', `Age` = '" + Integer.parseInt(attributes[1])+ "', `Salary` = '" + Double.parseDouble(attributes[2]) + "' WHERE (`id` = '" + id + "')";
//                    "UPDATE " + table + " SET `name` = '" + name + "' WHERE (`id` = '" + id + "')";
            /*employees.clear();
//            employees = new ArrayList();
            System.out.println("-----------------");
            for(Employee e : employees){
                System.out.println(e);
            }
            ReadEmployees();*/

            employees.get(id-1).setName(attributes[0]);
            employees.get(id-1).setAge(Integer.parseInt(attributes[1]));
            employees.get(id-1).setSalary(Double.parseDouble(attributes[2]));
        }

        if(table == "new_schema.clients") {
            query = "UPDATE " + table + " SET `name` = '" + attributes[0] + "', `age` = '" + Integer.parseInt(attributes[1]) + "', `method` = '" + attributes[2] + "', `check` = '" + Boolean.parseBoolean(attributes[3]) + "'  WHERE (`id` = '" + id + "')";

            clients.get(id-1).setName(attributes[0]);
            clients.get(id-1).setAge(Integer.parseInt(attributes[1]));
//            clients.get(id-1).setDebitCard(att);
        }

        if(table == "new_schema.basket") {
            query = "UPDATE " + table + " SET `name` = '" + attributes[0] + "', `brand` = '" + attributes[1] + "', `price` = '" + Double.parseDouble(attributes[2]) + "' WHERE (`id` = '" + id + "')";
//                    "UPDATE " + table + " SET `name` = '" + name + "' WHERE (`id` = '" + id + "')";

            basket.get(id).setName(attributes[0]);
            basket.get(id).setBrand(attributes[1]);
            basket.get(id).setPret(Double.parseDouble(attributes[2]));
        }

        try{
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    private static ResultSet getAllProducts(Statement statement, String table) {
        String query = "SELECT * FROM " + table;
        try{
            return statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println(e);
        } return null;
    }

    private static void createProductDB(Statement statement){
        try{
            System.out.println("Choos a number between:\n1.Jewlery\n2.Cosmetic\n3.Electronics\n4.AnimalFood");
            Scanner s1 = new Scanner(System.in);
            int id = s1.nextInt();
            String sql = null;
            switch(id){
                case 1: //Jewlery : String name, String brand, double pret, String material
                    sql = "CREATE TABLE `new_schema`.`jewlery` (`id` INT NOT NULL AUTO_INCREMENT,`name` VARCHAR(40) NULL,`brand` VARCHAR(45) NULL,`pret` DOUBLE NULL,`material` VARCHAR(45) NULL, PRIMARY KEY (`id`))";
                    statement.executeUpdate(sql);
                    break;

                case 2: //Cosmetic: String name, String brand, double pret
                    sql = "CREATE TABLE `new_schema`.`cosmetic` (`id` INT NOT NULL AUTO_INCREMENT,`name` VARCHAR(45) NULL,`brand` VARCHAR(45) NULL,`pret` DOUBLE NULL, PRIMARY KEY (`id`))";
                    statement.executeUpdate(sql);
                    break;

                case 3: //Electronics: String name, String brand, double pret, int guarantee
                    sql = "CREATE TABLE `new_schema`.`electronics` (`id` INT NOT NULL AUTO_INCREMENT,`name` VARCHAR(45) NULL,`brand` VARCHAR(45) NULL,`pret` DOUBLE NULL,`guarantee` INT NULL, PRIMARY KEY (`id`))";
                    statement.executeUpdate(sql);
                    break;

                case 4: //AnimalFood: String name, String brand, double pret, String animal, double quantity
                    sql = "CREATE TABLE `new_schema`.`animalFood` (`id` INT NOT NULL AUTO_INCREMENT,`name` VARCHAR(45) NULL,`brand` VARCHAR(45) NULL,`pret` DOUBLE NULL,`animal` VARCHAR(20) NULL,`quantity` DOUBLE NULL, PRIMARY KEY (`id`))";
                    statement.executeUpdate(sql);
                    break;

                default:
                     System.out.println("Invalid option! Try again!");

            }

//            "CREATE TABLE `new_schema`.`new_table2` (`id` INT NOT NULL AUTO_INCREMENT,`val1` VARCHAR(45) NULL,`val2` INT NULL, PRIMARY KEY (`id`))";
        } catch(SQLException e){
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void ReadEmployees(){
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()){

            ResultSet resultSet = getAllProducts(statement, "new_schema.employees");

            while(resultSet.next()) {
                Employee employee = new Employee();
//                employee.setId(resultSet.getInt("id"));
                employee.setName(resultSet.getString("name"));
                employee.setAge(resultSet.getInt("age"));
                employee.setSalary(resultSet.getDouble("salary"));

                employees.add(employee);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    private static void ReadClients(){
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()){

            ResultSet resultSet = getAllProducts(statement, "new_schema.clients");

            while(resultSet.next()) {
                Client client = new Client();

                client.setName(resultSet.getString("name"));
                client.setAge(resultSet.getInt("age"));

                clients.add(client);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    private static void ReadBasket(){
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()){

            ResultSet resultSet = getAllProducts(statement, "new_schema.basket");

            while(resultSet.next()) {
                Employee employee = new Employee();
//                employee.setId(resultSet.getInt("id"));
                employee.setName(resultSet.getString("name"));
                employee.setAge(resultSet.getInt("age"));
                employee.setSalary(resultSet.getDouble("salary"));

                employees.add(employee);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void show() throws IOException {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            while(true) {
                System.out.print("\n" + this);
                int n = scanner.nextInt();
                String temp;
                switch(n) {
                    case 0:
                        AuditCenter t4 = new AuditCenter("Display options");
                        t4.start();
                        AuditCenter.audit("Display options", t4);
                        System.out.print(this);
                        break;
                    case 1:
                        AuditCenter t5 = new AuditCenter("Display clients list");
                        t5.start();
                        AuditCenter.audit("Display clients list", t5);
                        this.showClientsList();
                        break;
                    case 2:
                        AuditCenter t6 = new AuditCenter("Display employees list");
                        t6.start();
                        AuditCenter.audit("Display employees list",t6);
                        this.showEmployeeList();
                        break;
                    case 3:
                        AuditCenter t7 = new AuditCenter("Add a client");
                        t7.start();
                        AuditCenter.audit("Add a client",t7);
                        System.out.println("Please write the name, age, payment method and the existence of a fideliy card of the client!");
                        Scanner scanner2 = new Scanner(System.in);
                        temp = scanner2.nextLine();
                        this.fin.write(new File("C:\\Users\\m_mel\\Desktop\\ClientsList.csv"), temp);
                        String[] temp2 = temp.split(",");
                        this.addClient(new Client(temp2[0], Integer.parseInt(temp2[1]), temp2[2], Boolean.parseBoolean(temp2[3])));
                        break;
                    case 4:
                        AuditCenter t8 = new AuditCenter("Add an employee");
                        t8.start();
                        AuditCenter.audit("Add an employee",t8);
                        System.out.println("Please write the name, age, salary of the client!");
                        Scanner scanner3 = new Scanner(System.in);
                        temp = scanner3.nextLine();
                        this.fin.write(new File("C:\\Users\\m_mel\\Desktop\\EmployeesList.csv"), temp);
                        String[] temp3 = temp.split(",");
                        this.addEmployee(new Employee(temp3[0], Integer.parseInt(temp3[1]), Double.parseDouble(temp3[2])));
                        break;
                    case 5:
                        AuditCenter t9 = new AuditCenter("Display the products sold today");
                        t9.start();
                        AuditCenter.audit("Display the products sold today", t9);
                        System.out.println(this.basket + "\n");
                        break;
                    case 6:
                        AuditCenter t10 = new AuditCenter("Sort employee list");
                        t10.start();
                        AuditCenter.audit("Sort employee list", t10);
                        this.sortEmployeesList();
                        break;
                    case 7:
                        AuditCenter t11 = new AuditCenter("Display the contents of the client's basket");
                        t11.start();
                        AuditCenter.audit("Display the contents of the client's basket",t11);
                        Iterator var23 = basket.keySet().iterator();

                        while(var23.hasNext()) {
                            Integer index = (Integer)var23.next();
                            System.out.println(index + ". " + basket.get(index));
                        }

                        System.out.println();
                        break;
                    case 8:
                        AuditCenter t12 = new AuditCenter("Bought a toy");
                        t12.start();
                        AuditCenter.audit("Bought a toy", t12);
                        System.out.println("Please write the name, brand, price, gender(F/M) and minimum age for the toy!");
                        Scanner scanner4 = new Scanner(System.in);
                        temp = scanner4.nextLine();
                        this.fin.write(new File("C:\\Users\\m_mel\\Desktop\\Basket.csv"), "8," + temp);
                        String[] temp4 = temp.split(",");
                        this.addProduct(new Toys(temp4[0], temp4[1], Double.parseDouble(temp4[2]), temp4[3].charAt(0), Integer.parseInt(temp4[4])));
                        break;
                    case 9:
                        AuditCenter t13 = new AuditCenter("Bought a piece of clothing");
                        t13.start();
                        AuditCenter.audit("Bought a piece of clothing", t13);
                        System.out.println("Please write the name, brand, price, gender and size(S-XXL) for your piece of clothing!");
                        Scanner scanner5 = new Scanner(System.in);
                        temp = scanner5.nextLine();
                        this.fin.write(new File("C:\\Users\\m_mel\\Desktop\\Basket.csv"), "3," + temp);
                        String[] temp5 = temp.split(",");
                        this.addProduct(new Cloth(temp5[0], temp5[1], Double.parseDouble(temp5[2]), temp5[3].charAt(0), temp5[4].charAt(0)));
                        break;
                    case 10:
                        AuditCenter t14 = new AuditCenter("Bought food");
                        t14.start();
                        AuditCenter.audit("Bought food",t14);
                        System.out.println("Please write the name, brand, price, quantity and if it is packed for your food!");
                        Scanner scanner6 = new Scanner(System.in);
                        temp = scanner6.nextLine();
                        this.fin.write(new File("C:\\Users\\m_mel\\Desktop\\Basket.csv"), "6," + temp);
                        String[] temp6 = temp.split(",");
                        this.addProduct(new Food(temp6[0], temp6[1], Double.parseDouble(temp6[2]), Double.parseDouble(temp6[3]), Boolean.parseBoolean(temp6[4])));
                        break;
                    case 11:
                        AuditCenter t15 = new AuditCenter("Bought jewlery");
                        t15.start();
                        AuditCenter.audit("Bought jewlery",t15);
                        System.out.println("Please write the name, brand, price and material for your jewlery!");
                        Scanner scanner7 = new Scanner(System.in);
                        temp = scanner7.nextLine();
                        this.fin.write(new File("C:\\Users\\m_mel\\Desktop\\Basket.csv"), "7," + temp);
                        String[] temp7 = temp.split(",");
                        this.addProduct(new Jewlery(temp7[0], temp7[1], Double.parseDouble(temp7[2]), temp7[3]));
                        break;
                    case 12:
                        AuditCenter t16 = new AuditCenter("Bought an electronic");
                        t16.start();
                        AuditCenter.audit("Bought an electronic",t16);
                        System.out.println("Please write the name, brand, price, duration of your guarantee (in years) for your electronic!");
                        Scanner scanner8 = new Scanner(System.in);
                        temp = scanner8.nextLine();
                        this.fin.write(new File("C:\\Users\\m_mel\\Desktop\\Basket.csv"), "5," + temp);
                        String[] temp8 = temp.split(",");
                        this.addProduct(new Electronics(temp8[0], temp8[1], Double.parseDouble(temp8[2]), Integer.parseInt(temp8[3])));
                        break;
                    case 13:
                        AuditCenter t17 = new AuditCenter("Bought an animal");
                        t17.start();
                        AuditCenter.audit("Bought an animal",t17);
                        System.out.println("Please write the name, brand, price, type and quantity for your animal!");
                        Scanner scanner9 = new Scanner(System.in);
                        temp = scanner9.nextLine();
                        this.fin.write(new File("C:\\Users\\m_mel\\Desktop\\Basket.csv"), "1," + temp);
                        String[] temp9 = temp.split(",");
                        this.addProduct(new AnimalFood(temp9[0], temp9[1], Double.parseDouble(temp9[2]), temp9[3], Double.parseDouble(temp9[4])));
                        break;
                    case 14:
                        AuditCenter t18 = new AuditCenter("Bought cosmetics");
                        t18.start();
                        AuditCenter.audit("Bought cosmetics",t18);
                        System.out.println("Please write the name, brand and price for your cosmetics!");
                        Scanner scanner10 = new Scanner(System.in);
                        temp = scanner10.nextLine();
                        this.fin.write(new File("C:\\Users\\m_mel\\Desktop\\Basket.csv"), "4," + temp);
                        String[] temp10 = temp.split(",");
                        this.addProduct(new Cosmetic(temp10[0], temp10[1], Double.parseDouble(temp10[2])));
                        break;
                    case 15:
                        AuditCenter t19 = new AuditCenter("Exited the program succesfully");
                        t19.start();
                        AuditCenter.audit("Exited the program succesfully",t19);
                        System.out.println("Thank you!");
                        System.exit(0);
                        break;

                    case 16:
                        AuditCenter t20 = new AuditCenter("Changing tables");
                        t20.start();
                        AuditCenter.audit("Changing tables", t20);

                        System.out.println("What table do you want to change?");
                        System.out.println("1.Employees\n2.Clients\n3.Basket(1, 2 or 3)");
                        Scanner scanner11 = new Scanner(System.in);
                        int tableId = scanner11.nextInt();
                        String table = "";
                        switch(tableId){
                            case 1:
                                table = "new_schema.employees";
                                break;

                            case 2:
                                table = "new_schema.clients";
                                break;

                            case 3:
                                table = "new_schema.basket";
                                break;

                            default:
                                System.out.println("Invalid option!");
                        }

                        System.out.println("What element do you want to delete?");
                        int id = scanner11.nextInt();

                        try ( Connection connection = DriverManager.getConnection(url, username, password);
                              Statement statement = connection.createStatement()) {
                            deleteProduct(statement, table, id);
                            //updatare tabel
                        } catch(SQLException e) {
                            e.printStackTrace();
                        }
                        break;

                    case 17:
//                        System.out.println("What table do you want to change?");
                        AuditCenter t1 = new AuditCenter("Changing table");
                        t1.start();
                        AuditCenter.audit("Changing tables", t1);

                        System.out.println("1.Employees\n2.Clients\n3.Basket(1, 2 or 3)");
                        Scanner scanner12 = new Scanner(System.in);
                        int tableId2 = scanner12.nextInt();
                        String table2 = "";
                        String temp17 = "";
                        switch(tableId2){
                            case 1:
                                table2 = "new_schema.employees";
                                System.out.println("Write the name, age and salary of the employee:");
                                Scanner scanner13 = new Scanner(System.in);
                                temp17 = scanner13.nextLine();
                                break;

                            case 2:
                                table2 = "new_schema.clients";
                                System.out.println("Write the name, age, payment method(card/cash) of the client and if she/he has a fidelity card(true/false):");
                                Scanner scanner14 = new Scanner(System.in);
                                temp17 = scanner14.nextLine();
                                break;

                            case 3:
                                table2 = "new_schema.basket";
                                System.out.println("Write the name, brand and price of the product:");
                                Scanner scanner15 = new Scanner(System.in);
                                temp17 = scanner15.nextLine();
                                break;

                            default:
                                System.out.println("Invalid option!");
                        }

                        System.out.println("What element do you want to update?");
                        int id2 = scanner12.nextInt();

                        try(Connection connection = DriverManager.getConnection(url, username, password);
                            Statement statement = connection.createStatement()) {
                            updateProductById(statement, table2, id2, temp17);
                        } catch(SQLException e){
                            e.printStackTrace();
                        }
                        break;

                    case 18:
                        AuditCenter t3 = new AuditCenter("Changing table");
                        t3.start();
                        AuditCenter.audit("Changing table", t3);

                        try(Connection connection = DriverManager.getConnection(url, username, password);
                        Statement statement = connection.createStatement()) {
                        createProductDB(statement);
                    } catch(SQLException e){
                        e.printStackTrace();
                    }
                    break;

                    default:
                        AuditCenter.audit("Wrong number");
                        System.out.println("Wrong number!");
                }
            }
        }
    }

    void addProduct(Product product) {
        basket.put(basket.size() + 1, product);
    }

    void sortEmployeesList() {
        Collections.sort(this.employees, new Comparator<Employee>() {
            public int compare(Employee a, Employee b) {
                return Double.compare(a.getSalary(), b.getSalary());
            }
        });
    }

    void showClientsList() {
        Iterator var2 = this.clients.iterator();

        while(var2.hasNext()) {
            Client client = (Client)var2.next();
            System.out.println(client);
        }

    }

    public void showEmployeeList() {
        Iterator var2 = this.employees.iterator();

        while(var2.hasNext()) {
            Employee employee = (Employee)var2.next();
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
        return "Option 0 : Show options\nOption 1 : Clients list\nOption 2 : Employee list\nOption 3: Add client\nOption 4: Add employee\nOption 5: Show the sold product of today\nOption 6: Sort employees by salary\nOption 7: Show the basket\nOption 8: Buy another toy\nOption 9: Buy another piece of clothing\nOption 10: Buy food\nOption 11: Buy another jewlery\nOption 12: Buy another electronic\nOption 13: Buy food for your other animals\nOption 14: Buy another type of cosmetic product\nOption 15: Exit!\n"
                + "Option 16 : Delete element\n" + "Option 17 : Update element\n" + "Option 18 : Create product DB\n";
    }
}
