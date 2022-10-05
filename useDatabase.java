import java.sql.*;
import java.io.*;  
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;


/*
 * Function to read in data from csv files and store in database
 */
public class useDatabase {

    public static ArrayList<ArrayList<String>> readCSVFileName(String filename)  {
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        ArrayList<String> rows = new ArrayList<String>();

        Scanner sc;

        try {
            sc = new Scanner(new File(filename));

            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                rows = new ArrayList<String> (Arrays.asList(line.split(",")));
                data.add(rows);
            }

            data.remove(0); // removes title row from CSV
            data.remove(0); // removes attributes list
        sc.close();  //closes the scanner
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

  //Commands to run this script
  //This will compile all java files in this directory
  //javac *.java
  //This command tells the file where to find the postgres jar which it needs to execute postgres commands, then executes the code
  //Windows: java -cp ".;postgresql-42.2.8.jar" jdbcpostgreSQL
  //Mac/Linux: java -cp ".:postgresql-42.2.8.jar" jdbcpostgreSQL

  /*
   * Create a global hashmap to link menu items to ingredient/inventory items
   */


  public static void main(String args[]) {

    //Building the connection with your credentials
     Connection conn = null;
     String teamNumber = "54";
     String sectionNumber = "904";
     String dbName = "csce331_" + sectionNumber + "_" + teamNumber; 
     String dbConnectionString = "jdbc:postgresql://csce-315-db.engr.tamu.edu/" + dbName;
     dbSetup myCredentials = new dbSetup(); 

    //Connecting to the database
    try {
        conn = DriverManager.getConnection(dbConnectionString, myCredentials.user, myCredentials.pswd);
     } catch (Exception e) {
        e.printStackTrace();
        System.err.println(e.getClass().getName() + ": " + e.getMessage());
        System.exit(0);
     }

     System.out.println("Opened database successfully");

     // Populate database
     try {
       // Create a statement object
       Statement stmt = conn.createStatement();

       // Create tables (create strings and execute)
        // Customers
        String createCustomerTable = "CREATE TABLE customers (customerID int, firstName text, lastName text);";
        stmt.executeUpdate(createCustomerTable);

        // Employees
        String createEmployeeTable = "CREATE TABLE employees (employeeID int, firstName varchar, lastName varchar, payRate float, role varchar, startDate date, isManager boolean);";
        stmt.executeUpdate(createEmployeeTable);

        // //Inventory
        String createInventoryTable = "CREATE TABLE inventory (itemID int, name text, category text, expirationDate date, fridgeRequired boolean, quantity int, unit text);";
        stmt.executeUpdate(createInventoryTable);

        // Finances
        String createFinanceTable = "CREATE TABLE finances (transactionID int, isDebit boolean, isCredit boolean, details text, amount float);";
        stmt.executeUpdate(createFinanceTable);

        // Orders
        String createOrdersTable = "CREATE TABLE orders (orderID int, orderNumber int, totalPrice float, saleDate date, employeeID int, customerID int, satisfied boolean, itemsOrdered text);";
        stmt.executeUpdate(createOrdersTable);

        // POPULATE TABLES IN DATABASE
        // For Customers
        ArrayList<ArrayList<String>> customerList = readCSVFileName("Customers.csv");
        for (int i = 0; i < customerList.size(); i++) {
            String customerID = customerList.get(i).get(0);
            String firstName = customerList.get(i).get(1);
            String lastName = customerList.get(i).get(2);

            // Query String
            String query = String.format("INSERT INTO customers VALUES ('%s', '%s', '%s');", customerID, firstName, lastName);

            // Execute the query on the server
            stmt.executeUpdate(query);
        }

        // For Employees
        ArrayList<ArrayList<String>> employeeList = readCSVFileName("Employees.csv");
        for (int i = 0; i < employeeList.size(); i++) {
            String employeeID = employeeList.get(i).get(0);
            String firstName = employeeList.get(i).get(1);
            String lastName = employeeList.get(i).get(2);
            String payRate = employeeList.get(i).get(3); 
            String role = employeeList.get(i).get(4);
            String startDate = employeeList.get(i).get(5);
            String isManager = employeeList.get(i).get(6);

            // Query String
            String query = String.format("INSERT INTO employees VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s');", employeeID, firstName, lastName, payRate, role, startDate, isManager);

            // Execute query
            stmt.executeUpdate(query);
        }

        // For Inventory
        ArrayList<ArrayList<String>> inventoryList = readCSVFileName("Inventory.csv");
        for (int i = 0; i < inventoryList.size(); i++) {
            String itemID = inventoryList.get(i).get(0);
            String name = inventoryList.get(i).get(1);
            String category = inventoryList.get(i).get(2);
            String expDate = inventoryList.get(i).get(3);
            String needsFridge = inventoryList.get(i).get(4);
            String quantity = inventoryList.get(i).get(5);
            String unit = inventoryList.get(i).get(6);

            // Query String
            String query = String.format("INSERT INTO inventory VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s');", itemID, name, category, expDate, needsFridge, quantity, unit);

            // Execute query
            stmt.executeUpdate(query);
        }

        // For Finances
        ArrayList<ArrayList<String>> financesList = readCSVFileName("Finances.csv");
        for (int i = 0; i < financesList.size(); i++) {
            String tranID = financesList.get(i).get(0);
            String isDebit = financesList.get(i).get(1);
            String isCredit = financesList.get(i).get(2);
            String details = financesList.get(i).get(3);
            String amount = financesList.get(i).get(4);

            // Query String
            String query = String.format("INSERT INTO finances VALUES ('%s', '%s', '%s', '%s', '%s');", tranID, isDebit, isCredit, details, amount);

            // Execute query
            stmt.executeUpdate(query);
        }

        // For Orders
        ArrayList<ArrayList<String>> ordersList = readCSVFileName("Orders.csv");
        for (int i = 0; i < ordersList.size(); i++) {
            String orderID = ordersList.get(i).get(0);
            String orderNum = ordersList.get(i).get(1);
            String totPrice = ordersList.get(i).get(2);
            String date = ordersList.get(i).get(3);
            String empID = ordersList.get(i).get(4);
            String custID = ordersList.get(i).get(5);
            String satisfied = ordersList.get(i).get(6);
            String items = ordersList.get(i).get(7);

            // Query string
            String query = String.format("INSERT INTO orders VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');", orderID, orderNum, totPrice, date, empID, custID, satisfied, items);

            // Execute query
            stmt.executeUpdate(query);
        }

       System.out.println("--------------------Query Results--------------------");
   } catch (Exception e) {
       e.printStackTrace();
       System.err.println(e.getClass().getName()+": "+e.getMessage());
       System.exit(0);
   }

    //closing the connection
    try {
      conn.close();
      System.out.println("Connection Closed.");
    } catch(Exception e) {
      System.out.println("Connection NOT Closed.");
    }
  }
}
